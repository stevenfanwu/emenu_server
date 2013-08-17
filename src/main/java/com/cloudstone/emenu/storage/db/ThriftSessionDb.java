/**
 * @(#)ThriftSessionDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.ThriftSession;
import com.cloudstone.emenu.util.JsonUtils;
import com.cloudstone.emenu.util.UnitUtils;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class ThriftSessionDb extends JsonDb {
    private static final Logger LOG = LoggerFactory.getLogger(ThriftSessionDb.class);
    
    private static final String TABLE_NAME = "thriftSession";
    
    public static final long EXPIRE_TIME = 30 * UnitUtils.DAY;

    public ThriftSessionDb() {
        super(TABLE_NAME);
    }

    public ThriftSession get(EmenuContext context, String sessionId) {
        return get(context, sessionId, ThriftSession.class);
    }
    
    public void put(EmenuContext context, String sessionId, ThriftSession session) {
        super.set(context, sessionId, session);
    }
    
    public List<ThriftSession> getAllSession(EmenuContext context) {
        List<ThriftSession> ret = new LinkedList<ThriftSession>();
        List<String> list = super.getAll(context);
        for (String s:list) {
            ThriftSession session = JsonUtils.fromJson(s, ThriftSession.class);
            ret.add(session);
        }
        return ret;
    }
    
    public ThriftSession getLatest(EmenuContext context, String imei) {
        List<ThriftSession> list = getAllSession(context);
        for (ThriftSession s:list) {
            if (System.currentTimeMillis() - s.getActivateTime() > EXPIRE_TIME) {
                remove(context, s.getSessionId());
            }
        }
        Collections.sort(list, CMP);
        for (ThriftSession s:list) {
            if (s.getImei().equals(imei)) {
                return s;
            }
        }
        return null;
    }
    
    private static final Comparator<ThriftSession> CMP = new Comparator<ThriftSession>() {
        
        @Override
        public int compare(ThriftSession o1, ThriftSession o2) {
            if (o1.getActivateTime() > o2.getActivateTime()) {
                return -1;
            }
            return 1;
        }
    };
}