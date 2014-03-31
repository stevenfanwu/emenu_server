/**
 * @(#)ThriftSession.java, Jul 26, 2013. 
 *
 */
package com.cloudstone.emenu.data;


/**
 * @author xuhongfeng
 */
public class ThriftSession {
    private String sessionId;
    //TODO save userId only
    private User user;
    private long activateTime;
    private String imei;

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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
