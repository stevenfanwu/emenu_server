/**
 * @(#)OrderLogic.java, Jul 29, 2013. 
 * 
 */

package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.constant.Const.TableStatus;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.PayType;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.OrderDishVO;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.exception.NotFoundException;
import com.cloudstone.emenu.exception.PreconditionFailedException;
import com.cloudstone.emenu.storage.db.IBillDb;
import com.cloudstone.emenu.storage.db.IOrderDb;
import com.cloudstone.emenu.storage.db.IOrderDishDb;
import com.cloudstone.emenu.storage.db.IPayTypeDb;
import com.cloudstone.emenu.util.DataUtils;
import com.cloudstone.emenu.util.UnitUtils;
import com.cloudstone.emenu.wrap.OrderWraper;

/**
 * @author xuhongfeng
 */
@Component
public class OrderLogic extends BaseLogic {
    private static final Logger LOG = LoggerFactory.getLogger(OrderLogic.class);

    @Autowired
    private IBillDb billDb;

    @Autowired
    private IOrderDb orderDb;

    @Autowired
    private IOrderDishDb orderDishDb;

    @Autowired
    private IPayTypeDb payTypeDb;

    @Autowired
    private TableLogic tableLogic;

    @Autowired
    private MenuLogic menuLogic;

    @Autowired
    private PrinterLogic printerLogic;

    @Autowired
    protected OrderWraper orderWraper;

    public void addOrderDish(EmenuContext context, OrderDish orderDish) {
        long now = System.currentTimeMillis();
        orderDish.setCreatedTime(now);
        orderDish.setUpdateTime(now);
        orderDishDb.add(context, orderDish);
    }

    public void updateOrderDish(EmenuContext context, OrderDish orderDish) {
        orderDish.setUpdateTime(System.currentTimeMillis());
        orderDishDb.update(context, orderDish);
    }

    public Order getOldestOrder(EmenuContext context) {
        return orderDb.getOldestOrder(context);
    }

    public Order getOrder(EmenuContext context, int orderId) {
        return orderDb.get(context, orderId);
    }

    public void updateOrder(EmenuContext context, Order order) {
        // TODO check order
        order.setUpdateTime(System.currentTimeMillis());
        orderDb.update(context, order);
    }

    public void addOrder(EmenuContext context, Order order) {
        // TODO Check Order
        long now = System.currentTimeMillis();
        order.setUpdateTime(now);
        order.setCreatedTime(now);
        orderDb.add(context, order);
    }

    public void deleteOrder(EmenuContext context, int orderId) {
        orderDb.delete(context, orderId);
    }

    public void deleteOrderDish(EmenuContext context, int orderId, int dishId) {
        orderDishDb.delete(context, orderId, dishId);
    }

    public List<Dish> listDishes(EmenuContext context, int orderId) {
        List<OrderDish> relations = orderDishDb.listOrderDish(context, orderId);
        DataUtils.filterDeleted(relations);
        List<Dish> dishes = new ArrayList<Dish>();
        for (OrderDish r : relations) {
            int dishId = r.getDishId();
            Dish dish = menuLogic.getDish(context, dishId);
            if (dish != null) {
                dishes.add(dish);
            }
        }
        return dishes;
    }

    public List<OrderDish> listOrderDishes(EmenuContext context, int orderId) {
        List<OrderDish> datas = orderDishDb.listOrderDish(context, orderId);
        DataUtils.filterDeleted(datas);
        return datas;
    }

    public List<PayType> listPayTypes(EmenuContext context) {
        List<PayType> datas = payTypeDb.getAllPayType(context);
        DataUtils.filterDeleted(datas);
        return datas;
    }

    public List<Bill> listBills(EmenuContext context) {
        List<Bill> datas = billDb.listBills(context);
        DataUtils.filterDeleted(datas);
        return datas;
    }

    public Bill payBill(EmenuContext context, Bill bill, User user) {

        if (getBillByOrderId(context, bill.getOrderId()) != null) {
            throw new DataConflictException("请勿重复提交订单");
        }
        Order order = getOrder(context, bill.getOrderId());
        if (order == null || order.isDeleted()) {
            throw new BadRequestError();
        }
        Table table = tableLogic.get(context, order.getTableId());
        if (table == null || table.getStatus() != TableStatus.OCCUPIED) {
            throw new BadRequestError();
        }
        order.setStatus(Const.OrderStatus.PAYED);
        order.setPrice(bill.getCost());
        OrderVO orderVO = orderWraper.wrap(context, order);
        bill.setOrder(orderVO);
        long now = System.currentTimeMillis();
        bill.setCreatedTime(now);
        bill.setUpdateTime(now);
        // Start transaction
        context.beginTransaction(dataSource);
        try {
            orderDb.update(context, order);
            billDb.add(context, bill);
            table.setStatus(TableStatus.EMPTY);
            table.setOrderId(0);
            tableLogic.update(context, table);
            tableLogic.setCustomerNumber(context, table.getId(), 0);

            try {
                printerLogic.printBill(context, bill, user);
            } catch (Exception e) {
                throw new PreconditionFailedException("打印失败", e);
            }

            context.commitTransaction();
            // End transaction
        } finally {
            context.closeTransaction(dataSource);
        }
        tableLogic.setCustomerNumber(context, table.getId(), 0);
        return billDb.get(context, bill.getId());
    }

    public Bill getBillByOrderId(EmenuContext context, int orderId) {
        return billDb.getByOrderId(context, orderId);
    }

    public List<Order> getDailyOrders(EmenuContext context, long time) {
        long currentDay = (long) (time / UnitUtils.DAY);
        long startTime = currentDay * UnitUtils.DAY;
        long endTime = startTime + UnitUtils.DAY;

        return getOrders(context, startTime, endTime);
    }

    public List<Order> getOrders(EmenuContext context, long startTime, long endTime) {
        List<Order> orders = orderDb.getOrdersByTime(context, startTime, endTime);
        DataUtils.filterDeleted(orders);
        Collections.sort(orders, ORDER_COMPARATOR);
        return orders;
    }

    public List<Bill> getBills(EmenuContext context, long startTime, long endTime) {
        if (startTime <= 0 || endTime <= 0 || startTime > endTime)
            throw new BadRequestError();
        List<Bill> bills = billDb.getBillsByTime(context, startTime, endTime);
        DataUtils.filterDeleted(bills);
        return bills;
    }

    private static final Comparator<Order> ORDER_COMPARATOR = new Comparator<Order>() {

        @Override
        public int compare(Order order1, Order order2) {
            if (order1.getCreatedTime() > order2.getCreatedTime())
                return -1;
            else if (order1.getCreatedTime() == order2.getCreatedTime())
                return 0;
            else
                return 1;
        }

    };
    
    public Order cancelDish(EmenuContext context, int orderId, int dishId, int count) {
        Order order = getOrder(context, orderId);
        if (order==null || order.isDeleted()) {
            throw new NotFoundException("该订单不存在");
        }
        List<OrderDish> dishes = listOrderDishes(context, orderId);
        OrderDish dish = null;
        for (OrderDish d:dishes) {
            if (d.getDishId() == dishId) {
                dish = d;
                break;
            }
        }
        if (dish==null) {
            throw new NotFoundException("订单中不存在该菜品");
        }
        if (dish.getNumber()<count) {
            throw new PreconditionFailedException("菜品数量错误");
        }
        context.beginTransaction(dataSource);
        try {
            //update orderDish
            if (dish.getNumber() > count) {
                dish.setNumber(dish.getNumber() - count);
                updateOrderDish(context, dish);
            } else {
                deleteOrderDish(context, orderId, dishId);
            }
            
            //update order
            order.setPrice(order.getPrice() - count*dish.getPrice());
            order.setOriginPrice(order.getOriginPrice() - count*dish.getPrice());
            updateOrder(context, order);
            
            context.commitTransaction();
        } finally {
            context.closeTransaction(dataSource);
        }
        return order;
    }
    
    public Order submit(EmenuContext context, Order order, Table table, List<OrderDishVO> dishes) {
        int customerNumber = tableLogic.getCustomerNumber(context, table.getId());
        context.beginTransaction(dataSource);
        try {
            //save order
            order.setTableId(table.getId());
            order.setCustomerNumber(customerNumber);
            double originPrice = 0;
            for (OrderDishVO r:dishes) {
                Dish dish = menuLogic.getDish(context, r.getId());
                if (dish == null || dish.isDeleted()) {
                    throw new NotFoundException("存在非法菜品");
                }
                originPrice += dish.getPrice() * r.getNumber();
                r.setPrice(dish.getPrice());
            }
            order.setOriginPrice(originPrice);
            order.setUserId(context.getLoginUserId());
            addOrder(context, order);
            
            //save order dish
            for (OrderDishVO r:dishes) {
                r.setOrderId(order.getId());
                r.setStatus(Const.OrderDishStatus.ORDERED);
                addOrderDish(context, r.toOrderDish());
            }
            
            //update table
            table.setOrderId(order.getId());
            tableLogic.update(context, table);
            context.commitTransaction();
            return order;
        } finally {
            context.closeTransaction(dataSource);
        }
    }
}
