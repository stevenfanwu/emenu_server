/**
 * @(#)IdNameDb.java, Jul 22, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
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
    protected void onCheckCreateTable(EmenuContext context) {
        checkCreateTable(context, getTableName(), COL_DEF);
    }
    
    protected void add(EmenuContext context, T data) {
        data.setId(genId(context));
        executeSQL(context, SQL_INSERT, new IdNameBinder(data));
    }
    
    protected void update(EmenuContext context, T data) {
        executeSQL(context, SQL_UPDATE, new UpdateBinder(data));
    }
    
    protected List<T> getAll(EmenuContext context) {
        return query(context, SQL_SELECT, StatementBinder.NULL, rowMapper);
    }
    
    protected T get(EmenuContext context, int id) {
        IdStatementBinder binder = new IdStatementBinder(id);
        T data = queryOne(context, SQL_SELECT_BY_ID, binder, rowMapper);
        return data;
    }
    
    public T getByName(EmenuContext context, String name) {
        return super.getByName(context, name, rowMapper);
    }
    
    /* -------- abstract --------- */
    protected abstract T newObject();

    /* ---------- SQL ---------- */
    private static enum Column {
        ID("id"), NAME("name"),
        CREATED_TIME("createdTime"), UPDATE_TIME("updatetime"), DELETED("deleted");
        
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
        .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
        .build();
    private final String SQL_UPDATE = new UpdateSqlBuilder(getTableName())
        .appendSetValue(Column.NAME)
        .appendSetValue(Column.CREATED_TIME)
        .appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId().build();
    private final String SQL_SELECT_BY_ID = new SelectSqlBuilder(getTableName())
        .appendWhereId().build();
    private final String SQL_SELECT = new SelectSqlBuilder(getTableName()).build();
    private final String SQL_INSERT = new InsertSqlBuilder(getTableName(), 5).build();
    
    public static class IdNameBinder implements StatementBinder {
        private final IdName data;

        public IdNameBinder(IdName data) {
            super();
            this.data = data;
        }
        
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, data.getId());
            stmt.bind(2, data.getName());
            stmt.bind(3, data.getCreatedTime());
            stmt.bind(4, data.getUpdateTime());
            stmt.bind(5, data.isDeleted() ? 1 : 0);
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
            stmt.bind(2, data.getCreatedTime());
            stmt.bind(3, data.getUpdateTime());
            stmt.bind(4, data.isDeleted() ? 1 : 0);
            stmt.bind(5, data.getId());
        }
    }
    
    private final RowMapper<T> rowMapper = new RowMapper<T>() {

        @Override
        public T map(SQLiteStatement stmt) throws SQLiteException {
            T data = newObject();
            data.setId(stmt.columnInt(0));
            data.setName(stmt.columnString(1));
            data.setCreatedTime(stmt.columnLong(2));
            data.setUpdateTime(stmt.columnLong(3));
            data.setDeleted(stmt.columnInt(4) == 1);
            return data;
        }
    };
}
