/**
 * @(#)IOrderDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Order;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderDb {
    public void add(Order order) throws SQLiteException;
    public Order get(int id) throws SQLiteException;
}
