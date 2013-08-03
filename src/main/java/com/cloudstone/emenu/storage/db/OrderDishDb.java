/**
 * @(#)OrderDishDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.OrderDish;
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
    public void add(final OrderDish data) throws SQLiteException {
        add(new InsertBinder(data.getOrderId(), data.getDishId()) {
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
    public List<OrderDish> listOrderDish(int orderId) throws SQLiteException {
        return listById1(orderId);
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
}
