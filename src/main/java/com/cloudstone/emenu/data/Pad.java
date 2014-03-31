/**
 * @(#)Pad.java, Aug 4, 2013. 
 *
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 */
public class Pad extends IdName {
    private String desc;
    private String imei;
    private int batteryLevel;

    private ThriftSession session;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(int batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public ThriftSession getSession() {
        return session;
    }

    public void setSession(ThriftSession session) {
        this.session = session;
    }
}
