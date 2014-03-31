/**
 * @(#)BaseData.java, 2013-7-6. 
 *
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 */
public class BaseData {
    private long createdTime;
    private long updateTime;
    private boolean deleted;

    // Id of the restaurant the user belongs to
    private int restaurantId;

    public BaseData() {
        super();
    }

    public BaseData(BaseData other) {
        super();
        createdTime = other.getCreatedTime();
        updateTime = other.updateTime;
        deleted = other.deleted;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
}
