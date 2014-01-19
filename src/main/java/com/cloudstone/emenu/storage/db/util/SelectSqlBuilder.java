/**
 * @(#)ColumnDefBuilder.java, 2013-6-23. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

/**
 * @author xuhongfeng
 *
 */
public class SelectSqlBuilder extends SQLBuilder {

    public SelectSqlBuilder(String tableName) {
        super();
        append("SELECT * from " + tableName);
    }
    
    public SelectSqlBuilder appendOrderBy(Object col, boolean desc) {
        append(" ORDER BY ");
        append(col);
        append(desc ? " DESC" : " ASC");
        return this;
    }
    
    @Override
    public SelectSqlBuilder appendWhere(Object whereColumn) {
        super.appendWhere(whereColumn);
        return this;
    }

    public SelectSqlBuilder append(Object s) {
        super.append(s);
        return this;
    }
}