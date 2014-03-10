
package com.cloudstone.emenu.storage.db;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.TableStat;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

@Repository
public class TableStatDb extends SQLiteDb implements ITableStatDb {

    private static final String TABLE_NAME = "tablestat";

    @Override
    public TableStat get(EmenuContext context, long day) {
        DayBinder binder = new DayBinder(day, context.getRestaurantId());
        return queryOne(context, SQL_SELECT_BY_DAY, binder, rowMapper);
    }

    @Override
    public void add(EmenuContext context, TableStat stat) {
        stat.setId(genId(context));
        stat.setRestaurantId(context.getRestaurantId());
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
        AVE_PERSON("avePerson"), AVE_ORDER("aveOrder"),
        CUSTOMER_COUNT("customerCount"), TABLE_NAME("tableName"), TIPS("tips"),
        CREATED_TIME("createdTime"), UPDATE_TIME("updatetime"), DELETED("deleted"),
        RESTAURANT_ID("restaurantId");

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
            .append(Column.AVE_PERSON, DataType.REAL, "NOT NULL")
            .append(Column.AVE_ORDER, DataType.REAL, "NOT NULL")
            .append(Column.CUSTOMER_COUNT, DataType.INTEGER, "NOT NULL")
            .append(Column.TABLE_NAME, DataType.TEXT, "NOT NULL")
            .append(Column.TIPS, DataType.REAL, "NOT NULL")
            .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
            .append(Column.RESTAURANT_ID, DataType.INTEGER, "NOT NULL").build();

    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 14).build();

    private static class StatBinder implements StatementBinder {
        private final TableStat stat;

        public StatBinder(TableStat stat) {
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
            stmt.bind(6, stat.getAvePerson());
            stmt.bind(7, stat.getAveOrder());
            stmt.bind(8, stat.getCustomerCount());
            stmt.bind(9, stat.getTableName());
            stmt.bind(10, stat.getTips());
            stmt.bind(11, stat.getCreatedTime());
            stmt.bind(12, stat.getUpdateTime());
            stmt.bind(13, stat.isDeleted() ? 1 : 0);
            stmt.bind(14, stat.getRestaurantId());
        }
    }

    private static final String SQL_SELECT_BY_DAY = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.DAY)
        .appendWhereRestaurantId().build();

    private static final RowMapper<TableStat> rowMapper = new RowMapper<TableStat>() {

        @Override
        public TableStat map(SQLiteStatement stmt) throws SQLiteException {
            TableStat stat = new TableStat();
            stat.setId(stmt.columnInt(0));
            stat.setDay(stmt.columnLong(1));
            stat.setIncome(stmt.columnDouble(2));
            stat.setCount(stmt.columnInt(3));
            stat.setDiscount(stmt.columnDouble(4));
            stat.setAvePerson(stmt.columnDouble(5));
            stat.setAveOrder(stmt.columnDouble(6));
            stat.setCustomerCount(stmt.columnInt(7));
            stat.setTableName(stmt.columnString(8));
            stat.setTips(stmt.columnDouble(9));
            stat.setCreatedTime(stmt.columnLong(10));
            stat.setUpdateTime(stmt.columnLong(11));
            stat.setDeleted(stmt.columnInt(12) == 1);
            return stat;
        }
    };

    private static final class DayBinder implements StatementBinder {
        private final long day;
        private final int restaurantId;

        public DayBinder(long day, int restaurantId) {
            super();
            this.day = day;
            this.restaurantId = restaurantId;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, day);
            stmt.bind(2, restaurantId);
        }
    }
}
