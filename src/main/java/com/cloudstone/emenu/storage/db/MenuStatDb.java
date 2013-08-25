
package com.cloudstone.emenu.storage.db;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.MenuStat;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

@Repository
public class MenuStatDb extends SQLiteDb implements IMenuStatDb {

    private static final String TABLE_NAME = "dishstat";

    @Override
    public MenuStat get(EmenuContext context, long day) {
        DayBinder binder = new DayBinder(day);
        return queryOne(context, SQL_SELECT_BY_DAY, binder, rowMapper);
    }

    @Override
    public void add(EmenuContext context, MenuStat stat) {
        stat.setId(genId(context));
        StatBinder binder = new StatBinder(stat);
        executeSQL(context, SQL_INSERT, binder);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable(EmenuContext context) {
        checkCreateTable(context, TABLE_NAME, COL_DEF);
    }

    /* ---------- SQL ---------- */
    private static enum Column {
        ID("id"), DAY("day"), INCOME("income"), COUNT("count"), DISCOUNT("discount"),
        CHAPTER_NAME("chapterName"),
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
            .append(Column.DAY, DataType.INTEGER, "NOT NULL")
            .append(Column.INCOME, DataType.REAL, "NOT NULL")
            .append(Column.COUNT, DataType.INTEGER, "NOT NULL")
            .append(Column.DISCOUNT, DataType.REAL, "NOT NULL")
            .append(Column.CHAPTER_NAME, DataType.TEXT, "NOT NULL")
            .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.DELETED, DataType.INTEGER, "NOT NULL").build();

    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 9).build();

    private static class StatBinder implements StatementBinder {
        private final MenuStat stat;

        public StatBinder(MenuStat stat) {
            super();
            this.stat = stat;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, stat.getId());
            stmt.bind(2, stat.getDay());
            stmt.bind(3, stat.getIncome());
            stmt.bind(4, stat.getCount());
            stmt.bind(5, stat.getDiscount());
            stmt.bind(6, stat.getChapterName());
            stmt.bind(7, stat.getCreatedTime());
            stmt.bind(8, stat.getUpdateTime());
            stmt.bind(9, stat.isDeleted() ? 1 : 0);
        }
    }

    private static final String SQL_SELECT_BY_DAY = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.DAY).build();

    private static final RowMapper<MenuStat> rowMapper = new RowMapper<MenuStat>() {

        @Override
        public MenuStat map(SQLiteStatement stmt) throws SQLiteException {
            MenuStat stat = new MenuStat();
            stat.setId(stmt.columnInt(0));
            stat.setDay(stmt.columnLong(1));
            stat.setIncome(stmt.columnDouble(2));
            stat.setCount(stmt.columnInt(3));
            stat.setDiscount(stmt.columnDouble(4));
            stat.setChapterName(stmt.columnString(5));
            stat.setCreatedTime(stmt.columnLong(6));
            stat.setUpdateTime(stmt.columnLong(7));
            stat.setDeleted(stmt.columnInt(8) == 1);
            return stat;
        }
    };

    private static final class DayBinder implements StatementBinder {
        private final long day;

        public DayBinder(long day) {
            super();
            this.day = day;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, day);
        }
    }
}
