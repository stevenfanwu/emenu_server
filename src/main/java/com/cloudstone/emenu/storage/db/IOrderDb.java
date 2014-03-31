/**
 * @(#)IOrderDb.java, Jul 28, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Order;

/**
 * @author xuhongfeng
 */
public interface IOrderDb {
    public void add(EmenuContext context, Order order);

    public void update(EmenuContext context, Order order);

    public Order get(EmenuContext context, int id);

    public List<Order> getOrdersByTime(EmenuContext context, long startTime, long endTime);

    public Order getOldestOrder(EmenuContext context);

    public void delete(EmenuContext context, int id);
}
