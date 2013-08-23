/**
 * @(#)BaseData.java, 2013-7-6. 
 * 
 */

package com.cloudstone.emenu.data;

/**
 * @author carelife
 */
public class AveStat extends BaseStat {

    // 订单均价
    private double aveOrder;

    // 人均价格
    private double avePerson;

    public AveStat() {
        super();
    }

    public void setAveOrderPrice(double aveOrderPrice) {
        this.aveOrder = aveOrderPrice;
    }

    public double getAveOrderPrice() {
        return aveOrder;
    }

    public void setAvePersonPrice(double avePersonPrice) {
        this.avePerson = avePersonPrice;
    }

    public double getAvePersonPrice() {
        return avePerson;
    }
}
