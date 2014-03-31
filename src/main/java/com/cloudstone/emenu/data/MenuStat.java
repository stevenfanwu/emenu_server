/**
 * @(#)MenuStat.java, Aug 6, 2013. 
 *
 */

package com.cloudstone.emenu.data;

/**
 * @author carelife
 */
public class MenuStat extends BaseStat {
    private String chapterName;

    private double count;

    public MenuStat() {
        super();
    }

    public void setCount(double count) {
        this.count = count;
    }

    public double getCount() {
        return count;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

}
