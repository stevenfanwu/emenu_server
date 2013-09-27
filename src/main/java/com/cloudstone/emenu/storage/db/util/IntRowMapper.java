/**
 * @(#)IntRowMapper.java, Sep 27, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author xuhongfeng
 *
 */
public class IntRowMapper implements RowMapper<Integer> {

    @Override
    public Integer map(SQLiteStatement stmt) throws SQLiteException {
        return stmt.columnInt(0);
    }

}
