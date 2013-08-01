/**
 * @(#)OrderService.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.PayType;
import com.cloudstone.emenu.exception.ServerError;

/**
 * @author xuhongfeng
 *
 */
@Service
public class OrderService extends BaseService implements IOrderService {
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
    
    @Override
    public List<Dish> listDishes(int orderId) {
        try {
            List<OrderDish> relations = orderDishDb.listOrderDish(orderId);
            List<Dish> dishes = new ArrayList<Dish>();
            for (OrderDish r:relations) {
                int dishId = r.getDishId();
                Dish dish = dishDb.get(dishId);
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
    public void addBill(Bill bill) {
        try {
            billDb.add(bill);
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
}
