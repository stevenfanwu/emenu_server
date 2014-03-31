/**
 * @(#)Order.java, Jul 28, 2013. 
 *
 */
package com.cloudstone.emenu.data;

import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 */
public class Order extends IEntity {
    private double originPrice;
    private double price;
    private int tableId;
    private int userId;
    private int customerNumber;
    private int status;

    public Order() {
        super();
    }

    public Order(Order order) {
        super(order);
        setId(order.getId());
        setOriginPrice(DataUtils.calMoney(order.getOriginPrice()));
        setPrice(DataUtils.calMoney(order.getPrice()));
        setTableId(order.getTableId());
        setCustomerNumber(order.getCustomerNumber());
        setStatus(order.getStatus());
    }

    public double getOriginPrice() {
        return originPrice;
    }

    public void setOriginPrice(double originPrice) {
        this.originPrice = originPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
