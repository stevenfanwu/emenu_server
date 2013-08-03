/**
 * @(#)Bill.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class Bill extends IEntity {
    private int orderId;
    private double cost;
    private double discount;
    private double tip;
    private boolean invoice;
    private int[] discountDishIds;
    private int payType;
    private String remarks;
    
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public double getDiscount() {
        return discount;
    }
    public void setDiscount(double discount) {
        this.discount = discount;
    }
    public double getTip() {
        return tip;
    }
    public void setTip(double tip) {
        this.tip = tip;
    }
    public boolean isInvoice() {
        return invoice;
    }
    public void setInvoice(boolean invoice) {
        this.invoice = invoice;
    }
    public int[] getDiscountDishIds() {
        return discountDishIds;
    }
    public void setDiscountDishIds(int[] discountDishIds) {
        this.discountDishIds = discountDishIds;
    }
    public int getPayType() {
        return payType;
    }
    public void setPayType(int payType) {
        this.payType = payType;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
