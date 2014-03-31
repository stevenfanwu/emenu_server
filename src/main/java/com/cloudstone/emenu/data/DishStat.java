/**
 * @(#)DailyStat.java, Aug 6, 2013. 
 *
 */

package com.cloudstone.emenu.data;

/**
 * @author carelife
 */
public class DishStat extends BaseStat {
    //
    private String dishName;

    // 菜品分类
    private String dishClass;

    // 退菜数量
    private double backCount;

    // 份数
    private double count;

    // 单价
    private double price;

    public DishStat() {
        super();
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishClass() {
        return dishClass;
    }

    public void setDishClass(String dishClass) {
        this.dishClass = dishClass;
    }

    public double getBackCount() {
        return backCount;
    }

    public void setBackCount(double backCount) {
        this.backCount = backCount;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double dishCount) {
        this.count = backCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
