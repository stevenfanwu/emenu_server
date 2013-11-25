/**
 * @(#)MyContextListener.java, Aug 17, 2013. 
 * 
 */
package com.cloudstone.emenu;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cloudstone.emenu.constant.Const;

/**
 * @author xuhongfeng
 *
 */
public class MyContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent evt) {
    }

    @Override
    public void contextInitialized(ServletContextEvent evt) {
        File homeDir = new File(evt.getServletContext().getRealPath("/"));
        System.setProperty(Const.PARAM_WEB_HOME_DIR, homeDir.getAbsolutePath());
        File tomcatHome = homeDir.getParentFile().getParentFile();
        System.setProperty(Const.PARAM_TOMCAT_HOME, tomcatHome.getAbsolutePath());
        File dataDir = new File(tomcatHome, "cloudstone-data");
        System.setProperty(Const.PARAM_CLOUDSTONE_DATA_DIR, dataDir.getAbsolutePath());
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }
        File dbFile = new File(dataDir, "cloudstone.db");
        System.setProperty(Const.PARAM_DB_FILE, dbFile.getAbsolutePath());
    }

}
