/**
 * @(#)Bill.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

import java.util.List;


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
    private BillArchive archive;
    
    public BillArchive getArchive() {
        return archive;
    }
    public void setArchive(BillArchive archive) {
        this.archive = archive;
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
    
    public static class BillArchive {
        private Order order;
        private Table table;
        private List<Dish> dishes;
        
        public static BillArchive build(Order order, Table table, List<Dish> dishes) {
            BillArchive archive = new BillArchive();
            archive.setDishes(dishes);
            archive.setOrder(order);
            archive.setTable(table);
            return archive;
        }
        
        public Order getOrder() {
            return order;
        }
        public void setOrder(Order order) {
            this.order = order;
        }
        public Table getTable() {
            return table;
        }
        public void setTable(Table table) {
            this.table = table;
        }
        public List<Dish> getDishes() {
            return dishes;
        }
        public void setDishes(List<Dish> dishes) {
            this.dishes = dishes;
        }
    }
}
