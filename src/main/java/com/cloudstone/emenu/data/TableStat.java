/**
 * @(#)DailyStat.java, Aug 6, 2013. 
 *
 */

package com.cloudstone.emenu.data;

/**
 * @author carelife
 */
public class TableStat extends AveStat {
    //
    private String tableName;

    // 当天客流量
    private int customerCount;

    // 服务费总额
    private double tips;

    private int count;

    public TableStat() {
        super();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public double getTips() {
        return tips;
    }

    public void setTips(double tips) {
        this.tips = tips;
    }
}
