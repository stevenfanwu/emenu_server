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
    private int backCount;
    
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
    
    public int getBackCount() {
        return backCount;
    }

    public void setBackCount(int backCount) {
        this.backCount = backCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
