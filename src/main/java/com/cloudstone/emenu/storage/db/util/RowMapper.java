/**
 * @(#)RowMapper.java, 2013-6-23. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author xuhongfeng
 *
 */
public abstract interface RowMapper<T> {
    public T map(SQLiteStatement stmt) throws SQLiteException;
}
