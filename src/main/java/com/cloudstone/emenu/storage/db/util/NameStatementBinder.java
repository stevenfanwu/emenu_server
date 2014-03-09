/**
 * @(#)NameStatementBinder.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author xuhongfeng
 *
 */
public class NameStatementBinder implements StatementBinder {
    private final String name;

    private final int restaurantId;
    
    public NameStatementBinder(String name, int restaurantId) {
        super();
        this.name = name;
        this.restaurantId = restaurantId;
    }

    @Override
    public void onBind(SQLiteStatement stmt) throws SQLiteException {
        stmt.bind(1, name);
        stmt.bind(2, restaurantId);
    }

}
