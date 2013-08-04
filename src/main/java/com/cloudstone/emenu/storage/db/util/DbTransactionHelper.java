package com.cloudstone.emenu.storage.db.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;

public class DbTransactionHelper {
    private static final Logger LOG = LoggerFactory.getLogger(DbTransactionHelper.class);

    private static DbTransactionHelper dbTransHelper;
    
    public static DbTransactionHelper getInstance() {
        if(dbTransHelper == null)
            dbTransHelper = new DbTransactionHelper();
        return dbTransHelper;
    }
    
    private SQLiteConnection open() throws SQLiteException {
        SQLiteConnection conn = new SQLiteConnection();
        conn.open();
        return conn;
    }
    
    public void beginTransaction() throws SQLiteException {
        SQLiteConnection conn = open();
        conn.exec("BEGIN");
    }

    public void endTransaction() throws SQLiteException {
        SQLiteConnection conn = open();
        conn.exec("COMMIT");
    }
    
}
