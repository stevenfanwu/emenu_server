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
    
    private static final String COMMA = ",";

    public SQLBuilder() {
        super();
    }
    
    protected SQLBuilder append(Object s) {
        sb.append(s);
        return this;
    }
    
    protected SQLBuilder appendComma() {
        return append(COMMA);
    }
    
    public String build() {
        return sb.toString();
    }
    
    public int size() {
        return sb.length();
    }
    
    private boolean firstWhere = true;
    
    public SQLBuilder appendNotDeleted() {
        if (firstWhere) {
            append(" where ");
        } else {
            append("AND ");
        }
        firstWhere = false;
        append("deleted=0");
        return this;
    }
    
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
    
    public SQLBuilder appendWhereName() {
        return this.appendWhere("name");
    }
    
    public SQLBuilder appendWhereId() {
        return this.appendWhere("id");
    }
    
    public SQLBuilder appendWhereIdIn(Object idName, int[] ids) {
        if (firstWhere) {
            append(" WHERE ");
        } else {
            append(" AND ");
        }
        firstWhere = false;
        append(idName);
        append(" IN (");
        append(SqlUtils.idsToStr(ids));
        append(")");
        return this;
    }
    
    public SQLBuilder appendWhereIdIn(int[] ids) {
        return appendWhereIdIn("id", ids);
    }
}
