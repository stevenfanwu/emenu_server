/**
 * @(#)IDb.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import com.almworks.sqlite4java.SQLiteException;

/**
 * @author xuhongfeng
 *
 */
public interface IDb {
    public int getMaxId() throws SQLiteException;
    public String getTableName();
}
