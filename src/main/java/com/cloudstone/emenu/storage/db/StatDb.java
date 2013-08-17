
package com.cloudstone.emenu.storage.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.DailyStat;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.NameStatementBinder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.storage.db.util.UpdateSqlBuilder;

@Repository
public class StatDb extends SQLiteDb implements IStatDb {

    private static final String TABLE_NAME = "stat";

    private static final Logger LOG = LoggerFactory.getLogger(StatDb.class);

    @Override
    public DailyStat get(long time) {
        NameStatementBinder binder = new NameStatementBinder("" + time);
        try {
            return queryOne(SQL_SELECT_BY_TIME, binder, rowMapper);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void update(DailyStat stat) {
        try {
            executeSQL(null, SQL_UPDATE, new UpdateBinder(stat));
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void add(DailyStat stat) {
        try {
            stat.setId(genId());
            StatBinder binder = new StatBinder(stat);
            executeSQL(null, SQL_INSERT, binder);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }

    /* ---------- SQL ---------- */
    private static enum Column {
        ID("id"), TIME("day"), INCOME("income"), CUSTOMER_COUNT("customerCount"), TABLE_COUNT(
                "tableCount"), TABLE_RATE("tableRate"), CREATED_TIME("createdTime"), UPDATE_TIME(
                "time"), DELETED("deleted");

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
            .append(Column.TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.INCOME, DataType.REAL, "NOT NULL")
            .append(Column.CUSTOMER_COUNT, DataType.INTEGER, "NOT NULL")
            .append(Column.TABLE_COUNT, DataType.INTEGER, "NOT NULL")
            .append(Column.TABLE_RATE, DataType.REAL, "NOT NULL")
            .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.DELETED, DataType.INTEGER, "NOT NULL").build();

    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 9).build();

    private static class StatBinder implements StatementBinder {
        private final DailyStat stat;

        public StatBinder(DailyStat stat) {
            super();
            this.stat = stat;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, stat.getId());
            stmt.bind(2, stat.getTime());
            stmt.bind(3, stat.getIncome());
            stmt.bind(4, stat.getCustomerCount());
            stmt.bind(5, stat.getTableCount());
            stmt.bind(6, stat.getTableRate());
            stmt.bind(7, stat.getCreatedTime());
            stmt.bind(8, stat.getUpdateTime());
            stmt.bind(9, stat.isDeleted() ? 1 : 0);
        }
    }

    private static class UpdateBinder implements StatementBinder {
        private final DailyStat stat;

        public UpdateBinder(DailyStat stat) {
            super();
            this.stat = stat;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, stat.getTime());
            stmt.bind(2, stat.getIncome());
            stmt.bind(3, stat.getCustomerCount());
            stmt.bind(4, stat.getTableCount());
            stmt.bind(5, stat.getTableRate());
            stmt.bind(6, stat.getCreatedTime());
            stmt.bind(7, stat.getUpdateTime());
            stmt.bind(8, stat.isDeleted() ? 1 : 0);
            stmt.bind(9, stat.getId());
        }
    }

    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME).appendWhereId()
            .build();

    private static final String SQL_SELECT_BY_TIME = new SelectSqlBuilder(TABLE_NAME)
            .appendWhereTime().build();

    private static final RowMapper<DailyStat> rowMapper = new RowMapper<DailyStat>() {

        @Override
        public DailyStat map(SQLiteStatement stmt) throws SQLiteException {
            DailyStat stat = new DailyStat();
            stat.setId(stmt.columnInt(0));
            stat.setTime(stmt.columnLong(1));
            stat.setIncome(stmt.columnDouble(2));
            stat.setCustomerCount(stmt.columnInt(3));
            stat.setTableCount(stmt.columnInt(4));
            stat.setTableRate(stmt.columnDouble(5));
            stat.setCreatedTime(stmt.columnLong(6));
            stat.setUpdateTime(stmt.columnLong(7));
            stat.setDeleted(stmt.columnInt(8) == 1);
            return stat;
        }
    };

    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
            .appendSetValue(Column.TIME)
            .appendSetValue(Column.INCOME)
            .appendSetValue(Column.CUSTOMER_COUNT)
            .appendSetValue(Column.TABLE_COUNT)
            .appendSetValue(Column.TABLE_RATE)
            .appendSetValue(Column.CREATED_TIME)
            .appendSetValue(Column.UPDATE_TIME)
            .appendSetValue(Column.DELETED)
            .appendWhereId()
            .build();

}
