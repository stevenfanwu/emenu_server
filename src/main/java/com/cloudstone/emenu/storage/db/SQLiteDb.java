/**
 * @(#)BaseDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.BaseData;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.storage.BaseStorage;
import com.cloudstone.emenu.storage.db.util.CreateIndexBuilder;
import com.cloudstone.emenu.storage.db.util.DbTransaction;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.NameStatementBinder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.SqliteDataSource;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.util.IdGenerator;

/**
 * @author xuhongfeng
 *
 */
public abstract class SQLiteDb extends BaseStorage implements IDb {
    private static final Logger LOG = LoggerFactory.getLogger(SQLiteDb.class);
    
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS %s (%s)";
    
    private static final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();
    private static final Lock READ_LOCK = LOCK.readLock();
    private static final Lock WRITE_LOCK = LOCK.writeLock();
            
    private SqliteDataSource dataSource;
    
    private IdGenerator idGenerator = new IdGenerator();
    
    public static enum DataType {
        NULL("NULL"), INTEGER("INTEGER"), REAL("REAL"), TEXT("TEXT"), BLOB("BLOB");
        
        private final String str;
        private DataType(String str) {
            this.str = str;
        }
        
        @Override
        public String toString() {
            return str;
        }
    }
    
    @Override
    public int getMaxId() throws SQLiteException {
        String sql = "SELECT MAX(id) FROM " + getTableName();
        return queryInt(sql, StatementBinder.NULL);
    }
    
    public void delete(int id) throws SQLiteException {
        String sql = "UPDATE " + getTableName() + " SET deleted=1 WHERE id=?";
        executeSQL(sql, new IdStatementBinder(id), null);
    }
    
    /* ---------- protected ----------*/
    protected int genId() throws SQLiteException {
        return idGenerator.generateId(this);
    }
    
    protected <T extends BaseData> T getByName(String name, RowMapper<T> rowMapper) throws SQLiteException {
        String sql = new SelectSqlBuilder(getTableName()).appendWhereName().build();
        List<T> list = query(sql, new NameStatementBinder(name), rowMapper);
        T r = null;
        for (int i=0; i<list.size(); i++) {
            r = list.get(i);
            if (!r.isDeleted()) {
                break;
            }
        }
        return r;
    }
    
    protected List<IdName> getIdNames() throws SQLiteException {
        String sql = "SELECT id, name, createdTime, updateTime, deleted FROM " + getTableName();
        return query(sql, StatementBinder.NULL, ID_NAME_ROW_MAPPER);
    }
    
    private volatile boolean inited = false;
    public void init() throws SQLiteException {
        if(!inited) {
            inited = true;
            onCheckCreateTable();
        }
    }

    protected void checkCreateTable(String tableName, String columnDef) throws SQLiteException {
        String sql = String.format(SQL_CREATE, tableName, columnDef);
//        LOG.info("create table sql: " + sql);
        executeSQL(sql, StatementBinder.NULL, null);
    }
    
    protected void checkCreateIndex(String indexName, String tableName, Object... columns) throws SQLiteException {
        CreateIndexBuilder createIndexBuilder = new CreateIndexBuilder(indexName,
                tableName, columns);
        executeSQL(createIndexBuilder.build(), StatementBinder.NULL, null);
    }
    
    protected void executeSQL(String sql, StatementBinder binder, DbTransaction trans) throws SQLiteException {
        SQLiteConnection conn = dataSource.open(this, trans);
        SQLiteStatement stmt = conn.prepare(sql);
        WRITE_LOCK.lock();
        try {
            binder.onBind(stmt);
            stmt.stepThrough();
        } finally {
            stmt.dispose();
            conn.dispose();
            WRITE_LOCK.unlock();
        }
    }
    
    protected String queryString(String sql, StatementBinder binder) throws SQLiteException {
        return new QueryStringGetter().exec(sql, binder);
    }
    
    protected int queryInt(String sql, StatementBinder binder) throws SQLiteException {
        return new QueryIntGetter().exec(sql, binder);
    }
    
    protected <T> T queryOne(String sql, StatementBinder binder, RowMapper<T> rowMapper) throws SQLiteException {
        return new QueryObjectGetter<T>() {}.exec(sql, binder, rowMapper);
    }
    
    protected <T> List<T> query(String sql, StatementBinder binder, RowMapper<T> rowMapper) throws SQLiteException {
        return new QueryListGetter<T>().exec(sql, binder, rowMapper);
    }
    
    /* ---------- abstract ----------*/
    protected abstract void onCheckCreateTable() throws SQLiteException;
    
    /* ---------- Inner Class ---------- */
    private abstract class BaseQueryGetter<T, R> {
        protected R exec(String sql, StatementBinder binder, RowMapper<T> rowMapper) throws SQLiteException {
            SQLiteConnection conn = dataSource.open(SQLiteDb.this, null);
            SQLiteStatement stmt = conn.prepare(sql);
            try {
                binder.onBind(stmt);
                READ_LOCK.lock();
                try {
                    return parseData(stmt, rowMapper);
                } finally {
                    READ_LOCK.unlock();
                }
            } finally {
                stmt.dispose();
                conn.dispose();
            }
        }
        
        protected abstract R parseData(SQLiteStatement stmt, RowMapper<T> rowMapper) throws SQLiteException;
    }
    
    private abstract class QueryObjectGetter<T> extends BaseQueryGetter<T, T> {
        @Override
        public T exec(String sql, StatementBinder binder,
                RowMapper<T> rowMapper) throws SQLiteException {
            return super.exec(sql, binder, rowMapper);
        }
        
        @Override
        protected T parseData(SQLiteStatement stmt, RowMapper<T> rowMapper)
                throws SQLiteException {
            if (stmt.step()) {
                return rowMapper.map(stmt);
            } else {
                return null;
            }
        }
    }
    
    private class QueryListGetter<T> extends BaseQueryGetter<T, List<T>> {

        protected List<T> parseData(SQLiteStatement stmt,
                RowMapper<T> rowMapper) throws SQLiteException {
            List<T> list = new ArrayList<T>();
            while (stmt.step()) {
                list.add(rowMapper.map(stmt));
            }
            return list;
        }
    }
    
    private class QueryStringGetter extends QueryObjectGetter<String> {
        public String exec(String sql, StatementBinder binder) throws SQLiteException {
            return super.exec(sql, binder, null);
        }
        @Override
        protected String parseData(SQLiteStatement stmt,
                RowMapper<String> rowMapper) throws SQLiteException {
            if (stmt.step()) {
                return stmt.columnString(0);
            } else {
                return null;
            }
        }
    }
    
    private class QueryIntGetter extends QueryObjectGetter<Integer> {
        public Integer exec(String sql, StatementBinder binder) throws SQLiteException {
            return super.exec(sql, binder, null);
        }
        @Override
        protected Integer parseData(SQLiteStatement stmt,
                RowMapper<Integer> rowMapper) throws SQLiteException {
            if (stmt.step()) {
                return stmt.columnInt(0);
            } else {
                return 0;
            }
        }
    }
    
    private static final RowMapper<IdName> ID_NAME_ROW_MAPPER = new RowMapper<IdName> (){
    
        @Override
        public IdName map(SQLiteStatement stmt) throws SQLiteException {
            IdName o = new IdName();
            o.setId(stmt.columnInt(0));
            o.setName(stmt.columnString(1));
            o.setCreatedTime(stmt.columnLong(2));
            o.setUpdateTime(stmt.columnLong(3));
            o.setDeleted(stmt.columnInt(4) == 1);
            return o;
        }
    
    };

    public SqliteDataSource getDataSource() {
        return dataSource;
    }

    @Autowired
    public void setDataSource(SqliteDataSource dataSource) {
        inited = false;
        this.dataSource = dataSource;
    }

}
