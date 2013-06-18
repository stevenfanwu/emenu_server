/**
 * @(#)UserSession.java, Jun 15, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

import com.cloudstone.emenu.util.NumberUtils;

/**
 * 
 * SessionCookie { userId, ip, createdTime}
 * 
 * @author xuhongfeng
 *
 */
public class UserSession {
    private long userId;
    
    private long createdTime;
    
    private int ip;
    
    //TODO timeSpan?
    public boolean checkSession(long userId, int ip, long timeSpan) {
        if (userId!=this.getUserId() || ip!=this.getIp()) {
            return false;
        }
        long now = System.currentTimeMillis();
        if (now - createdTime > timeSpan) {
            return false;
        }
        return true;
    }
    
    /* ---------- static ---------- */
    /**
     * return null if parse failed
     * 
     * @param sessionStr
     * @return
     */
    public static UserSession parseSession(String sessionStr) {
        String[] ss = sessionStr.split("::");
        if (ss.length != 3) {
            return null;
        }
        
        long userId = NumberUtils.toLong(ss[0], -1);
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
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
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
