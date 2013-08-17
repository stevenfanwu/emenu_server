/**
 * @(#)Order.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class Order extends IEntity {
    private double originPrice;
    private double price;
    private int tableId;
    private int customerNumber;
    
    public Order() {
        super();
    }
    
    public Order(Order order) {
        super(order);
        setId(order.getId());
        setOriginPrice(order.getOriginPrice());
        setPrice(order.getPrice());
        setTableId(order.getTableId());
        setCustomerNumber(order.getCustomerNumber());
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

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }
}
