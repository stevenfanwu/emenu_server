/**
 * @(#)JsonDb.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class JsonDb extends SQLiteDb {
    private static final Logger LOG = LoggerFactory.getLogger(JsonDb.class);
    
    private static final String TABLE_NAME = "json";
    private static final Map<String, String> cache = new ConcurrentHashMap<String, String>();

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }
    
    public <T> T get(String key, Class<T> clazz) throws SQLiteException {
        String json = innerGet(key);
        return json == null ? null : JsonUtils.fromJson(json, clazz);
    }
    
    private String innerGet(String key) throws SQLiteException {
        String value = cache.get(key);
        if (value == null) {
            value = queryString(SQL_SELECT, new KeyBinder(key));
            if (value != null) {
                cache.put(key, value);
            }
        }
        return value;
    }
    
    public <T> void set(String key, T value) throws SQLiteException {
        innerSet(key, JsonUtils.toJson(value));
    }
    
    private void innerSet(String key, String value) throws SQLiteException {
        executeSQL(SQL_REPLACE, new ReplaceBinder(key, value));
        cache.put(key, value);
    }
    
    private static final String SQL_REPLACE = "REPLACE INTO " + TABLE_NAME + " VALUES(?, ?)";
    private static final String SQL_SELECT = "SELECT value FROM " + TABLE_NAME + " WHERE key=?";
    
    private class ReplaceBinder implements StatementBinder {
        private final String key;
        private final String value;
        
        public ReplaceBinder(String key, String value) {
            super();
            this.key = key;
            this.value = value;
        }
        
        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, key);
            stmt.bind(2, value);
        }
    }
    private class KeyBinder implements StatementBinder {
        private final String key;

        public KeyBinder(String key) {
            super();
            this.key = key;
        }
        
        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, key);
        }
    }
    
    private static final String COL_DEF = new ColumnDefBuilder()
        .append(Column.KEY, DataType.TEXT, "NOT NULL PRIMARY KEY")
        .append(Column.VALUE, DataType.TEXT, "NOT NULL")
        .build();

    private static enum Column {
        KEY("key"), VALUE("value");
        
        private final String str;
        private Column(String str) {
            this.str = str;
        }
        
        @Override
        public String toString() {
            return str;
        }
    }
}
