/**
 * @(#)IBillDb.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface IBillDb {
    public void add(Bill bill, DbTransaction trans) throws SQLiteException;
    public List<Bill> listBills() throws SQLiteException;
    public Bill get(int id) throws SQLiteException;
    public Bill getByOrderId(int orderId) throws SQLiteException;
}
