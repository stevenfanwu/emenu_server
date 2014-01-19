/**
 * @(#)DbUpgrader.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.almworks.sqlite4java.SQLiteConnection;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.constant.ServerConfig;
import com.cloudstone.emenu.logic.ConfigLogic;
import com.cloudstone.emenu.logic.MenuLogic;
import com.cloudstone.emenu.storage.db.GenStatDb;
import com.cloudstone.emenu.storage.db.IDishStatDb;
import com.cloudstone.emenu.storage.db.IGenStatDb;
import com.cloudstone.emenu.storage.db.IMenuStatDb;
import com.cloudstone.emenu.storage.db.IUserDb;

/**
 * @author carelife
 *
 */
@Component
public class DbUpgrader {
    private static final Logger LOG = LoggerFactory.getLogger(DbUpgrader.class);
    private static ThreadLocal<SimpleDateFormat> FORMAT = new ThreadLocal<SimpleDateFormat>() {
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        };
    };
    
    private static volatile boolean upgrading = false;
    
    @Autowired
    private ConfigLogic configLogic;
    @Autowired
    private MenuLogic menuLogic;
    @Autowired
    private SqliteDataSource dataSource;
    
    @Autowired
    private IUserDb userDb;
    @Autowired
    private IDishStatDb dishStatDb;
    @Autowired
    private IMenuStatDb menuStatDb;
    @Autowired
    private IGenStatDb genStatDb;
    
    public void checkUpgrade(final EmenuContext context) {
        if (upgrading) {
            return;
        }
        upgrading = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    startUpgrade(context);
                } catch (Exception e) {
                    LOG.error("", e);
                    throw new RuntimeException(e);
                } finally {
                    upgrading = false;
                }
            };
        }.start();
    }
    
    private void startUpgrade(EmenuContext context) throws Exception {
        File dbDir = new File(System.getProperty(Const.PARAM_CLOUDSTONE_DATA_DIR));
        File bakDir = new File(dbDir, "backup");
        if (!bakDir.exists()) {
            bakDir.mkdirs();
        }
        File dbFile = new File(System.getProperty(Const.PARAM_DB_FILE));
        File tmpFile = new File(dbDir, "tmp.db");
        File bakFile = new File(bakDir, FORMAT.get().format(new Date()) + ".db");
        
        try {
            FileUtils.copyFile(dbFile, bakFile);
            FileUtils.copyFile(dbFile, tmpFile);
            dataSource.setDbFile(tmpFile);
            int oldVersion = configLogic.getDbVersion(context);
            int newVersion = ServerConfig.DB_VERSION;
            for (int i=oldVersion+1; i<=newVersion; i++) {
                onUpgrade(context, i-1, i);
                configLogic.setDbVersion(context, i);
                FileUtils.copyFile(tmpFile, dbFile);
            }
        } finally {
            dataSource.setDbFile(dbFile);
            FileUtils.deleteQuietly(tmpFile);
        }
    }
    
    private void onUpgrade(EmenuContext context, int oldVersion, int newVersion) throws Exception {
        LOG.info(String.format("UPGRADE %d to %d", oldVersion, newVersion));
        if (oldVersion==1 && newVersion==2) {
            int[] chapterIds = menuLogic.getChapterIds(context);
            SQLiteConnection conn = dataSource.open();
            conn.exec("ALTER TABLE printTemplate ADD COLUMN chapterIds TEXT DEFAULT ''");
            conn.exec(String.format("UPDATE printTemplate set chapterIds='%s'", SqlUtils.idsToStr(chapterIds)));
            conn.dispose();
        } else if (oldVersion==2 && newVersion==3) {
            SQLiteConnection conn = dataSource.open();
            conn.exec("ALTER TABLE chapter ADD COLUMN ordinal INTEGER DEFAULT 0");
            conn.exec("DROP TABLE dishstat");
            conn.exec("DROP TABLE menustat");
            conn.dispose();
            
            /**
             * re-create tables
             */
            dishStatDb.init();
            menuStatDb.init();
            
            menuLogic.fixChapterOrdinal(context);
        } else if (oldVersion==3 && newVersion==4) {
        	SQLiteConnection conn = dataSource.open();
            conn.exec("ALTER TABLE bill ADD COLUMN coupons REAL DEFAULT 0");
            conn.exec("ALTER TABLE bill ADD COLUMN vipId INTEGER DEFAULT 0");
            conn.exec("ALTER TABLE bill ADD COLUMN vipCost REAL DEFAULT 0");
            conn.exec("DROP TABLE genstat");
            conn.dispose();
            
            /**
             * re-create tables
             */
            genStatDb.init();
        }
    }
}
