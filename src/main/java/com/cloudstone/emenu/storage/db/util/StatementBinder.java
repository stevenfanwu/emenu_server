/**
 * @(#)StatementBinder.java, 2013-6-23. 
 *
 */
package com.cloudstone.emenu.storage.db.util;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author xuhongfeng
 */
public interface StatementBinder {
    public static StatementBinder NULL = new StatementBinder() {
        @Override
        public void onBind(SQLiteStatement stmt) {
        }
    };

    public void onBind(SQLiteStatement stmt) throws SQLiteException;
}
