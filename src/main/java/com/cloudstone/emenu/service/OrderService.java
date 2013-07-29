/**
 * @(#)OrderService.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.exception.ServerError;

/**
 * @author xuhongfeng
 *
 */
@Service
public class OrderService extends BaseService implements IOrderService {

    @Override
    public void addOrder(Order order) {
        try {
            orderDb.add(order);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void addOrderDish(OrderDish orderDish) {
        try {
            orderDishDb.add(orderDish);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
}
