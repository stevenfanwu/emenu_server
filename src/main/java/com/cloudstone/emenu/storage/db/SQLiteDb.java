/**
 * @(#)BaseDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.storage.BaseStorage;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

/**
 * @author xuhongfeng
 *
 */
//TODO the jni problem
public abstract class SQLiteDb extends BaseStorage {
    private static final Logger LOG = LoggerFactory.getLogger(SQLiteDb.class);
    
    private static final String SQL_CREATE = "CREATE TABLE IF NOT EXISTS %s (%s)";
            
    private static File DB_FILE;
    
    public static enum DataType {
        NULL("NULL"), INTEGER("INTEGER"), REAL("REAL"), TEXT("TEXT"), BLOB("BLOB");
        
        private final String str;
        private DataType(String str) {
            this.str = str;
        }
        
        @Override
        public String toString() {
            return str;
        }
    }
    
    /* ---------- protected ----------*/
    protected void init() {
        DB_FILE = new File(System.getProperty(Const.PARAM_DB_FILE));
        if (!DB_FILE.exists()) {
            try {
                DB_FILE.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            onCheckCreateTable();
        } catch (Throwable e) {
            LOG.error("", e);
            throw new RuntimeException(e);
        }
    }
    
    protected SQLiteConnection open() throws SQLiteException {
        if (DB_FILE == null) {
            init();
        }
        SQLiteConnection conn = new SQLiteConnection(DB_FILE);
        conn.open();
        return conn;
    }
    
    protected void checkCreateTable(String tableName, String columnDef) throws SQLiteException {
        String sql = String.format(SQL_CREATE, tableName, columnDef);
        LOG.info("create table sql: " + sql);
        executeSQL(sql, StatementBinder.NULL);
    }
    
    
    protected void executeSQL(String sql, StatementBinder binder) throws SQLiteException {
        SQLiteConnection conn = open();
        SQLiteStatement stmt = conn.prepare(sql);
        //TODO
        LOG.info("stmt = " + stmt);
        try {
            binder.onBind(stmt);
            stmt.stepThrough();
        } finally {
            stmt.dispose();
            conn.dispose();
        }
    }
    
    protected <T> T queryOne(String sql, StatementBinder binder, RowMapper<T> rowMapper) throws SQLiteException {
        SQLiteConnection conn = open();
        SQLiteStatement stmt = conn.prepare(sql);
        try {
            binder.onBind(stmt);
            if (stmt.step()) {
                return rowMapper.map(stmt);
            } else{
                return null;
            }
        } finally {
            stmt.dispose();
            conn.dispose();
        }
    }
    
    protected <T> List<T> query(String sql, StatementBinder binder, RowMapper<T> rowMapper) throws SQLiteException {
        SQLiteConnection conn = open();
        SQLiteStatement stmt = conn.prepare(sql);
        List<T> list = new ArrayList<T>();
        try {
            binder.onBind(stmt);
            while (stmt.step()) {
                list.add(rowMapper.map(stmt));
            }
        } finally {
            stmt.dispose();
            conn.dispose();
        }
        return list;
    }
    
    /* ---------- abstract ----------*/
    protected abstract void onCheckCreateTable() throws SQLiteException;
}
