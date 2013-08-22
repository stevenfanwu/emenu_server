/**
 * @(#)OrderWraper.java, Jul 30, 2013. 
 * 
 */

package com.cloudstone.emenu.wrap;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.OrderVO;

/**
 * @author xuhongfeng
 */
@Component
public class OrderWraper extends BaseWraper {
    private static final Logger LOG = LoggerFactory.getLogger(OrderWraper.class);

    public OrderVO wrap(EmenuContext context, Order order) {
        Table table = tableLogic.get(context, order.getTableId());
        List<OrderDish> relations = orderLogic
                .listOrderDishes(context, order.getId());
        List<Dish> dishes = orderLogic
                .listDishes(context, order.getId());
        User user = userLogic.getUser(context, order.getUserId());
        return OrderVO.create(order, table, relations, dishes, user);
    }

    public List<OrderVO> wrap(EmenuContext context, List<Order> orders) {
        List<OrderVO> orderVOs = new ArrayList<OrderVO>();
        for (Order order : orders) {
            orderVOs.add(this.wrap(context, order));
        }
        return orderVOs;

    }
}
