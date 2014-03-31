/**
 * @(#)PrintTemplate.java, Aug 14, 2013. 
 *
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 */
public class PrintTemplate extends IdName {
    private int headerId;
    private int footerId;
    private int cutType;
    private int fontSize = 1;
    private int[] chapterIds;

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getHeaderId() {
        return headerId;
    }

    public void setHeaderId(int headerId) {
        this.headerId = headerId;
    }

    public int getFooterId() {
        return footerId;
    }

    public void setFooterId(int footerId) {
        this.footerId = footerId;
    }

    public int getCutType() {
        return cutType;
    }

    public void setCutType(int cutType) {
        this.cutType = cutType;
    }

    public int[] getChapterIds() {
        return chapterIds;
    }

    public void setChapterIds(int[] chapterIds) {
        this.chapterIds = chapterIds;
    }
}
