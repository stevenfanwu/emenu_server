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
import com.cloudstone.emenu.storage.db.util.DbTransactionHelper;

/**
 * @author xuhongfeng
 *
 */
public interface IOrderService {
    public void addOrder(Order order);
    public Order getOrder(int orderId);
    public void addOrderDish(OrderDish orderDish);
    public List<Dish> listDishes(int orderId);
    public List<OrderDish> listOrderDish(int orderId);
    
    public List<PayType> listPayTypes();
    
    public void addBill(Bill bill, DbTransactionHelper trans);
    public List<Bill> listBills();
    public Bill getBill(int id);
    public Bill getBillByOrderId(int orderId);

}
