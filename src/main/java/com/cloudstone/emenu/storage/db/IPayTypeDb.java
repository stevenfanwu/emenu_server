/**
 * @(#)IPayTypeDb.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.PayType;

/**
 * @author xuhongfeng
 *
 */
public interface IPayTypeDb {
    public List<PayType> getAllPayType(EmenuContext context) ;
    public void addPayType(EmenuContext context, PayType payType) ;
}
