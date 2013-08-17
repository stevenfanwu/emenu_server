/**
 * @(#)DbUpgrader.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.constant.ServerConfig;
import com.cloudstone.emenu.logic.ConfigLogic;
import com.cloudstone.emenu.storage.db.IUserDb;

/**
 * @author xuhongfeng
 *
 */
@Component
public class DbUpgrader {
    private static final Logger LOG = LoggerFactory.getLogger(DbUpgrader.class);
    
    private static volatile boolean upgrading = false;
    
    @Autowired
    private ConfigLogic configLogic;
    @Autowired
    private SqliteDataSource dataSource;
    
    @Autowired
    private IUserDb userDb;
    
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
                    throw new RuntimeException(e);
                } finally {
                    upgrading = false;
                }
            };
        }.start();
    }
    
    private void startUpgrade(EmenuContext context) throws Exception {
        File dbDir = new File(System.getProperty(Const.PARAM_CLOUDSTONE_DATA_DIR));
        File dbFile = new File(System.getProperty(Const.PARAM_DB_FILE));
        File tmpFile = new File(dbDir, "tmp.db");
        
        try {
            FileUtils.copyFile(dbFile, tmpFile);
            dataSource.setDbFile(tmpFile);
            int oldVersion = configLogic.getDbVersion(context);
            int newVersion = ServerConfig.DB_VERSION;
            for (int i=oldVersion+1; i<=newVersion; i++) {
                onUpgrade(i-1, i);
                configLogic.setDbVersion(context, i);
                FileUtils.copyFile(tmpFile, dbFile);
            }
        } finally {
            dataSource.setDbFile(dbFile);
            FileUtils.deleteQuietly(tmpFile);
        }
    }
    
    private void onUpgrade(int oldVersion, int newVersion) throws Exception {
    }
}
