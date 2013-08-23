/**
 * @(#)BaseData.java, 2013-7-6. 
 * 
 */

package com.cloudstone.emenu.data;

/**
 * @author carelife
 */
public class BaseStat extends BaseData {
    private int id;

    // 当天日期
    private long day;

    // 总收入
    private double income;

    // 总单数 or 翻桌次数 or 总份数
    private int count;

    // 优惠总额
    private double discount;

    public BaseStat() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDay() {
        return day;
    }

    public void setDay(long day) {
        this.day = day;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getIncome() {
        return income;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDiscount() {
        return discount;
    }
}
