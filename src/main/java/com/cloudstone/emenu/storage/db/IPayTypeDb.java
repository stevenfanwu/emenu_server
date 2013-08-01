/**
 * @(#)IPayTypeDb.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.PayType;

/**
 * @author xuhongfeng
 *
 */
public interface IPayTypeDb {
    public List<PayType> getAllPayType() throws SQLiteException;
    public void addPayType(PayType payType) throws SQLiteException;
}
