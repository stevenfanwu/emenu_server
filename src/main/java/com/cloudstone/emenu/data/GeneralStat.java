/**
 * @(#)GeneralStat.java, Aug 6, 2013. 
 *
 */

package com.cloudstone.emenu.data;

/**
 * @author carelife
 */
public class GeneralStat extends AveStat {
    // 当天客流量
    private int customerCount;

    // 翻桌率
    private double tableRate;

    // 发票数量
    private int invoiceCount;

    // 发票总额
    private double invoiceAmount;

    // 服务费总额
    private double tips;

    // 代金券总额
    private double coupons;

    private int count;

    public GeneralStat() {
        super();
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public int getInvoiceCount() {
        return invoiceCount;
    }

    public void setInvoiceCount(int invoiceCount) {
        this.invoiceCount = invoiceCount;
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

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public double getTips() {
        return tips;
    }

    public void setTips(double tips) {
        this.tips = tips;
    }

    public double getCoupons() {
        return coupons;
    }

    public void setCoupons(double coupons) {
        this.coupons = coupons;
    }
}
