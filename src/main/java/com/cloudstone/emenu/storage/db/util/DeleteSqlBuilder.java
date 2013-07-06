/**
 * @(#)DeleteSqlBuilder.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

/**
 * @author xuhongfeng
 *
 */
public class DeleteSqlBuilder extends SQLBuilder {

    public DeleteSqlBuilder(String tableName) {
        super();
        append("DELETE FROM ");
        append(tableName);
    }
}
