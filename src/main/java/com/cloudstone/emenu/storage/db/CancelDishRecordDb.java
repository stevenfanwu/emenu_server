/**
 * @(#)CancelDishRecordDb.java, Aug 26, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.CancelDishRecord;
import com.cloudstone.emenu.storage.db.BillDb.OrderIdBinder;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SQLBuilder;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class CancelDishRecordDb extends SQLiteDb implements ICancelDishRecordDb {
    @Override
    public void add(EmenuContext context, CancelDishRecord record) {
        record.setId(genId(context));
        executeSQL(context, SQL_INSERT, new RecordBinder(record));
    }

    @Override
    public int getCount(EmenuContext context, final int dishId,
            final long startTime, final long endTime) {
        String sql = new SQLBuilder().append("SELECT COUNT(*) FROM ")
            .append(TABLE_NAME)
            .appendWhere(Column.DISH_ID)
            .append(" AND time>=?")
            .append(" AND time<=?")
            .build();
        return queryInt(context, sql, new StatementBinder() {
            
            @Override
            public void onBind(SQLiteStatement stmt) throws SQLiteException {
                stmt.bind(1, dishId);
                stmt.bind(2, startTime);
                stmt.bind(3, endTime);
            }
        });
    }
    
    @Override
    public List<CancelDishRecord> listByOrderId(EmenuContext context,
            int orderId) {
        return query(context, SQL_SELECT_BY_ORDER, new OrderIdBinder(orderId), ROW_MAPPER);
    }
    
    /* ---------------------- */
    

    private static final String TABLE_NAME = "cancelDishRecord";
    
    private static final String SQL_SELECT_BY_ORDER = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.ORDER_ID).build();

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable(EmenuContext context) {
        checkCreateTable(context, TABLE_NAME, COL_DEF);
    }

    private static enum Column {
        ID("id"), TIME("time"), DISH_ID("dishId"), COUNT("count"), ORDER_ID("orderId"),
        CREATED_TIME("createdTime"), UPDATE_TIME("updateTime"), DELETED("deleted");
        
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
        .append(Column.DISH_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.COUNT, DataType.INTEGER, "NOT NULL")
        .append(Column.ORDER_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
        .build();
    
    private static final String SQL_INSERT =
            new InsertSqlBuilder(TABLE_NAME, 8).build();
    
    private static class RecordBinder implements StatementBinder {
        private final CancelDishRecord record;

        public RecordBinder(CancelDishRecord record) {
            super();
            this.record = record;
        }
        
        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, record.getId());
            stmt.bind(2, record.getTime());
            stmt.bind(3, record.getDishId());
            stmt.bind(4, record.getCount());
            stmt.bind(5, record.getOrderId());
            stmt.bind(6, record.getCreatedTime());
            stmt.bind(7, record.getUpdateTime());
            stmt.bind(8, record.isDeleted() ? 1 : 0);
        }
    }
    
    private static final RowMapper<CancelDishRecord> ROW_MAPPER = new RowMapper<CancelDishRecord>() {
        @Override
        public CancelDishRecord map(SQLiteStatement stmt)
                throws SQLiteException {
            CancelDishRecord record = new CancelDishRecord();
            record.setId(stmt.columnInt(0));
            record.setTime(stmt.columnLong(1));
            record.setDishId(stmt.columnInt(2));
            record.setCount(stmt.columnInt(3));
            record.setOrderId(stmt.columnInt(4));
            record.setCreatedTime(stmt.columnLong(5));
            record.setUpdateTime(stmt.columnLong(6));
            record.setDeleted(stmt.columnInt(7) == 1);
            return record;
        }
    };
}
