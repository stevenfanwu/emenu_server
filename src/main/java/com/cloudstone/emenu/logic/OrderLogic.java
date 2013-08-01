/**
 * @(#)OrderLogic.java, Jul 29, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.PayType;


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
    
    public List<OrderDish> listOrderDishes(int orderId) {
        return orderService.listOrderDish(orderId);
    }
    
    public List<PayType> listPayTypes() {
        return orderService.listPayTypes();
    }
    
    public List<Bill> listBills() {
        return orderService.listBills();
    }
    
    public Bill addBill(Bill bill) {
        orderService.addBill(bill);
        return orderService.getBill(bill.getId());
    }
    
    public Bill getBillByOrderId(int orderId) {
        return orderService.getBillByOrderId(orderId);
    }
}
