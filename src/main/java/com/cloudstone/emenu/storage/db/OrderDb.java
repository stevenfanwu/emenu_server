/**
 * @(#)OrderDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class OrderDb extends SQLiteDb implements IOrderDb {
    @Override
    public void add(Order order) throws SQLiteException {
        order.setId(genId());
        OrderBinder binder = new OrderBinder(order);
        executeSQL(null, SQL_INSERT, binder);
    }
    
    @Override
    public Order get(int id) throws SQLiteException {
        IdStatementBinder binder = new IdStatementBinder(id);
        return queryOne(SQL_SELECT_BY_ID, binder, rowMapper);
    }

    /* ---------- Override ---------- */
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
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
        .append(Column.CUSTOMER_NUMBER, DataType.INTEGER, "NOT NULL")
        .append(Column.ORIGIN_PRICE, DataType.REAL, "NOT NULL")
        .append(Column.PRICE, DataType.REAL, "NOT NULL")
        .append(Column.TABLE_ID, DataType.INTEGER, "NOT NULL")
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
}
