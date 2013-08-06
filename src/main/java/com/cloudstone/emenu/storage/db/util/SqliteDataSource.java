/**
 * @(#)SqliteDataSource.java, Aug 2, 2013. 
 * 
 */

package com.cloudstone.emenu.storage.db.util;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.storage.db.SQLiteDb;

/**
 * @author xuhongfeng
 */
@Component
public class SqliteDataSource {
    private static final Logger LOG = LoggerFactory.getLogger(SqliteDataSource.class);

    private File dbFile;

    public File getDbFile() {
        if (dbFile == null) {
            String dbPath = System.getProperty(Const.PARAM_DB_FILE);
            dbFile = new File(dbPath);
        }
        if (!dbFile.exists()) {
            synchronized (SqliteDataSource.class) {
                if (!dbFile.exists()) {
                    try {
                        dbFile.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return dbFile;
    }

    public void setDbFile(File dbFile) {
        this.dbFile = dbFile;
    }

    public DbTransaction openTrans() {
        return new DbTransaction(getDbFile());
    }

    public SQLiteConnection open(SQLiteDb db, DbTransaction trans) throws SQLiteException {
        db.init();
        SQLiteConnection conn;
        if (trans != null && trans.getIsTrans())
            conn = trans.getTransConn(getDbFile());
        else
            conn = new SQLiteConnection(getDbFile());
        conn.open();
        return conn;
    }

}
