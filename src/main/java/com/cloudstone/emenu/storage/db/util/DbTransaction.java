
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

    public DbTransaction(SQLiteConnection conn) {
        this.conn = conn;
    }

    public SQLiteConnection getTransConn() {
        if (!isBegin)
            throw new ServerError("trans is not begin");
        return conn;
    }

    public void begin() {
        if (this.conn.isOpen()) {
            throw new ServerError("conn is already open");
        }
        if (isBegin) {
            throw new ServerError("trans is already begin");
        }
        try {
            this.conn.open();
            conn.exec("BEGIN");
            isBegin = true;
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    public void commit() {
        if (!this.conn.isOpen()) {
            throw new ServerError("conn is not open");
        }
        if (!isBegin) {
            throw new ServerError("trans is not begin");
        }
        try {
            conn.exec("COMMIT");
            isBegin = false;
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    public boolean isBegin() {
        return isBegin;
    }
}
