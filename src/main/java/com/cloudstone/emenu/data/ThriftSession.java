/**
 * @(#)ThriftSession.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class ThriftSession {
    private String sessionId;
    private User user;
    private long activateTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(long activateTime) {
        this.activateTime = activateTime;
    }
}
