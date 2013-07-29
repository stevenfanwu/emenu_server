/**
 * @(#)IOrderDishDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.OrderDish;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderDishDb {
    public void add(OrderDish data) throws SQLiteException;
    public List<OrderDish> listOrderDish(int orderId) throws SQLiteException;
}
