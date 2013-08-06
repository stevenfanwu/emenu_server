
package com.cloudstone.emenu.storage.db.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.exception.ServerError;

public class DbTransaction {
    private static final Logger LOG = LoggerFactory.getLogger(DbTransaction.class);

    private boolean isBegin;

    private SQLiteConnection conn;

    public DbTransaction(File dbFile) {
        getTransConn(dbFile);
        if (!conn.isOpen()) {
            try {
                conn.open();
            } catch (SQLiteException e) {
                throw new ServerError(e);
            }
        }
    }

    SQLiteConnection getTransConn(File dbFile) {
        if (conn == null)
            conn = new SQLiteConnection(dbFile);
        return conn;
    }

    public void beginTrans() {
        if (conn == null)
            throw new NullPointerException();
        try {
            conn.exec("BEGIN");
            isBegin = true;
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    public void commitTrans() {
        if (conn == null)
            throw new NullPointerException();
        try {
            conn.exec("COMMIT");
            isBegin = false;
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    public boolean getIsTrans() {
        return isBegin;
    }
}
