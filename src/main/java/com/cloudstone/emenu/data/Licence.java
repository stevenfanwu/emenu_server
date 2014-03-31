/**
 * @(#)Licence.java, Oct 5, 2013. 
 *
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 */
public class Licence {
    public static final int TYPE_FOR_EVER = 1;
    public static final int TYPE_TEST = 2;
    public static final int TYPE_LIMIT = 3;

    private String uuid;
    private int padCount;

    private int type;
    private boolean paid;
    private long licenceTime;

    private long createdTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getPadCount() {
        return padCount;
    }

    public void setPadCount(int padCount) {
        this.padCount = padCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public long getLicenceTime() {
        return licenceTime;
    }

    public void setLicenceTime(long licenceTime) {
        this.licenceTime = licenceTime;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }


}
