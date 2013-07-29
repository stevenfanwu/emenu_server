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
    
    @Override
    public void add(final OrderDish data) throws SQLiteException {
        add(new InsertBinder(data.getOrderId(), data.getDishId()) {
            @Override
            protected void bindOthers(SQLiteStatement stmt)
                    throws SQLiteException {
                stmt.bind(3, data.getNumber());
                stmt.bind(4, data.getPrice());
                stmt.bind(5, CollectionUtils.join(data.getRemarks(), SPLIT_REMARKS));
                stmt.bind(6, data.getStatus());
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
                COL_NUMBER, COL_PRICE, COL_REMARKS, COL_STATUS
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
            return data;
        }
    };
}
