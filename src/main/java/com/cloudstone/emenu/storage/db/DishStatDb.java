
package com.cloudstone.emenu.storage.db;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishStat;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

@Repository
public class DishStatDb extends SQLiteDb implements IDishStatDb {

    private static final String TABLE_NAME = "dishstat";

    @Override
    public DishStat get(EmenuContext context, String dishName, long day) {
        DayBinder binder = new DayBinder(dishName, day);
        return queryOne(context, SQL_SELECT_BY_DAY, binder, rowMapper);
    }

    @Override
    public void add(EmenuContext context, DishStat stat) {
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
        DISH_NAME("dishName"), DISH_CLASS("dishClass"), BACK_COUNT("backCount"), PRICE("price"),
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
            .append(Column.DISH_NAME, DataType.TEXT, "NOT NULL")
            .append(Column.DISH_CLASS, DataType.TEXT, "NOT NULL")
            .append(Column.BACK_COUNT, DataType.INTEGER, "NOT NULL")
            .append(Column.PRICE, DataType.REAL, "NOT NULL")
            .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.DELETED, DataType.INTEGER, "NOT NULL").build();

    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 12).build();

    private static class StatBinder implements StatementBinder {
        private final DishStat stat;

        public StatBinder(DishStat stat) {
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
            stmt.bind(6, stat.getDishName());
            stmt.bind(7, stat.getDishClass());
            stmt.bind(8, stat.getBackCount());
            stmt.bind(9, stat.getPrice());
            stmt.bind(10, stat.getCreatedTime());
            stmt.bind(11, stat.getUpdateTime());
            stmt.bind(12, stat.isDeleted() ? 1 : 0);
        }
    }

    private static final String SQL_SELECT_BY_DAY = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.DISH_NAME)
        .appendWhere(Column.DAY).build();

    private static final RowMapper<DishStat> rowMapper = new RowMapper<DishStat>() {

        @Override
        public DishStat map(SQLiteStatement stmt) throws SQLiteException {
            DishStat stat = new DishStat();
            stat.setId(stmt.columnInt(0));
            stat.setDay(stmt.columnLong(1));
            stat.setIncome(stmt.columnDouble(2));
            stat.setCount(stmt.columnInt(3));
            stat.setDiscount(stmt.columnDouble(4));
            stat.setDishName(stmt.columnString(5));
            stat.setDishClass(stmt.columnString(6));
            stat.setBackCount(stmt.columnInt(7));
            stat.setPrice(stmt.columnDouble(8));
            stat.setCreatedTime(stmt.columnLong(9));
            stat.setUpdateTime(stmt.columnLong(10));
            stat.setDeleted(stmt.columnInt(11) == 1);
            return stat;
        }
    };

    private static final class DayBinder implements StatementBinder {
        private final long day;
        private final String dishName;

        public DayBinder(String dishName, long day) {
            super();
            this.day = day;
            this.dishName = dishName;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, dishName);
            stmt.bind(2, day);
        }
    }
}
