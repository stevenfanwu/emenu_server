/**
 * @(#)IOrderService.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.PayType;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderService {
    public void addOrder(DbTransaction trans, Order order);
    public Order getOrder(int orderId);
    public void addOrderDish(DbTransaction trans, OrderDish orderDish);
    public void updateOrderDish(DbTransaction trans, OrderDish orderDish);
    public void updateOrder(DbTransaction trans, Order order);
    public List<Dish> listDishes(int orderId);
    public List<OrderDish> listOrderDish(int orderId);
    
    public List<PayType> listPayTypes();
    
    public void addBill(DbTransaction trans, Bill bill);
    public List<Bill> listBills();
    public Bill getBill(int id);
    public Bill getBillByOrderId(int orderId);

    public List<Order> getDailyOrders(long startTime, long endTime);

}
