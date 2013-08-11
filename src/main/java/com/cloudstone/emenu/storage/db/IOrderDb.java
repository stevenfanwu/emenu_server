/**
 * @(#)IOrderDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderDb {
    public void add(Order order) throws SQLiteException;
    public void update(DbTransaction trans, Order order);
    public Order get(int id) throws SQLiteException;
}
