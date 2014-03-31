/**
 * @(#)PollingManager.java, Aug 28, 2013. 
 *
 */
package com.cloudstone.emenu.data.misc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author xuhongfeng
 */
@Service
public class PollingManager {
    private Map<Integer, PollingMessage> map =
            new HashMap<Integer, PollingMessage>();

    public synchronized void putMessage(PollingMessage msg) {
        map.put(msg.type, msg);
    }

    public synchronized void putMessage(int type) {
        putMessage(type, null);
    }

    public synchronized void putMessage(int type, Object data) {
        putMessage(new PollingMessage(type, data));
    }

    public synchronized PollingMessage query() {
        Iterator<PollingMessage> it = map.values().iterator();
        PollingMessage msg = PollingMessage.NULL;
        if (it.hasNext()) {
            msg = it.next();
            it.remove();
        }
        return msg;
    }

    public static class PollingMessage {
        public static final int TYPE_NEW_ORDER = 1;
        public static final int TYPE_OCCUPY_TABLE = 2;
        public static final int TYPE_CLEAR_TABLE = 3;

        public final int type;
        public final Object data;

        public PollingMessage(int type, Object data) {
            super();
            this.type = type;
            this.data = data;
        }

        public static final PollingMessage NULL =
                new PollingMessage(0, null);
    }

}
