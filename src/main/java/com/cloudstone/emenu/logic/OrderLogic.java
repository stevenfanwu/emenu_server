/**
 * @(#)OrderLogic.java, Jul 29, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;


/**
 * @author xuhongfeng
 *
 */
@Component
public class OrderLogic extends BaseLogic {
    public Order getOrder(int orderId) {
        return orderService.getOrder(orderId);
    }
    
    public List<Dish> listDishes(int orderId) {
        return orderService.listDishes(orderId);
    }
}
