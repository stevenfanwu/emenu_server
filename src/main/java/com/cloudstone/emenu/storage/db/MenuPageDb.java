/**
 * @(#)MenuPageDb.java, Jul 14, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.MenuPage;
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
@Repository
public class MenuPageDb extends SQLiteDb implements IMenuPageDb {
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void addMenuPage(MenuPage page) throws SQLiteException {
        page.setId(genId());
        executeSQL(SQL_INSERT, new MenuPageBinder(page));
    }

    @Override
    public void updateMenuPage(MenuPage page) throws SQLiteException {
        executeSQL(SQL_UPDATE, new UpdateBinder(page));
    }

    @Override
    public void deleteMenuPage(int id) throws SQLiteException {
        delete(id);
    }

    @Override
    public List<MenuPage> getAllMenuPage() throws SQLiteException {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }

    @Override
    public List<MenuPage> listMenuPages(int chapterId) throws SQLiteException {
        GetByChapterIdBinder binder = new  GetByChapterIdBinder(chapterId);
        return query(SQL_SELECT_BY_CHAPTER_ID, binder, rowMapper);
    }
    
    @Override
    public List<MenuPage> listMenuPages(int[] ids) throws SQLiteException {
        String sql = new SelectSqlBuilder(TABLE_NAME).appendWhereIdIn(ids).build();
        return query(sql, StatementBinder.NULL, rowMapper);
    }

    @Override
    public MenuPage getMenuPage(int id) throws SQLiteException {
        IdStatementBinder binder = new IdStatementBinder(id);
        MenuPage page = queryOne(SQL_SELECT_BY_ID, binder, rowMapper);
        return page;
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }

    /* --------- SQL ---------- */
    private static final String TABLE_NAME = "menuPage";
    private static enum Column {
        ID("id"), CHAPTER_ID("pageId"), DISH_COUNT("dishCount"),
        ORDINAL("ordinal"),
        CREATED_TIME("createdTime"), UPDATE_TIME("time"), DELETED("deleted");
        
        private final String str;
        private Column(String str) {
            this.str = str;
        }
        
        @Override
        public String toString() {
            return str;
        }
    }
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 7).build();
    
    private static final RowMapper<MenuPage> rowMapper = new RowMapper<MenuPage>() {

        @Override
        public MenuPage map(SQLiteStatement stmt) throws SQLiteException {
            MenuPage page = new MenuPage();
            page.setId(stmt.columnInt(0));
            page.setChapterId(stmt.columnInt(1));
            page.setDishCount(stmt.columnInt(2));
            page.setOrdinal(stmt.columnInt(3));
            page.setCreatedTime(stmt.columnLong(4));
            page.setUpdateTime(stmt.columnLong(5));
            page.setDeleted(stmt.columnInt(6) == 1);
            return page;
        }
    };
    
    private static class MenuPageBinder implements StatementBinder {
        private final MenuPage page;

        public MenuPageBinder(MenuPage page) {
            super();
            this.page = page;
        }
        
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, page.getId());
            stmt.bind(2, page.getChapterId());
            stmt.bind(3, page.getDishCount());
            stmt.bind(4, page.getOrdinal());
            stmt.bind(5, page.getCreatedTime());
            stmt.bind(6, page.getUpdateTime());
            stmt.bind(7, page.isDeleted() ? 1 : 0);
        }
    }
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.CHAPTER_ID).appendSetValue(Column.DISH_COUNT)
        .appendSetValue(Column.ORDINAL)
        .appendSetValue(Column.CREATED_TIME)
        .appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId().build();
    private static class UpdateBinder implements StatementBinder {
        private final MenuPage page;

        public UpdateBinder(MenuPage page) {
            super();
            this.page = page;
        }
        
        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, page.getChapterId());
            stmt.bind(2, page.getDishCount());
            stmt.bind(3, page.getOrdinal());
            stmt.bind(4, page.getCreatedTime());
            stmt.bind(5, page.getUpdateTime());
            stmt.bind(6, page.isDeleted() ? 1 : 0);
            stmt.bind(7, page.getId());
        }
    }
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME)
        .appendOrderBy(Column.ORDINAL, false)
        .build();
    private static class GetByChapterIdBinder implements StatementBinder {
        private final int chapterId;

        public GetByChapterIdBinder(int chapterId) {
            super();
            this.chapterId = chapterId;
        }


        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, chapterId);
        }
    }
    private static final String SQL_SELECT_BY_CHAPTER_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.CHAPTER_ID)
        .appendOrderBy(Column.ORDINAL, false)
        .build();
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    private static final String COL_DEF = new ColumnDefBuilder()
        .append(Column.ID, DataType.INTEGER, "NOT NULL PRIMARY KEY")
        .append(Column.CHAPTER_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.DISH_COUNT, DataType.INTEGER, "NOT NULL")
        .append(Column.ORDINAL, DataType.INTEGER, "NOT NULL")
        .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
        .build();
}
