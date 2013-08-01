/**
 * @(#)OrderDishVO.java, Jul 30, 2013. 
 * 
 */
package com.cloudstone.emenu.data.vo;

import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.OrderDish;

/**
 * @author xuhongfeng
 *
 */
public class OrderDishVO extends Dish {
    private double number;
    private String[] remarks;
    private int orderStatus;
    private double totalCost;

    public OrderDishVO() {
        super();
    }

    public OrderDishVO(Dish dish) {
        super(dish);
    }
    
    public static OrderDishVO create(OrderDish r, Dish dish) {
        OrderDishVO o = new OrderDishVO(dish);
        o.setNumber(r.getNumber());
        o.setRemarks(r.getRemarks());
        o.setOrderStatus(r.getStatus());
        //TODO round
        o.setTotalCost(o.getNumber()*o.getPrice());
        return o;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public String[] getRemarks() {
        return remarks;
    }

    public void setRemarks(String[] remarks) {
        this.remarks = remarks;
    }

    public int getOrderStatus() {
        return this.orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }
}
