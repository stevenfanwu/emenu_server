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
public class ChapterDb extends SQLiteDb implements IChapterDb {

    @Override
    public void addChapter(Chapter chapter) throws SQLiteException {
        executeSQL(SQL_INSERT, new ChapterBinder(chapter));
    }

    @Override
    public void updateChapter(Chapter chapter) throws SQLiteException {
        executeSQL(SQL_UPDATE, new UpdateBinder(chapter));
    }

    @Override
    public void deleteChapter(long id) throws SQLiteException {
        executeSQL(SQL_DELETE, new IdStatementBinder(id));
    }

    @Override
    public List<Chapter> getAllChapter() throws SQLiteException {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }

    @Override
    public Chapter getChapter(long id) throws SQLiteException {
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
    
    private static final RowMapper<Chapter> rowMapper = new RowMapper<Chapter>() {

        @Override
        public Chapter map(SQLiteStatement stmt) throws SQLiteException {
            Chapter chapter = new Chapter();
            chapter.setId(stmt.columnLong(0));
            chapter.setName(stmt.columnString(1));
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
            stmt.bind(2, chapter.getId());
        }
    }
}
