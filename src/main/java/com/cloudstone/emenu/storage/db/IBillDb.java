/**
 * @(#)IBillDb.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface IBillDb {
    public void add(DbTransaction trans, Bill bill) ;
    public List<Bill> listBills() ;
    public Bill get(int id) ;
    public Bill getByOrderId(int orderId) ;
    public List<Bill> getBillsByTime(long startTime, long endTime) ;

}
