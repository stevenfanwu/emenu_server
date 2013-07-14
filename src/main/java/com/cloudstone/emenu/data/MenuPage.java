/**
 * @(#)MenuPage.java, Jul 14, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class MenuPage extends BaseData {
    private long id;
    private long chapterId;
    private int dishCount;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getChapterId() {
        return chapterId;
    }
    public void setChapterId(long chapterId) {
        this.chapterId = chapterId;
    }
    public int getDishCount() {
        return dishCount;
    }
    public void setDishCount(int dishCount) {
        this.dishCount = dishCount;
    }
}