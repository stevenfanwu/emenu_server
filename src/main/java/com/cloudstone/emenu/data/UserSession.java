/**
 * @(#)UserSession.java, Jun 15, 2013. 
 *
 */
package com.cloudstone.emenu.data;

import com.cloudstone.emenu.util.NumberUtils;

/**
 * SessionCookie { userId, ip, createdTime}
 *
 * @author xuhongfeng
 */
public class UserSession {
    private static final String SPLIT = ",";
    private int userId;

    private long createdTime;

    private int ip;

    public boolean checkSession(int userId, int ip) {
        if (userId != this.getUserId() || ip != this.getIp()) {
            return false;
        }
        return true;
    }

    private static final String FORMAT = "%d" + SPLIT + "%d" + SPLIT + "%d";

    public String encode() {
        return String.format(FORMAT, userId, ip, createdTime);
    }
    
    /* ---------- static ---------- */

    /**
     * return null if decode failed
     *
     * @param sessionStr
     * @return
     */
    public static UserSession decode(String sessionStr) {
        String[] ss = sessionStr.split(SPLIT);
        if (ss.length != 3) {
            return null;
        }

        int userId = NumberUtils.toInt(ss[0], -1);
        if (userId == -1) {
            return null;
        }

        int ip = NumberUtils.toInt(ss[1], -1);
        if (ip == -1) {
            return null;
        }

        long createdTime = NumberUtils.toLong(ss[2], -1);
        if (createdTime == -1) {
            return null;
        }

        UserSession session = new UserSession();
        session.setUserId(userId);
        session.setIp(ip);
        session.setCreatedTime(createdTime);

        return session;
    }

    /* ---------- setter and getter ---------- */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public int getIp() {
        return ip;
    }

    public void setIp(int ip) {
        this.ip = ip;
    }
}
