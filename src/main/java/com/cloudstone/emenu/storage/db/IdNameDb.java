/**
 * @(#)IdNameDb.java, Jul 22, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.DeleteSqlBuilder;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.storage.db.util.UpdateSqlBuilder;

/**
 * @author xuhongfeng
 *
 */
public abstract class IdNameDb<T extends IdName> extends SQLiteDb {

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(getTableName(), COL_DEF);
    }
    
    protected void add(T data) throws SQLiteException {
        executeSQL(SQL_INSERT, new IdNameBinder(data));
    }
    
    protected void update(T data) throws SQLiteException {
        executeSQL(SQL_UPDATE, new UpdateBinder(data));
    }
    
    protected void delete(long id) throws SQLiteException {
        executeSQL(SQL_DELETE, new IdStatementBinder(id));
    }
    
    protected List<T> getAll() throws SQLiteException {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }
    
    protected T get(long id) throws SQLiteException {
        IdStatementBinder binder = new IdStatementBinder(id);
        T data = queryOne(SQL_SELECT_BY_ID, binder, rowMapper);
        return data;
    }
    
    /* -------- abstract --------- */
    protected abstract T newObject();

    /* ---------- SQL ---------- */
    private static enum Column {
        ID("id"), NAME("name");
        
        private final String str;
        private Column(String str) {
            this.str = str;
        }
        
        @Override
        public String toString() {
            return str;
        }
    }

    private static final String COL_DEF = new ColumnDefBuilder()
        .append(Column.ID, DataType.INTEGER, "NOT NULL PRIMARY KEY")
        .append(Column.NAME, DataType.TEXT, "NOT NULL")
        .build();
    private final String SQL_UPDATE = new UpdateSqlBuilder(getTableName())
        .appendSetValue(Column.NAME).appendWhereId().build();
    private final String SQL_SELECT_BY_ID = new SelectSqlBuilder(getTableName())
        .appendWhereId().build();
    private final String SQL_SELECT = new SelectSqlBuilder(getTableName()).build();
    private final String SQL_DELETE = new DeleteSqlBuilder(getTableName())
        .appendWhereId().build();
    private final String SQL_INSERT = new InsertSqlBuilder(getTableName(), 2).build();
    
    public static class IdNameBinder implements StatementBinder {
        private final IdName data;

        public IdNameBinder(IdName data) {
            super();
            this.data = data;
        }
        
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, data.getId());
            stmt.bind(2, data.getName());
        }
    }
    private static class UpdateBinder implements StatementBinder {
        private final IdName data;

        public UpdateBinder(IdName data) {
            super();
            this.data = data;
        }
        
        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, data.getName());
            stmt.bind(2, data.getId());
        }
    }
    
    private final RowMapper<T> rowMapper = new RowMapper<T>() {

        @Override
        public T map(SQLiteStatement stmt) throws SQLiteException {
            T data = newObject();
            data.setId(stmt.columnLong(0));
            data.setName(stmt.columnString(1));
            return data;
        }
    };
}
