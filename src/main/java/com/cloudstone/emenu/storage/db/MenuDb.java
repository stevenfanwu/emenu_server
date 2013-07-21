/**
 * @(#)MenuDb.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.Menu;
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
@Repository
public class MenuDb extends SQLiteDb implements IMenuDb {
    
    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void addMenu(Menu menu) throws SQLiteException {
        executeSQL(SQL_INSERT, new MenuBinder(menu));
    }

    @Override
    public void updateMenu(Menu menu) throws SQLiteException {
        executeSQL(SQL_UPDATE, new UpdateBinder(menu));
    }

    @Override
    public void deleteMenu(long id) throws SQLiteException {
        executeSQL(SQL_DELETE, new IdStatementBinder(id));
    }

    @Override
    public List<Menu> getAllMenu() throws SQLiteException {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }

    @Override
    public Menu getMenu(long id) throws SQLiteException {
        IdStatementBinder binder = new IdStatementBinder(id);
        Menu menu = queryOne(SQL_SELECT_BY_ID, binder, rowMapper);
        return menu;
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }
    
    /* ---------- SQL ---------- */
    private static final String TABLE_NAME = "menu";
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
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.NAME).appendWhereId().build();
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();
    private static final String SQL_DELETE = new DeleteSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 2).build();
    
    private static final RowMapper<Menu> rowMapper = new RowMapper<Menu>() {

        @Override
        public Menu map(SQLiteStatement stmt) throws SQLiteException {
            Menu menu = new Menu();
            menu.setId(stmt.columnLong(0));
            menu.setName(stmt.columnString(1));
            return menu;
        }
    };
    
    private static class MenuBinder implements StatementBinder {
        private final Menu menu;

        public MenuBinder(Menu menu) {
            super();
            this.menu = menu;
        }
        
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, menu.getId());
            stmt.bind(2, menu.getName());
        }
    }
    private static class UpdateBinder implements StatementBinder {
        private final Menu menu;

        public UpdateBinder(Menu menu) {
            super();
            this.menu = menu;
        }
        
        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, menu.getName());
            stmt.bind(2, menu.getId());
        }
    }
}
