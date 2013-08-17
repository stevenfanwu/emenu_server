/**
 * @(#)IDb.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;


/**
 * @author xuhongfeng
 *
 */
public interface IDb {
    public int getMaxId(EmenuContext context) ;
    public void delete(EmenuContext context, int id) ;
    public String getTableName();
}
