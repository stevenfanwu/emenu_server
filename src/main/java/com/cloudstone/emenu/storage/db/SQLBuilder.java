/**
 * @(#)SQLBuilder.java, 2013-6-22. 
 * 
 */
package com.cloudstone.emenu.storage.db;

/**
 * @author xuhongfeng
 *
 */
public class SQLBuilder {
    private final StringBuilder sb = new StringBuilder();

    public SQLBuilder() {
        super();
    }
    
    public SQLBuilder append(String s) {
        sb.append(s);
        return this;
    }
    
    public String build() {
        return sb.toString();
    }
    
    public int size() {
        return sb.length();
    }
}
