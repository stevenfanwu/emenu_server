/**
 * @(#)PayedOrderVO.java, Aug 23, 2013. 
 *
 */
package com.cloudstone.emenu.data.vo;

import com.cloudstone.emenu.data.Bill;

/**
 * @author xuhongfeng
 */
public class PayedOrderVO extends OrderVO {
    private Bill bill;

    public PayedOrderVO() {
        super();
    }

    public PayedOrderVO(OrderVO order, Bill bill) {
        super(order);
        setBill(bill);
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

}
