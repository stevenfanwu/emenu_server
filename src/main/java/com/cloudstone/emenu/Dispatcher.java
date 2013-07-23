/**
 * @(#)Dispatcher.java, Jun 1, 2013. 
 * 
 */
package com.cloudstone.emenu;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.servlet.DispatcherServlet;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.storage.db.DishTagDb;


/**
 * @author xuhongfeng
 *
 */
public class Dispatcher extends DispatcherServlet {
    //Global TODO
    //TODO database upgrade
    //TODO image
    
    private static final long serialVersionUID = 1245712434233720170L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        File homeDir = new File(config.getServletContext().getRealPath("/"));
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
        if (!dbFile.exists()) {
            try {
                dbFile.createNewFile();
                DishTagDb.initData();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        super.init(config);
    }
}
