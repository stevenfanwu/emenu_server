package com.cloudstone.emenu.storage.db.util;

/**
 * 
 * @author xuhongfeng
 *
 */
public class InsertSqlBuilder extends SQLBuilder {

    public InsertSqlBuilder(String tableName, int columnCount) {
        super();
        append("INSERT INTO " + tableName + " VALUES");
        append(" (");
        for (int i=0; i<columnCount; i++) {
            if (i != 0) {
                append(", ");
            }
            append("?");
        }
        append(")");
    }
    
}