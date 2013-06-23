package com.cloudstone.emenu.storage.db.util;

import com.cloudstone.emenu.storage.db.SQLiteDb.DataType;

/**
 * 
 * @author xuhongfeng
 *
 */
public class ColumnDefBuilder extends SQLBuilder {
    public ColumnDefBuilder append(String name, DataType type, String constraint) {
        if (size() > 0) {
            append(", ");
        }
        append(String.format("%s %s %s", name, type, constraint));
        return this;
    }
}