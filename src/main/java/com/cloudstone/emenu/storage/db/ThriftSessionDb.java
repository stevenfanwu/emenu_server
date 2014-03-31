/**
 * @(#)ThriftSessionDb.java, Aug 4, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.ThriftSession;
import com.cloudstone.emenu.util.JsonUtils;
import com.cloudstone.emenu.util.UnitUtils;

/**
 * @author xuhongfeng
 */
@Repository
public class ThriftSessionDb extends JsonDb {
    private static final Logger LOG = LoggerFactory.getLogger(ThriftSessionDb.class);

    private static final String TABLE_NAME = "thriftSession";

    public static final long EXPIRE_TIME = 30 * UnitUtils.DAY;

    public ThriftSessionDb() {
        super(TABLE_NAME);
    }

    @Override
    protected void init(EmenuContext context) {
        super.init(context);
        clearExpired(context);
    }

    public Map<String, ThriftSession> dumpSessions(EmenuContext context) {
        Map<String, String> data = super.dump(context);
        Map<String, ThriftSession> map = new HashMap<String, ThriftSession>();

        for (java.util.Map.Entry<String, String> e : data.entrySet()) {
            String sessionId = e.getKey();
            String json = e.getValue();
            ThriftSession session = JsonUtils.fromJson(json, ThriftSession.class);
            map.put(sessionId, session);
        }

        return map;
    }

    public void clearExpired(EmenuContext context) {
        Map<String, ThriftSession> map = dumpSessions(context);
        long now = System.currentTimeMillis();
        for (java.util.Map.Entry<String, ThriftSession> e : map.entrySet()) {
            String sessionId = e.getKey();
            ThriftSession session = e.getValue();
            if (now - session.getActivateTime() > EXPIRE_TIME || !sessionId.equals(session.getSessionId())) {
                remove(context, sessionId);
            }
        }
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
        for (String s : list) {
            ThriftSession session = JsonUtils.fromJson(s, ThriftSession.class);
            ret.add(session);
        }
        return ret;
    }

    public ThriftSession getLatest(EmenuContext context, String imei) {

        List<ThriftSession> list = getAllSession(context);
        for (ThriftSession s : list) {
            if (System.currentTimeMillis() - s.getActivateTime() > EXPIRE_TIME) {
                remove(context, s.getSessionId());
            }
        }
        Collections.sort(list, CMP);
        for (ThriftSession s : list) {
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