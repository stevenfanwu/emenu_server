/**
 * @(#)OrderWraper.java, Jul 30, 2013. 
 * 
 */
package com.cloudstone.emenu.wrap;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.vo.OrderVO;

/**
 * @author xuhongfeng
 *
 */
@Component
public class OrderWraper extends BaseWraper {

    public OrderVO wrap(Order order) {
        Table table = tableLogic.get(order.getTableId());
        List<OrderDish> relations = orderLogic.listOrderDishes(order.getId());
        List<Dish> dishes = orderLogic.listDishes(order.getId());
        return OrderVO.create(order, table, relations, dishes);
    }
}
