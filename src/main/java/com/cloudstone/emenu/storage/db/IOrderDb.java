/**
 * @(#)IOrderDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderDb {
    public void add(DbTransaction trans, Order order) ;
    public void update(DbTransaction trans, Order order);
    public Order get(int id) ;
    public List<Order> getOrdersByTime(long startTime, long endTime) ;
}
