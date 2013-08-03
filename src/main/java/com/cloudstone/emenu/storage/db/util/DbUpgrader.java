/**
 * @(#)DbUpgrader.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

import java.io.File;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.constant.ServerConfig;
import com.cloudstone.emenu.data.User;
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
    
    public void checkUpgrade() {
        if (upgrading) {
            return;
        }
        upgrading = true;
        new Thread() {
            @Override
            public void run() {
                try {
                    startUpgrade();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    upgrading = false;
                }
            };
        }.start();
    }
    
    private void startUpgrade() throws Exception {
        File dbDir = new File(System.getProperty(Const.PARAM_CLOUDSTONE_DATA_DIR));
        File dbFile = new File(System.getProperty(Const.PARAM_DB_FILE));
        File tmpFile = new File(dbDir, "tmp.db");
        
        try {
            FileUtils.copyFile(dbFile, tmpFile);
            dataSource.setDbFile(tmpFile);
            int oldVersion = configLogic.getDbVersion();
            int newVersion = ServerConfig.DB_VERSION;
            for (int i=oldVersion+1; i<=newVersion; i++) {
                onUpgrade(i-1, i);
                configLogic.setDbVersion(i);
                FileUtils.copyFile(tmpFile, dbFile);
            }
        } finally {
            dataSource.setDbFile(dbFile);
            FileUtils.deleteQuietly(tmpFile);
        }
    }
    
    private void onUpgrade(int oldVersion, int newVersion) throws Exception {
        if (oldVersion==1 && newVersion==2) {
            List<User> users = userDb.getAll();
            for (User user:users) {
                LOG.info("user = " + user.getName());
                synchronized (this) {
                    Thread.sleep(5000L);
                }
            }
        }
    }
}
