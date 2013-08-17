/**
 * @(#)IOrderDishDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderDishDb {
    public void add(DbTransaction trans, OrderDish data) ;
    public void update(DbTransaction trans, OrderDish data);
    public List<OrderDish> listOrderDish(int orderId) ;
}
