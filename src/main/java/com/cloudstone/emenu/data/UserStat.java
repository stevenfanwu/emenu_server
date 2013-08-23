/**
 * @(#)DailyStat.java, Aug 6, 2013. 
 * 
 */

package com.cloudstone.emenu.data;

/**
 * @author carelife
 */
public class UserStat extends AveStat {
    //
    private String userName;

    // 当天客流量
    private int customerCount;

    public UserStat() {
        super();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

}
