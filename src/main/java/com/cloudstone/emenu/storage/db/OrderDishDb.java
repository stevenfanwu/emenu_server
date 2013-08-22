/**
 * @(#)OrderDishDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.storage.db.util.UpdateSqlBuilder;
import com.cloudstone.emenu.util.CollectionUtils;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class OrderDishDb extends RelationDb<OrderDish> implements IOrderDishDb {
    private static final String TABLE_NAME = "orderDish";
    
    private static final String SPLIT_REMARKS = "&&";
    
    private static final RelationDbColumn COL_NUMBER = new RelationDbColumn("number", DataType.REAL, false);
    private static final RelationDbColumn COL_PRICE = new RelationDbColumn("price", DataType.REAL, false);
    private static final RelationDbColumn COL_REMARKS = new RelationDbColumn("remarks", DataType.TEXT, false);
    private static final RelationDbColumn COL_STATUS = new RelationDbColumn("status", DataType.INTEGER, false);
    private static final RelationDbColumn COL_CREATED_TIME =
            new RelationDbColumn("createdTime", DataType.INTEGER, false);
    private static final RelationDbColumn COL_UPDATE_TIME =
            new RelationDbColumn("updateTime", DataType.INTEGER, false);
    private static final RelationDbColumn COL_DELETED =
            new RelationDbColumn("deleted", DataType.INTEGER, false);
    
    @Override
    public void add(EmenuContext context, final OrderDish data) {
        add(context, new InsertBinder(data.getOrderId(), data.getDishId()) {
            @Override
            protected void bindOthers(SQLiteStatement stmt)
                    throws SQLiteException {
                long now = System.currentTimeMillis();
                stmt.bind(3, data.getNumber());
                stmt.bind(4, data.getPrice());
                stmt.bind(5, CollectionUtils.join(data.getRemarks(), SPLIT_REMARKS));
                stmt.bind(6, data.getStatus());
                stmt.bind(7, now);
                stmt.bind(8, now);
                stmt.bind(9, 0);
            }
        });
    }
    
    @Override
    public void delete(EmenuContext context, int orderId, int dishId) {
        super.delete(context, orderId, dishId);
    }
    
    @Override
    public void update(EmenuContext context, OrderDish data) {
        executeSQL(context, SQL_UPDATE, new UpdateBinder(data));
    }
    
    @Override
    public List<OrderDish> listOrderDish(EmenuContext context, int orderId) {
        return listById1(context, orderId);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected RelationDbConfig onCreateConfig() {
        RelationDbColumn[] columns = new RelationDbColumn[] {
                COL_NUMBER, COL_PRICE, COL_REMARKS, COL_STATUS,
                COL_CREATED_TIME, COL_UPDATE_TIME, COL_DELETED
        };
        return new RelationDbConfig(TABLE_NAME, columns);
    }

    @Override
    protected RelationRowMapper<OrderDish> onCreateRowMapper() {
        return rowMapper;
    }

    private static RelationRowMapper<OrderDish> rowMapper = new RelationRowMapper<OrderDish>() {
        
        @Override
        protected OrderDish newRelation() {
            return new OrderDish();
        }
        
        @Override
        public OrderDish map(SQLiteStatement stmt) throws SQLiteException {
            OrderDish data = super.map(stmt);
            data.setNumber(stmt.columnDouble(2));
            data.setPrice(stmt.columnDouble(3));
            String strRemarks = stmt.columnString(4);
            data.setRemarks(strRemarks.split(SPLIT_REMARKS));
            data.setStatus(stmt.columnInt(5));
            data.setCreatedTime(stmt.columnLong(6));
            data.setUpdateTime(stmt.columnLong(7));
            data.setDeleted(stmt.columnInt(8) == 1);
            return data;
        }
    };
    
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(COL_NUMBER)
        .appendSetValue(COL_PRICE)
        .appendSetValue(COL_STATUS)
        .appendSetValue(COL_REMARKS)
        .appendSetValue(COL_CREATED_TIME)
        .appendSetValue(COL_UPDATE_TIME)
        .appendSetValue(COL_DELETED)
        .appendWhere(ID1)
        .appendWhere(ID2)
        .build();
    
    private static class UpdateBinder implements StatementBinder {
        private final OrderDish data;

        public UpdateBinder(OrderDish data) {
            super();
            this.data = data;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, data.getNumber());
            stmt.bind(2, data.getPrice());
            stmt.bind(3, data.getStatus());
            String remarks = "";
            if (data.getRemarks() != null) {
                remarks = CollectionUtils.join(data.getRemarks(), SPLIT_REMARKS);
            }
            stmt.bind(4, remarks);
            stmt.bind(5, data.getCreatedTime());
            stmt.bind(6, data.getUpdateTime());
            stmt.bind(7, data.isDeleted() ? 1 : 0);
            stmt.bind(8, data.getId1());
            stmt.bind(9, data.getId2());
        }
    }
}
