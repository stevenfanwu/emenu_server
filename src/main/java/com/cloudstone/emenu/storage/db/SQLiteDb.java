/**
 * @(#)BaseDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
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
            
    //TODO where to store this file
    @Value("${db.file}")
    private String DB_FILE;
    
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
    @PostConstruct
    protected void init() {
        try {
            onCheckCreateTable();
        } catch (SQLiteException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected SQLiteConnection open() throws SQLiteException {
        File dbFile = new File(DB_FILE);
//        LOG.info("dbFile=" + dbFile.getAbsolutePath() + ", exists=" + dbFile.exists());
        SQLiteConnection conn = new SQLiteConnection(dbFile);
        conn.open();
        return conn;
    }
    
    protected void checkCreateTable(String tableName, String columnDef) throws SQLiteException {
        String sql = String.format(SQL_CREATE, tableName, columnDef);
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
