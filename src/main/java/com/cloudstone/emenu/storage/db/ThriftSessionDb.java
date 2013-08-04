/**
 * @(#)ThriftSessionDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.ThriftSession;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.util.JsonUtils;
import com.cloudstone.emenu.util.UnitUtils;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class ThriftSessionDb extends JsonDb {
    private static final String TABLE_NAME = "thriftSession";
    
    private static final long EXPIRE_TIME = 30 * UnitUtils.DAY;

    public ThriftSessionDb() {
        super(TABLE_NAME);
    }

    public ThriftSession get(String sessionId) {
        try {
            return get(sessionId, ThriftSession.class);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    public void put(String sessionId, ThriftSession session) {
        try {
            super.set(sessionId, session);
            new Thread() {
                @Override
                public void run() {
                    try {
                        List<String> list = getAll();
                        for (String s:list) {
                            ThriftSession session = JsonUtils.fromJson(s, ThriftSession.class);
                            if (System.currentTimeMillis() - session.getActivateTime() > EXPIRE_TIME) {
                                remove(session.getSessionId());
                            }
                        }
                    } catch (SQLiteException e) {
                        throw new RuntimeException(e);
                    }
                };
            }.start();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
}
