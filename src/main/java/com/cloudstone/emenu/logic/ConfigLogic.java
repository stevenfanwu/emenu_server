/**
 * @(#)ConfigLogic.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.JsonKeyConst;
import com.cloudstone.emenu.constant.ServerConfig;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.ConfigDb;

/**
 * @author xuhongfeng
 *
 */
@Component
public class ConfigLogic extends BaseLogic {
    @Autowired
    private ConfigDb configDb;
    
    public int getPadNumber() {
        //TODO
        return 2;
    }
    
    public int getDbVersion(EmenuContext context) {
        Integer v = configDb.get(context, JsonKeyConst.DB_VERSION, Integer.class);
        if (v == null) {
            return 1;
        }
        return v;
    }
    
    public void setDbVersion(EmenuContext context, int version) {
        configDb.set(context, JsonKeyConst.DB_VERSION, version);
    }
    
    public boolean needUpgradeDb(EmenuContext context) {
        if (ServerConfig.DB_VERSION == getDbVersion(context)) {
            return false;
        }
        if (ServerConfig.DB_VERSION < getDbVersion(context)) {
            throw new ServerError("error db version");
        }
        return true;
    }
}
