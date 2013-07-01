/**
 * @(#)UpdateSqlBuilder.java, 2013-7-2. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

/**
 * @author xuhongfeng
 *
 */
public class UpdateSqlBuilder extends SQLBuilder {
    private final String TABLE_NAME;

    public UpdateSqlBuilder(String tableName) {
        super();
        TABLE_NAME = tableName;
        append("UPDATE " + TABLE_NAME +" SET ");
    }

    private boolean firstValue = true;
    public UpdateSqlBuilder appendSetValue(String key) {
        if (!firstValue) {
            append(",");
        }
        firstValue = false;
        this.append(key + "=?");
        return this;
    }
}
