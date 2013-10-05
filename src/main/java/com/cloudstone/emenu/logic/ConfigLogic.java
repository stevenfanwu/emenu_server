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
import com.cloudstone.emenu.data.Licence;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.ConfigDb;
import com.cloudstone.emenu.util.LicenceHelper;

/**
 * @author xuhongfeng
 *
 */
@Component
public class ConfigLogic extends BaseLogic {
    @Autowired
    private ConfigDb configDb;
    @Autowired
    private LicenceHelper licenceHelper;
    
    public int getPadNumber() {
        Licence licence = licenceHelper.getLicence();
        return licence==null ? 0 : licence.getPadCount();
    }
    
    public int getDbVersion(EmenuContext context) {
        Integer v = configDb.get(context, JsonKeyConst.DB_VERSION, Integer.class);
        if (v == null) {
            return ServerConfig.DB_VERSION;
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
