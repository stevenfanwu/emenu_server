
package com.cloudstone.emenu.storage.db;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.UserStat;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

@Repository
public class UserStatDb extends SQLiteDb implements IUserStatDb {

    private static final String TABLE_NAME = "userstat";

    @Override
    public UserStat get(EmenuContext context, long day) {
        DayBinder binder = new DayBinder(day, context.getRestaurantId());
        return queryOne(context, SQL_SELECT_BY_DAY, binder, rowMapper);
    }

    @Override
    public void add(EmenuContext context, UserStat stat) {
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
        CUSTOMER_COUNT("customerCount"), USER_NAME("userName"), 
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
            .append(Column.USER_NAME, DataType.TEXT, "NOT NULL")
            .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
            .append(Column.RESTAURANT_ID, DataType.INTEGER, "NOT NULL").build();

    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 13).build();

    private static class StatBinder implements StatementBinder {
        private final UserStat stat;

        public StatBinder(UserStat stat) {
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
            stmt.bind(9, stat.getUserName());
            stmt.bind(10, stat.getCreatedTime());
            stmt.bind(11, stat.getUpdateTime());
            stmt.bind(12, stat.isDeleted() ? 1 : 0);
            stmt.bind(13, stat.getRestaurantId());
        }
    }

    private static final String SQL_SELECT_BY_DAY = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.DAY)
        .appendWhereRestaurantId().build();

    private static final RowMapper<UserStat> rowMapper = new RowMapper<UserStat>() {

        @Override
        public UserStat map(SQLiteStatement stmt) throws SQLiteException {
            UserStat stat = new UserStat();
            stat.setId(stmt.columnInt(0));
            stat.setDay(stmt.columnLong(1));
            stat.setIncome(stmt.columnDouble(2));
            stat.setCount(stmt.columnInt(3));
            stat.setDiscount(stmt.columnDouble(4));
            stat.setAvePerson(stmt.columnDouble(5));
            stat.setAveOrder(stmt.columnDouble(6));
            stat.setCustomerCount(stmt.columnInt(7));
            stat.setUserName(stmt.columnString(8));
            stat.setCreatedTime(stmt.columnLong(9));
            stat.setUpdateTime(stmt.columnLong(10));
            stat.setDeleted(stmt.columnInt(11) == 1);
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
