/**
 * @(#)IOrderDishDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderDishDb {
    public void add(DbTransaction trans, OrderDish data) throws SQLiteException;
    public void update(DbTransaction trans, OrderDish data);
    public List<OrderDish> listOrderDish(int orderId) throws SQLiteException;
}
