/**
 * @(#)BillDb.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.SqlUtils;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class BillDb extends SQLiteDb implements IBillDb {
    
    @Override
    public Bill getByOrderId(int orderId) throws SQLiteException {
        return queryOne(SQL_SELECT_BY_ORDER_ID, new OrderIdBinder(orderId), rowMapper);
    }

    @Override
    public void add(Bill bill) throws SQLiteException {
        bill.setId(genId());
        executeSQL(SQL_INSERT, new BillBinder(bill));
    }

    @Override
    public List<Bill> listBills() throws SQLiteException {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }
    
    @Override
    public Bill get(int id) throws SQLiteException {
        IdStatementBinder binder = new IdStatementBinder(id);
        return queryOne(SQL_SELECT_BY_ID, binder, rowMapper);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }

    private static final String TABLE_NAME = "bill";
    
    private static enum Column {
        ID("id"), ORDER_ID("orderId"), COST("cost"), DISCOUNT("discount"),
        TIP("tip"), INVOICE("invoice"), DISCOUNT_DISH_IDS("discountDishIds"),
        PAY_TYPE("payType"), REMARKS("remarks");
        
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
        .append(Column.ORDER_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.COST, DataType.REAL, "NOT NULL")
        .append(Column.DISCOUNT, DataType.REAL, "NOT NULL")
        .append(Column.TIP, DataType.REAL, "NOT NULL")
        .append(Column.INVOICE, DataType.INTEGER, "NOT NULL")
        .append(Column.DISCOUNT_DISH_IDS, DataType.TEXT, "NOT NULL")
        .append(Column.PAY_TYPE, DataType.INTEGER, "NOT NULL")
        .append(Column.REMARKS, DataType.TEXT, "NOT NULL")
        .build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 9).build();
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();
    
    private static class BillBinder implements StatementBinder {
        private final Bill bill;

        public BillBinder(Bill bill) {
            super();
            this.bill = bill;
        }
        
        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, bill.getId());
            stmt.bind(2, bill.getOrderId());
            stmt.bind(3, bill.getCost());
            stmt.bind(4, bill.getDiscount());
            stmt.bind(5, bill.getTip());
            stmt.bind(6, bill.isInvoice() ? 1 : 0);
            stmt.bind(7, SqlUtils.idsToStr(bill.getDiscountDishIds()));
            stmt.bind(8, bill.getPayType());
            stmt.bind(9, bill.getRemarks());
        }
    }
    
    private static final RowMapper<Bill> rowMapper = new RowMapper<Bill>() {
        
        @Override
        public Bill map(SQLiteStatement stmt) throws SQLiteException {
            Bill bill = new Bill();
            bill.setId(stmt.columnInt(0));
            bill.setOrderId(stmt.columnInt(1));
            bill.setCost(stmt.columnDouble(2));
            bill.setDiscount(stmt.columnDouble(3));
            bill.setTip(stmt.columnDouble(4));
            bill.setInvoice(stmt.columnInt(5) == 1);
            bill.setDiscountDishIds(SqlUtils.strToIds(stmt.columnString(6)));
            bill.setPayType(stmt.columnInt(7));
            bill.setRemarks(stmt.columnString(8));
            
            return bill;
        }
    };
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    private static final String SQL_SELECT_BY_ORDER_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.ORDER_ID).build();
    private static class OrderIdBinder implements StatementBinder {
        private final int orderId;

        public OrderIdBinder(int orderId) {
            super();
            this.orderId = orderId;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, orderId);
        }
    }
}
