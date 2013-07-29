/**
 * @(#)OrderDish.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

import com.cloudstone.emenu.storage.db.RelationDb.Relation;

/**
 * @author xuhongfeng
 *
 */
public class OrderDish extends Relation {
    private double number;
    private double price;
    private String[] remarks;
    private int status;
    
    public int getOrderId() {
        return getId1();
    }
    public void setOrderId(int orderId) {
        setId1(orderId);
    }
    public int getDishId() {
        return getId2();
    }
    public void setDishId(int dishId) {
        setId2(dishId);
    }
    public double getNumber() {
        return number;
    }
    public void setNumber(double number) {
        this.number = number;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String[] getRemarks() {
        return remarks;
    }
    public void setRemarks(String[] remarks) {
        this.remarks = remarks;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
