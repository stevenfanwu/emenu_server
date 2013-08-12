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

import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 */
@Component
public class OrderWraper extends BaseWraper {
    private static final Logger LOG = LoggerFactory.getLogger(OrderWraper.class);

    public OrderVO wrap(Order order) {
        LOG.info(JsonUtils.toJson(order));
        Table table = tableLogic.get(order.getTableId());
        LOG.info(JsonUtils.toJson(table));
        List<OrderDish> relations = orderLogic.listOrderDishes(order.getId());
        List<Dish> dishes = orderLogic.listDishes(order.getId());
        return OrderVO.create(order, table, relations, dishes);
    }

    public List<OrderVO> wrap(List<Order> orders) {
        List<OrderVO> orderVOs = new ArrayList<OrderVO>();
        for (Order order : orders) {
            LOG.info(JsonUtils.toJson(order));
            Table table = tableLogic.get(order.getTableId());
            LOG.info(JsonUtils.toJson(table));
            List<OrderDish> relations = orderLogic.listOrderDishes(order.getId());
            List<Dish> dishes = orderLogic.listDishes(order.getId());
            orderVOs.add(OrderVO.create(order, table, relations, dishes));
        }
        return orderVOs;

    }
}
