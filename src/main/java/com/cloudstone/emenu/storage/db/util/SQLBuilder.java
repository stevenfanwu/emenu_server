/**
 * @(#)SQLBuilder.java, 2013-6-22. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

/**
 * @author xuhongfeng
 *
 */
public class SQLBuilder {
    private final StringBuilder sb = new StringBuilder();

    public SQLBuilder() {
        super();
    }
    
    protected SQLBuilder append(String s) {
        sb.append(s);
        return this;
    }
    
    public String build() {
        return sb.toString();
    }
    
    public int size() {
        return sb.length();
    }
    
    private boolean firstWhere = true;
    public SQLBuilder appendWhere(Object whereColumn) {
        if (firstWhere) {
            append(" where ");
        } else {
            append("AND ");
        }
        firstWhere = false;
        append(whereColumn + "=? ");
        return this;
    }
    
    public SQLBuilder appendWhereId() {
        return this.appendWhere("id");
    }
}
