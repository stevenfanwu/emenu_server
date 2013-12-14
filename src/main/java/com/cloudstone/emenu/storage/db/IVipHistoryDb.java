package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Vip;
import com.cloudstone.emenu.data.VipHistory;

/**
 * 
 * @author carelife
 *
 */
public interface IVipHistoryDb extends IDb {
    public void add(EmenuContext context, VipHistory vip) ;
    public List<VipHistory> get(EmenuContext context, int vipid) ;
    public List<VipHistory> getAll(EmenuContext context) ;
}
