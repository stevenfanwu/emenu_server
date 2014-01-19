/**
 * @(#)IBillDb.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Bill;

/**
 * @author xuhongfeng
 *
 */
public interface IBillDb {
    public void add(EmenuContext context, Bill bill) ;
    public List<Bill> listBills(EmenuContext context) ;
    public Bill get(EmenuContext context, int id) ;
    public Bill getByOrderId(EmenuContext context, int orderId) ;
    public List<Bill> getBillsByTime(EmenuContext context, long startTime, long endTime) ;

}
