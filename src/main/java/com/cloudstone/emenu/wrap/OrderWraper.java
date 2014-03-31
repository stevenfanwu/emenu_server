/**
 * @(#)OrderWraper.java, Jul 30, 2013. 
 *
 */

package com.cloudstone.emenu.wrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.DishRecord;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.CancelDishVO;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.data.vo.PayedOrderVO;

/**
 * @author xuhongfeng
 */
@Component
public class OrderWraper extends BaseWraper {
//    private static final Logger LOG = LoggerFactory.getLogger(OrderWraper.class);

    @Autowired
    private DishWraper dishWraper;

    public OrderVO wrap(EmenuContext context, Order order, List<OrderDish> relations) {
        //table
        Table table = tableLogic.get(context, order.getTableId());

        //user
        User user = userLogic.getUser(context, order.getUserId());

        //dishes
        List<Dish> dishes = orderLogic
                .listDishes(context, order.getId(), relations);

        //cancel dishes
        List<DishRecord> records = recordLogic.listCancelDishRecords(context, order.getId());
        List<CancelDishVO> cancelDishes = dishWraper.wrapCancelDish(context, records);

        OrderVO o = new OrderVO(order, table, relations, dishes, user, cancelDishes);
        if (o.getStatus() == Const.OrderStatus.PAYED) {
            Bill bill = orderLogic.getBillByOrderId(context, order.getId());
            o = new PayedOrderVO(o, bill);
        }
        return o;
    }

    public OrderVO wrap(EmenuContext context, Order order) {
        List<OrderDish> relations = orderLogic
                .listOrderDishes(context, order.getId());
        return wrap(context, order, relations);
    }

    public List<OrderVO> wrap(EmenuContext context, List<Order> orders) {
        List<OrderVO> orderVOs = new ArrayList<OrderVO>();
        for (Order order : orders) {
            orderVOs.add(this.wrap(context, order));
        }
        return orderVOs;

    }
}
