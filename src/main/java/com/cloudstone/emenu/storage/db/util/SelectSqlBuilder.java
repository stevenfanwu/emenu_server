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
        append("SELECT * from " + tableName + " where ");
    }
    
    private boolean firstWhere = true;
    public SelectSqlBuilder appendWhere(String whereColumn) {
        if (!firstWhere) {
            append("AND ");
        }
        firstWhere = false;
        append(whereColumn + "=? ");
        return this;
    }
}