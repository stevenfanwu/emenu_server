/**
 * @(#)IDb.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;


/**
 * @author xuhongfeng
 *
 */
public interface IDb {
    public int getMaxId() ;
    public String getTableName();
    public void delete(int id) ;
}
