/**
 * @(#)OrderDishVO.java, Jul 30, 2013. 
 *
 */
package com.cloudstone.emenu.data.vo;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 */
public class OrderDishVO extends Dish {
    // private static final Logger LOG = LoggerFactory.getLogger(OrderDishVO.class);

    private double number;
    private String[] remarks;
    private int orderStatus;
    private double totalCost;
    private int orderId;
    private String unitLabel;
    private String typeLabel;//赠，加，退，空

    public OrderDishVO() {
        super();
    }

    public OrderDishVO(Dish dish) {
        super(dish);
        setUnitLabel(Const.DishUnit.getLabel(dish.getUnit()));
    }

    public static OrderDishVO create(OrderDish r, Dish dish, int orderId) {
        OrderDishVO o = new OrderDishVO(dish);
        o.setNumber(DataUtils.round(r.getNumber(), 1));
        o.setRemarks(r.getRemarks());
        o.setOrderStatus(r.getStatus());
        o.setTotalCost(DataUtils.calMoney(o.getNumber() * o.getPrice()));
        o.setOrderId(orderId);
        o.setUnitLabel(Const.DishUnit.getLabel(dish.getUnit()));
        o.setTypeLabel(Const.TypeUnit.getLabel(r.getType()));
        return o;
    }

    public OrderDish toOrderDish() {
        OrderDish r = new OrderDish();
        r.setDishId(getId());
        r.setOrderId(orderId);
        r.setNumber(number);
        r.setRemarks(remarks);
        r.setPrice(getPrice());
        return r;
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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUnitLabel() {
        return unitLabel;
    }

    public void setUnitLabel(String unitLabel) {
        this.unitLabel = unitLabel;
    }

    public String getTypeLabel() {
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

}
