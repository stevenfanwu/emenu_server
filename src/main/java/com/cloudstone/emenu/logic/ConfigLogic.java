/**
 * @(#)ConfigLogic.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.almworks.sqlite4java.SQLiteException;
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
    
    public int getDbVersion() {
        try {
            Integer v = configDb.get(JsonKeyConst.DB_VERSION, Integer.class);
            if (v == null) {
                return 1;
            }
            return v;
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    public void setDbVersion(int version) throws SQLiteException {
        configDb.set(JsonKeyConst.DB_VERSION, version);
    }
    
    public boolean needUpgradeDb() {
        if (ServerConfig.DB_VERSION == getDbVersion()) {
            return false;
        }
        if (ServerConfig.DB_VERSION < getDbVersion()) {
            throw new ServerError("error db version");
        }
        return true;
    }
}
