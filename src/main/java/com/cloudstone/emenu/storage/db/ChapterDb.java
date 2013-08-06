/**
 * @(#)ChapterDb.java, 2013-7-10. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.Chapter;
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
public class ChapterDb extends SQLiteDb implements IChapterDb {
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public Chapter getChapterByName(String name) throws SQLiteException {
        return getByName(name, rowMapper);
    }
    
    @Override
    public List<Chapter> listChapters(int menuId) throws SQLiteException {
        GetByMenuIdBinder binder = new GetByMenuIdBinder(menuId);
        return query(SQL_SELECT_BY_MENU_ID, binder, rowMapper);
    }
    
    @Override
    public List<Chapter> listChapters(int[] ids) throws SQLiteException {
        String sql = new SelectSqlBuilder(TABLE_NAME).appendWhereIdIn(ids).build();
        return query(sql, StatementBinder.NULL, rowMapper);
    }

    @Override
    public void addChapter(Chapter chapter) throws SQLiteException {
        chapter.setId(genId());
        executeSQL(SQL_INSERT, new ChapterBinder(chapter), null);
    }

    @Override
    public void updateChapter(Chapter chapter) throws SQLiteException {
        executeSQL(SQL_UPDATE, new UpdateBinder(chapter), null);
    }

    @Override
    public void deleteChapter(int id) throws SQLiteException {
        delete(id);
    }

    @Override
    public List<Chapter> getAllChapter() throws SQLiteException {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }

    @Override
    public Chapter getChapter(int id) throws SQLiteException {
        IdStatementBinder binder = new IdStatementBinder(id);
        Chapter chapter = queryOne(SQL_SELECT_BY_ID, binder, rowMapper);
        return chapter;
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }
    
    /* ---------- SQL ---------- */
    private static final String TABLE_NAME = "chapter";
    private static enum Column {
        ID("id"), NAME("name"), MENU_ID("menuId"),
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

    private static final String COL_DEF = new ColumnDefBuilder()
        .append(Column.ID, DataType.INTEGER, "NOT NULL PRIMARY KEY")
        .append(Column.NAME, DataType.TEXT, "NOT NULL")
        .append(Column.MENU_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
        .build();
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.NAME).appendSetValue(Column.MENU_ID)
        .appendSetValue(Column.CREATED_TIME).appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId().build();
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();
    private static final String SQL_SELECT_BY_MENU_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.MENU_ID).build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 6).build();
    
    private static final RowMapper<Chapter> rowMapper = new RowMapper<Chapter>() {

        @Override
        public Chapter map(SQLiteStatement stmt) throws SQLiteException {
            Chapter chapter = new Chapter();
            chapter.setId(stmt.columnInt(0));
            chapter.setName(stmt.columnString(1));
            chapter.setMenuId(stmt.columnInt(2));
            chapter.setCreatedTime(stmt.columnLong(3));
            chapter.setUpdateTime(stmt.columnLong(4));
            chapter.setDeleted(stmt.columnInt(5) == 1);
            return chapter;
        }
    };
    
    private static class ChapterBinder implements StatementBinder {
        private final Chapter chapter;

        public ChapterBinder(Chapter chapter) {
            super();
            this.chapter = chapter;
        }
        
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, chapter.getId());
            stmt.bind(2, chapter.getName());
            stmt.bind(3, chapter.getMenuId());
            stmt.bind(4, chapter.getCreatedTime());
            stmt.bind(5, chapter.getUpdateTime());
            stmt.bind(6, chapter.isDeleted() ? 1 : 0);
        }
    }
    private static class UpdateBinder implements StatementBinder {
        private final Chapter chapter;

        public UpdateBinder(Chapter chapter) {
            super();
            this.chapter = chapter;
        }
        
        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, chapter.getName());
            stmt.bind(2, chapter.getMenuId());
            stmt.bind(3, chapter.getCreatedTime());
            stmt.bind(4, chapter.getUpdateTime());
            stmt.bind(5, chapter.isDeleted() ? 1 : 0);
            stmt.bind(6, chapter.getId());
        }
    }
    
    private static class GetByMenuIdBinder implements StatementBinder {
        private final int menuId;

        public GetByMenuIdBinder(int menuId) {
            super();
            this.menuId = menuId;
        }


        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, menuId);
        }
    }
}
