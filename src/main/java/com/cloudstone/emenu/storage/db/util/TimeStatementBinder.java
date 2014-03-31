/**
 * @(#)IdStatementBinder.java, 2013-6-23. 
 *
 */
package com.cloudstone.emenu.storage.db.util;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * @author xuhongfeng
 */
public class TimeStatementBinder implements StatementBinder {
    private final long startTime;
    private final long endTime;
    private final int restaurantId;


    public TimeStatementBinder(long startTime, long endTime, int restaurantId) {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
        this.restaurantId = restaurantId;
    }

    @Override
    public void onBind(SQLiteStatement stmt) throws SQLiteException {
        stmt.bind(1, startTime);
        stmt.bind(2, endTime);
        stmt.bind(3, restaurantId);
    }

}
