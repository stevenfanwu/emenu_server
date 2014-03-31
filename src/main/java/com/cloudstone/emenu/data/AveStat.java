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

    public void setAveOrder(double aveOrder) {
        this.aveOrder = aveOrder;
    }

    public double getAveOrder() {
        return aveOrder;
    }

    public void setAvePerson(double avePerson) {
        this.avePerson = avePerson;
    }

    public double getAvePerson() {
        return avePerson;
    }
}
