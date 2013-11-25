/**
 * @(#)CountSqlBuilder.java, Jul 20, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

/**
 * @author xuhongfeng
 *
 */
public class CountSqlBuilder extends SQLBuilder {

    public CountSqlBuilder(String tableName) {
        super();
        append("SELECT COUNT(*) FROM " + tableName + " "); 
    }
}
