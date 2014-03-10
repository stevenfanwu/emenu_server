/**
 * @(#)BaseDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.cloudstone.emenu.storage.db.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.BaseData;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.BaseStorage;
import com.cloudstone.emenu.util.CollectionUtils;
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
    public int count(EmenuContext context) {
        String sql = "SELECT count(*) FROM " + getTableName() + " WHERE deleted=0";
        return queryInt(context, sql, StatementBinder.NULL);
    }
    
    @Override
    public int getMaxId(EmenuContext context) {
        String sql = "SELECT MAX(id) FROM " + getTableName();
        return queryInt(context, sql, StatementBinder.NULL);
    }
    
    @Override
    public void delete(EmenuContext context, int id) {
        String sql = "UPDATE " + getTableName() + " SET deleted=1 WHERE id=?";
        executeSQL(context, sql, new IdStatementBinder(id));
    }
    
    /* ---------- protected ----------*/
    protected int genId(EmenuContext context) {
        return idGenerator.generateId(context, this);
    }
    
    protected <T extends BaseData> T getByName(EmenuContext context,
            String name, RowMapper<T> rowMapper) {
        String sql = new SelectSqlBuilder(getTableName()).appendWhereName().appendWhereRestaurantId().build();
        List<T> list = query(context, sql, new NameStatementBinder(name, context.getRestaurantId()), rowMapper);
        return getFirstUndeleted(list);
    }

    protected <T extends BaseData> T getByNameAccrossRestaurants(EmenuContext context,
                                              String name, RowMapper<T> rowMapper) {
      String sql = new SelectSqlBuilder(getTableName()).appendWhereName().build();
      List<T> list = query(context, sql, new NameStatementBinder(name, 0), rowMapper);
      return getFirstUndeleted(list);
    }

    private <T extends BaseData> T getFirstUndeleted(List<T> list) {
       T r = null;
       for (int i=0; i<list.size(); i++) {
          r = list.get(i);
          if (!r.isDeleted()) {
             break;
          }
       }
       return r;
    }

    public <T> List<T> getAllInRestaurant(EmenuContext context, RowMapper<T> rowMapper) {
        return query(context,
           new SelectSqlBuilder(this.getTableName()).appendWhereRestaurantId().build(),
           new RestaurantIdBinder(context.getRestaurantId()), rowMapper);
    }
    
    protected List<IdName> getIdNames(EmenuContext context) {
        String sql = "SELECT id, name, createdTime, updateTime, deleted FROM " + getTableName() +
                     " WHERE restaurantId=?";
        return query(context, sql, new RestaurantIdBinder(context.getRestaurantId()), ID_NAME_ROW_MAPPER);
    }
    
    private volatile boolean inited = false;
    @PostConstruct
    public void init() {
        inited = true;
        try {
            init(new EmenuContext());
        } catch (Throwable e) {
            LOG.error("", e);
            inited = false;
        }
    }
    
    protected void init(EmenuContext context) {
        onCheckCreateTable(context);
    }

    protected void checkCreateTable(EmenuContext context, String tableName, String columnDef) {
        String sql = String.format(SQL_CREATE, tableName, columnDef);
//        LOG.info("create table sql: " + sql);
        executeSQL(context, sql, StatementBinder.NULL);
    }
    
    protected void executeSQL(EmenuContext context, String sql, StatementBinder binder) {
        LOG.info(sql);
        synchronized (dataSource) {
            try {
                SQLiteConnection conn = getConnection(context);
                SQLiteStatement stmt = conn.prepare(sql);
                try {
                    binder.onBind(stmt);
                    stmt.stepThrough();
                } finally {
                    stmt.dispose();
                    if (context.getTransaction() == null) {
                        conn.dispose();
                    }
                }
            } catch (SQLiteException e) {
                throw new ServerError(e);
            }
        }
    }
    
    protected String queryString(EmenuContext context, String sql, StatementBinder binder) {
        return new QueryStringGetter().exec(context, sql, binder);
    }
    
    protected int queryInt(EmenuContext context, String sql, StatementBinder binder) {
        return new QueryIntGetter().exec(context, sql, binder);
    }
    
    protected int[] queryIntArray(EmenuContext context, String sql, StatementBinder binder) {
        List<Integer> list = query(context, sql, binder, new IntRowMapper());
        return CollectionUtils.toIntArray(list);
    }
    
    protected <T> T queryOne(EmenuContext context, String sql, StatementBinder binder, RowMapper<T> rowMapper) {
        return new QueryObjectGetter<T>() {}.exec(context,
                sql, binder, rowMapper);
    }
    
    protected <T> List<T> query(EmenuContext context, String sql, StatementBinder binder, RowMapper<T> rowMapper) {
        return new QueryListGetter<T>().exec(context, sql, binder, rowMapper);
    }
    
    /* ---------- abstract ----------*/
    protected abstract void onCheckCreateTable(EmenuContext context) ;
    
    /* ---------- Inner Class ---------- */
    private abstract class BaseQueryGetter<T, R> {
        protected R exec(EmenuContext context, String sql,
                StatementBinder binder, RowMapper<T> rowMapper) {
            synchronized (dataSource) {
                try {
                    SQLiteConnection conn = getConnection(context);
                    SQLiteStatement stmt = conn.prepare(sql);
                    try {
                        binder.onBind(stmt);
                        LOG.info(sql);
                        return parseData(stmt, rowMapper);
                    } finally {
                        stmt.dispose();
                        if (context.getTransaction() == null) {
                            conn.dispose();
                        }
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
        public T exec(EmenuContext context, String sql, StatementBinder binder,
                RowMapper<T> rowMapper) {
            return super.exec(context, sql, binder, rowMapper);
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
        public String exec(EmenuContext context, String sql, StatementBinder binder) {
            return super.exec(context, sql, binder, null);
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
        public Integer exec(EmenuContext context, String sql, StatementBinder binder) {
            return super.exec(context, sql, binder, null);
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

    private SQLiteConnection getConnection(EmenuContext context) {
        if (!inited) {
            inited = true;
            try {
                init(context);
            } catch (Throwable  e){
                inited = false;
                throw new ServerError(e);
            }
        }
        if (context.getTransaction() != null)
            return context.getTransaction().getTransConn();
        else {
            try {
                return dataSource.open();
            } catch (SQLiteException e) {
                throw new ServerError(e);
            }
        }
    }
}