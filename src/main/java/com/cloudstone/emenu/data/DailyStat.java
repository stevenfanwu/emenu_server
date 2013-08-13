/**
 * @(#)DailyStat.java, Aug 6, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class DailyStat extends BaseData {
    private int id;
    
    //当天 精度为天
    private long time;
    
    //当天总收入
    private double income;
    
    //当天有几桌用餐
    private int tableCount;
    
    //当天客流量
    private int customerCount;
    
    //翻桌率
    private double tableRate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getTableCount() {
        return tableCount;
    }

    public void setTableCount(int tableCount) {
        this.tableCount = tableCount;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public double getTableRate() {
        return tableRate;
    }

    public void setTableRate(double tableRate) {
        this.tableRate = tableRate;
    }
    
}
