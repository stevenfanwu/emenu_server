/**
 * @(#)OrderDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.storage.db.util.TimeStatementBinder;
import com.cloudstone.emenu.storage.db.util.UpdateSqlBuilder;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class OrderDb extends SQLiteDb implements IOrderDb {
    @Override
    public void add(EmenuContext context, Order order) {
        order.setId(genId(context));
        OrderBinder binder = new OrderBinder(order);
        executeSQL(context, SQL_INSERT, binder);
    }
    
    @Override
    public Order get(EmenuContext context, int id) {
        IdStatementBinder binder = new IdStatementBinder(id);
        return queryOne(context, SQL_SELECT_BY_ID, binder, rowMapper);
    }
    
    @Override
    public void update(EmenuContext context, Order order) {
        executeSQL(context, SQL_UPDATE, new UpdateBinder(order));
    }

    @Override
    public List<Order> getOrdersByTime(EmenuContext context, long startTime, long endTime) {
        TimeStatementBinder binder = new TimeStatementBinder(startTime, endTime);
        return query(context, SQL_SELECT_BY_TIME, binder, rowMapper);
    }
    
    @Override
    public Order getOldestOrder(EmenuContext context) {
        return queryOne(context, SQL_SELECT_OLDEST, StatementBinder.NULL, rowMapper);
    }

    /* ---------- Override ---------- */
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable(EmenuContext context) {
        checkCreateTable(context, TABLE_NAME, COL_DEF);
    }

    /* ---------- SQL ---------- */
    private static final String TABLE_NAME = "`order`";
    
    private static enum Column {
        ID("id"), ORIGIN_PRICE("originPrice"), PRICE("price"), TABLE_ID("tableId"),
        USER_ID("userId"), CUSTOMER_NUMBER("customerNumber"), STATUS("status"),
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
        .append(Column.ORIGIN_PRICE, DataType.REAL, "NOT NULL")
        .append(Column.PRICE, DataType.REAL, "NOT NULL")
        .append(Column.TABLE_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.USER_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.CUSTOMER_NUMBER, DataType.INTEGER, "NOT NULL")
        .append(Column.STATUS, DataType.INTEGER, "NOT NULL")
        .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
        .build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 10).build();
    
    private static class OrderBinder implements StatementBinder {
        private final Order order;

        public OrderBinder(Order order) {
            super();
            this.order = order;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, order.getId());
            stmt.bind(2, order.getOriginPrice());
            stmt.bind(3, order.getPrice());
            stmt.bind(4, order.getTableId());
            stmt.bind(5, order.getUserId());
            stmt.bind(6, order.getCustomerNumber());
            stmt.bind(7, order.getStatus());
            stmt.bind(8, order.getCreatedTime());
            stmt.bind(9, order.getUpdateTime());
            stmt.bind(10, order.isDeleted() ? 1 : 0);
        }
    }
    
    private static class UpdateBinder implements StatementBinder {
        private final Order order;

        public UpdateBinder(Order order) {
            super();
            this.order = order;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, order.getOriginPrice());
            stmt.bind(2, order.getPrice());
            stmt.bind(3, order.getTableId());
            stmt.bind(4, order.getUserId());
            stmt.bind(5, order.getCustomerNumber());
            stmt.bind(6, order.getStatus());
            stmt.bind(7, order.getCreatedTime());
            stmt.bind(8, order.getUpdateTime());
            stmt.bind(9, order.isDeleted() ? 1 : 0);
            stmt.bind(10, order.getId());
        }
    }
    
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    
    private static final RowMapper<Order> rowMapper = new RowMapper<Order>() {
        
        @Override
        public Order map(SQLiteStatement stmt) throws SQLiteException {
            Order order = new Order();
            order.setId(stmt.columnInt(0));
            order.setOriginPrice(stmt.columnDouble(1));
            order.setPrice(stmt.columnDouble(2));
            order.setTableId(stmt.columnInt(3));
            order.setUserId(stmt.columnInt(4));
            order.setCustomerNumber(stmt.columnInt(5));
            order.setStatus(stmt.columnInt(6));
            order.setCreatedTime(stmt.columnLong(7));
            order.setUpdateTime(stmt.columnLong(8));
            order.setDeleted(stmt.columnInt(9) == 1);
            return order;
        }
    };
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.ORIGIN_PRICE)
        .appendSetValue(Column.PRICE)
        .appendSetValue(Column.TABLE_ID)
        .appendSetValue(Column.USER_ID)
        .appendSetValue(Column.CUSTOMER_NUMBER)
        .appendSetValue(Column.STATUS)
        .appendSetValue(Column.CREATED_TIME)
        .appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId().build();

    private static final String SQL_SELECT_BY_TIME = new SelectSqlBuilder(TABLE_NAME)
        .append(" WHERE createdTime>? ")
        .append(" AND createdTime<?").build();
    
    private static final String SQL_SELECT_OLDEST = new SelectSqlBuilder(TABLE_NAME)
        .append(" ORDER BY createdTime ASC LIMIT 1").build();
}
