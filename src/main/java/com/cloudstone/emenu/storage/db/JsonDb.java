/**
 * @(#)JsonDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
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
    
    public <T> T get(EmenuContext context, String key, Class<T> clazz) {
        String json = innerGet(context, key);
        return json == null ? null : JsonUtils.fromJson(json, clazz);
    }
    
    public Map<String, String> dump(EmenuContext context) {
        String sql = "SELECT key, value FROM " + TABLE_NAME;
        List<Entry> entries = query(context, sql, StatementBinder.NULL, EntryMapper);
        Map<String, String> map = new HashMap<String, String>();
        for (Entry entry:entries) {
            map.put(entry.key, entry.value);
        }
        return map;
    }
    
    public List<String> getAll(EmenuContext context) {
        return query(context, SQL_SELECT_ALL, StatementBinder.NULL, rowMapper);
    }
    
    public void remove(EmenuContext context, String key) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        executeSQL(context, SQL_DELETE, new KeyBinder(key));
        cache.remove(key);
    }
    
    private String innerGet(EmenuContext context, String key) {
        if (key == null) {
            return null;
        }
        String value = cache.get(key);
        if (value == null) {
            value = queryString(context, SQL_SELECT, new KeyBinder(key));
            if (value != null) {
                cache.put(key, value);
            }
        }
        return value;
    }
    
    public <T> void set(EmenuContext context, String key, T value) {
        try {
            innerSet(context, key, JsonUtils.toJson(value));
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    private void innerSet(EmenuContext context, String key, String value) throws SQLiteException {
        executeSQL(context, SQL_REPLACE, new ReplaceBinder(key, value));
        cache.put(key, value);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected void onCheckCreateTable(EmenuContext context) {
        checkCreateTable(context, TABLE_NAME, COL_DEF);
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
    
    private static final RowMapper<String> rowMapper = new RowMapper<String>() {
        
        @Override
        public String map(SQLiteStatement stmt) throws SQLiteException {
            return stmt.columnString(0);
        }
    };
    
    private static final RowMapper<Entry> EntryMapper = new RowMapper<Entry>() {

        @Override
        public Entry map(SQLiteStatement stmt) throws SQLiteException {
            Entry entry = new Entry();
            entry.setKey(stmt.columnString(0));
            entry.setValue(stmt.columnString(1));
            return entry;
        }
    };
    
    public static class Entry {
        private String key;
        private String value;
        
        public String getKey() {
            return key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
    }
}
