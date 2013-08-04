/**
 * @(#)JsonDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
public class JsonDb extends SQLiteDb {
    private final String TABLE_NAME;
    private final String SQL_REPLACE;
    private final String SQL_SELECT;
    private final String SQL_DELETE;
    private final String SQL_SELECT_ALL;
    
    private final Map<String, String> cache = new ConcurrentHashMap<String, String>();

    public JsonDb(String tableName) {
        super();
        TABLE_NAME = tableName;
        SQL_REPLACE = "REPLACE INTO " + TABLE_NAME + " VALUES(?, ?)";
        SQL_SELECT = "SELECT value FROM " + TABLE_NAME + " WHERE key=?";
        SQL_DELETE = "DELETE FROM " + TABLE_NAME + " WHERE key=?";
        SQL_SELECT_ALL = "SELECT value FROM " + TABLE_NAME;
    }
    
    public <T> T get(String key, Class<T> clazz) throws SQLiteException {
        String json = innerGet(key);
        return json == null ? null : JsonUtils.fromJson(json, clazz);
    }
    
    public List<String> getAll() throws SQLiteException {
        return query(SQL_SELECT_ALL, StatementBinder.NULL, rowMapper);
    }
    
    public void remove(String key) {
        try {
            executeSQL(SQL_DELETE, new KeyBinder(key));
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
        cache.remove(key);
    }
    
    private String innerGet(String key) throws SQLiteException {
        if (key == null) {
            return null;
        }
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

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }

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
    
    private RowMapper<String> rowMapper = new RowMapper<String>() {
        
        @Override
        public String map(SQLiteStatement stmt) throws SQLiteException {
            return stmt.columnString(0);
        }
    };
}
