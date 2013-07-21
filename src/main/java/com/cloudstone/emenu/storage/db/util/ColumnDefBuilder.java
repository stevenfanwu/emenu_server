package com.cloudstone.emenu.storage.db.util;

import com.cloudstone.emenu.storage.db.SQLiteDb.DataType;

/**
 * 
 * @author xuhongfeng
 *
 */
public class ColumnDefBuilder extends SQLBuilder {
    public ColumnDefBuilder append(Object column, DataType type, String constraint) {
        if (size() > 0) {
            append(", ");
        }
        append(String.format("%s %s %s", column.toString(), type, constraint));
        return this;
    }
    
    public ColumnDefBuilder appendPrimaryKey(Object... keys) {
        append(", primary key (");
        boolean first = true;
        for (Object key:keys) {
            if (!first) {
                appendComma();
            }
            first = false;
            append(key);
        }
        append(")");
        return this;
    }
}