/**
 * @(#)Chapter.java, 2013-7-10. 
 *
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 */
public class Chapter extends IdName {
    private int menuId;
    private int ordinal;

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
}
