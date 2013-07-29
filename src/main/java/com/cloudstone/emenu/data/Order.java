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
}
