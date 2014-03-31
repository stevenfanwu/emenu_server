/**
 * @(#)CancelDishVO.java, Sep 15, 2013. 
 *
 */
package com.cloudstone.emenu.data.vo;

import com.cloudstone.emenu.data.DishRecord;
import com.cloudstone.emenu.data.Dish;

/**
 * @author xuhongfeng
 */
public class CancelDishVO extends Dish {
    private long time;
    private int count;
    private int orderId;

    public CancelDishVO() {
        super();
    }

    public CancelDishVO(CancelDishVO dish) {
        super(dish);
    }

    public CancelDishVO(Dish dish, DishRecord record) {
        this(dish, record.getOrderId(), record.getTime(), record.getCount());
    }

    public CancelDishVO(Dish dish, int orderId, long time, int count) {
        super(dish);
        this.time = time;
        this.count = count;
        this.orderId = orderId;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
