/**
 * @(#)IOrderDishDb.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.OrderDish;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderDishDb {
    public void add(EmenuContext context, OrderDish data) ;
    public void update(EmenuContext context, OrderDish data);
    public List<OrderDish> listOrderDish(EmenuContext context, int orderId) ;
}
