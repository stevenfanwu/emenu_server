package com.cloudstone.emenu.storage.db.util;

/**
 * @author xuhongfeng
 */
public class InsertSqlBuilder extends SQLBuilder {

    public InsertSqlBuilder(String tableName, int columnCount) {
        this(tableName, columnCount, false);
    }

    public InsertSqlBuilder(String tableName, int columnCount, boolean replace) {
        super();
        if (replace) {
            append("REPLACE ");
        } else {
            append("INSERT ");
        }
        append("INTO " + tableName + " VALUES");
        append(" (");
        for (int i = 0; i < columnCount; i++) {
            if (i != 0) {
                append(", ");
            }
            append("?");
        }
        append(")");
    }

}