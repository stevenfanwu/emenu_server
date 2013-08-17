/**
 * @(#)OrderDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.DbTransaction;
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
    public void add(DbTransaction trans, Order order) {
        order.setId(genId());
        OrderBinder binder = new OrderBinder(order);
        executeSQL(trans, SQL_INSERT, binder);
    }
    
    @Override
    public Order get(int id) {
        IdStatementBinder binder = new IdStatementBinder(id);
        return queryOne(SQL_SELECT_BY_ID, binder, rowMapper);
    }
    
    @Override
    public void update(DbTransaction trans, Order order) {
        executeSQL(trans, SQL_UPDATE, new UpdateBinder(order));
    }

    @Override
    public List<Order> getOrdersByTime(long startTime, long endTime) {
        TimeStatementBinder binder = new TimeStatementBinder(startTime, endTime);
        return query(SQL_SELECT_BY_TIME, binder, rowMapper);
    }

    /* ---------- Override ---------- */
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable() {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }

    /* ---------- SQL ---------- */
    private static final String TABLE_NAME = "`order`";
    
    private static enum Column {
        ID("id"), ORIGIN_PRICE("originPrice"), PRICE("price"), TABLE_ID("tableId"),
        CUSTOMER_NUMBER("customerNumber"),
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
        .append(Column.ORIGIN_PRICE, DataType.REAL, "NOT NULL")
        .append(Column.PRICE, DataType.REAL, "NOT NULL")
        .append(Column.TABLE_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.CUSTOMER_NUMBER, DataType.INTEGER, "NOT NULL")
        .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
        .build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 8).build();
    
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
            stmt.bind(5, order.getCustomerNumber());
            stmt.bind(6, order.getCreatedTime());
            stmt.bind(7, order.getUpdateTime());
            stmt.bind(8, order.isDeleted() ? 1 : 0);
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
            stmt.bind(4, order.getCustomerNumber());
            stmt.bind(5, order.getCreatedTime());
            stmt.bind(6, order.getUpdateTime());
            stmt.bind(7, order.isDeleted() ? 1 : 0);
            stmt.bind(8, order.getId());
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
            order.setCustomerNumber(stmt.columnInt(4));
            order.setCreatedTime(stmt.columnLong(5));
            order.setUpdateTime(stmt.columnLong(6));
            order.setDeleted(stmt.columnInt(7) == 1);
            return order;
        }
    };
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.ORIGIN_PRICE)
        .appendSetValue(Column.PRICE)
        .appendSetValue(Column.TABLE_ID)
        .appendSetValue(Column.CUSTOMER_NUMBER)
        .appendSetValue(Column.CREATED_TIME)
        .appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId().build();

    private static final String SQL_SELECT_BY_TIME = new SelectSqlBuilder(TABLE_NAME)
        .append(" WHERE createdTime>? ")
        .append(" AND createdTime<?").build();
}
