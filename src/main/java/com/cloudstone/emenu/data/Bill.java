/**
 * @(#)Bill.java, Aug 1, 2013. 
 *
 */
package com.cloudstone.emenu.data;

import com.cloudstone.emenu.data.vo.OrderVO;

/**
 * @author xuhongfeng
 */
public class Bill extends IEntity {
    private int orderId;
    private double cost;
    private double discount;
    private double tip;
    private double invoicePrice;
    private boolean invoice;
    private int[] discountDishIds;
    private int payType;
    private String remarks;
    private OrderVO order;
    private double coupons;
    private int vipId;
    private double vipCost;

    public double getVipCost() {
        return vipCost;
    }

    public void setVipCost(double vipCost) {
        this.vipCost = vipCost;
    }

    public double getCoupons() {
        return coupons;
    }

    public void setCoupons(double coupons) {
        this.coupons = coupons;
    }

    public int getVipId() {
        return vipId;
    }

    public void setVipId(int vipId) {
        this.vipId = vipId;
    }

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

    public double getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(double invoicePrice) {
        this.invoicePrice = invoicePrice;
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

    public OrderVO getOrder() {
        return order;
    }

    public void setOrder(OrderVO order) {
        this.order = order;
    }
}
