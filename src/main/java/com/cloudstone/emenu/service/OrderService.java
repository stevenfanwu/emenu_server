/**
 * @(#)OrderService.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.PayType;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IBillDb;
import com.cloudstone.emenu.storage.db.IOrderDb;
import com.cloudstone.emenu.storage.db.IOrderDishDb;
import com.cloudstone.emenu.storage.db.IPayTypeDb;
import com.cloudstone.emenu.storage.db.util.DbTransaction;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 *
 */
@Service
public class OrderService extends BaseService implements IOrderService {
    @Autowired
    private IBillDb billDb;
    @Autowired
    private IOrderDb orderDb;
    @Autowired
    private IOrderDishDb orderDishDb;
    @Autowired
    private IPayTypeDb payTypeDb;
    
    @Autowired
    private IMenuService menuService;
    
    @Override
    public Bill getBillByOrderId(int orderId) {
        try {
            return billDb.getByOrderId(orderId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public Order getOrder(int orderId) {
        try {
            return orderDb.get(orderId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void addOrder(DbTransaction trans, Order order) {
        try {
            orderDb.add(trans, order);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void addOrderDish(DbTransaction trans, OrderDish orderDish) {
        try {
            orderDishDb.add(trans, orderDish);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void updateOrderDish(DbTransaction trans, OrderDish orderDish) {
        orderDishDb.update(trans, orderDish);
    }
    
    @Override
    public List<Dish> listDishes(int orderId) {
        try {
            List<OrderDish> relations = orderDishDb.listOrderDish(orderId);
            DataUtils.filterDeleted(relations);
            List<Dish> dishes = new ArrayList<Dish>();
            for (OrderDish r:relations) {
                int dishId = r.getDishId();
                Dish dish = menuService.getDish(dishId);
                if (dish != null) {
                    dishes.add(dish);
                }
            }
            return dishes;
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public List<OrderDish> listOrderDish(int orderId) {
        try {
            return orderDishDb.listOrderDish(orderId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public List<PayType> listPayTypes() {
        try {
            return payTypeDb.getAllPayType();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void addBill(DbTransaction trans, Bill bill) {
        try {
            billDb.add(trans, bill);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Bill> listBills() {
        try {
            return billDb.listBills();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public Bill getBill(int id) {
        try {
            return billDb.get(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void updateOrder(DbTransaction trans, Order order) {
        orderDb.update(trans, order);
    }

    @Override
    public List<Order> getDailyOrders(long startTime, long endTime) {
        List<Order> orders = null;
        try {
             orders = orderDb.getOrdersByTime(startTime, endTime);
             List<Bill> bills = billDb.getBillsByTime(startTime, endTime);
             DataUtils.filterDeleted(bills);
             for(Bill bill : bills) {
                 orders.add(bill.getOrder());
             }
             DataUtils.filterDeleted(orders);
             return sortByTime(orders);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Bill> getDailyBills(long startTime, long endTime) {
        List<Bill> bills = new ArrayList<Bill>();
        try {
             bills.addAll(billDb.getBillsByTime(startTime, endTime));
             DataUtils.filterDeleted(bills);
             return bills;
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
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

    private List<Order> sortByTime(List<Order> orders) {
        Collections.sort(orders, ORDER_COMPARATOR);
        return orders;
    }
}
