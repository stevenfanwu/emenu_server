/**
 * @(#)JsonDb.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import org.springframework.stereotype.Repository;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class ConfigDb extends JsonDb {
    private static final String TABLE_NAME = "config";

    public ConfigDb() {
        super(TABLE_NAME);
    }
    
}
