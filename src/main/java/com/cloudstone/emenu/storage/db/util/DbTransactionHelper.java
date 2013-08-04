
package com.cloudstone.emenu.storage.db.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;

@Component
public class DbTransactionHelper {
    private static final Logger LOG = LoggerFactory.getLogger(DbTransactionHelper.class);

    private boolean isBegin;

    private SQLiteConnection conn;

    SQLiteConnection getTransConn(File dbFile) throws SQLiteException {
        if (conn == null)
            conn = new SQLiteConnection(dbFile);
        if (!conn.isOpen())
            conn.open();
        return conn;
    }

    public void beginTrans() {
        isBegin = true;
        try {
            conn.exec("BEGIN");
        } catch (SQLiteException e) {
            LOG.debug(e.getMessage());
        }
    }

    public void commitTrans() {
        isBegin = false;
        try {
            conn.exec("COMMIT");
        } catch (SQLiteException e) {
            LOG.debug(e.getMessage());
        }
    }

    public boolean getIsTrans() {
        return isBegin;
    }
}
