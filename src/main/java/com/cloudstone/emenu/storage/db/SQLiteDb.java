/**
 * @(#)BaseDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.BaseData;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.BaseStorage;
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
    public int getMaxId() {
        String sql = "SELECT MAX(id) FROM " + getTableName();
        return queryInt(sql, StatementBinder.NULL);
    }
    
    public void delete(int id) {
        String sql = "UPDATE " + getTableName() + " SET deleted=1 WHERE id=?";
        executeSQL(null, sql, new IdStatementBinder(id));
    }
    
    /* ---------- protected ----------*/
    protected int genId() {
        return idGenerator.generateId(this);
    }
    
    protected <T extends BaseData> T getByName(String name, RowMapper<T> rowMapper) {
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
    
    protected List<IdName> getIdNames() {
        String sql = "SELECT id, name, createdTime, updateTime, deleted FROM " + getTableName();
        return query(sql, StatementBinder.NULL, ID_NAME_ROW_MAPPER);
    }
    
    private volatile boolean inited = false;
    @PostConstruct
    protected void init() {
        if(!inited) {
            inited = true;
            try {
                onCheckCreateTable();
            } catch (Throwable e) {
                inited = false;
            }
        }
    }

    protected void checkCreateTable(String tableName, String columnDef) {
        String sql = String.format(SQL_CREATE, tableName, columnDef);
//        LOG.info("create table sql: " + sql);
        executeSQL(null, sql, StatementBinder.NULL);
    }
    
    protected void executeSQL(DbTransaction trans, String sql, StatementBinder binder) {
        LOG.info(sql);
        synchronized (SQLiteDb.class) {
            try {
                SQLiteConnection conn = getConnection(trans);
                SQLiteStatement stmt = conn.prepare(sql);
                try {
                    binder.onBind(stmt);
                    stmt.stepThrough();
                } finally {
                    stmt.dispose();
                    if (trans == null) {
                        conn.dispose();
                    }
                }
            } catch (SQLiteException e) {
                throw new ServerError(e);
            }
        }
    }
    
    protected String queryString(String sql, StatementBinder binder) {
        return new QueryStringGetter().exec(sql, binder);
    }
    
    protected int queryInt(String sql, StatementBinder binder) {
        return new QueryIntGetter().exec(sql, binder);
    }
    
    protected <T> T queryOne(String sql, StatementBinder binder, RowMapper<T> rowMapper) {
        return new QueryObjectGetter<T>() {}.exec(sql, binder, rowMapper);
    }
    
    protected <T> List<T> query(String sql, StatementBinder binder, RowMapper<T> rowMapper) {
        return new QueryListGetter<T>().exec(sql, binder, rowMapper);
    }
    
    /* ---------- abstract ----------*/
    protected abstract void onCheckCreateTable() ;
    
    /* ---------- Inner Class ---------- */
    private abstract class BaseQueryGetter<T, R> {
        protected R exec(String sql, StatementBinder binder, RowMapper<T> rowMapper) {
            synchronized (SQLiteDb.class) {
                try {
                    SQLiteConnection conn = getConnection(null);
                    SQLiteStatement stmt = conn.prepare(sql);
                    try {
                        binder.onBind(stmt);
                        return parseData(stmt, rowMapper);
                    } finally {
                        stmt.dispose();
                        conn.dispose();
                    }
                } catch (SQLiteException e) {
                    throw new ServerError(e);
                }
            }
        }
        
        protected abstract R parseData(SQLiteStatement stmt, RowMapper<T> rowMapper) throws SQLiteException;
    }
    
    private abstract class QueryObjectGetter<T> extends BaseQueryGetter<T, T> {
        @Override
        public T exec(String sql, StatementBinder binder,
                RowMapper<T> rowMapper) {
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
        public String exec(String sql, StatementBinder binder) {
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
        public Integer exec(String sql, StatementBinder binder) {
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

    private SQLiteConnection getConnection(DbTransaction trans) throws SQLiteException {
        if (!inited) {
            init();
        }
        if (trans != null)
            return trans.getTransConn();
        else
            return dataSource.open();
    }
}
