/**
 * @(#)IOrderService.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderService {
    public void addOrder(Order order);
    public void addOrderDish(OrderDish orderDish);
}
