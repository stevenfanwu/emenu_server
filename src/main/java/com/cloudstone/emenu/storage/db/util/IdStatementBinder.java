/**
 * @(#)IdStatementBinder.java, 2013-6-23. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author xuhongfeng
 *
 */
public class IdStatementBinder implements StatementBinder {
    private final int id;

    public IdStatementBinder(int id) {
        super();
        this.id = id;
    }

    @Override
    public void onBind(SQLiteStatement stmt) throws SQLiteException {
        stmt.bind(1, id);
    }

}
