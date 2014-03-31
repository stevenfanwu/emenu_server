/**
 * @(#)FileStorage.java, Jul 27, 2013. 
 *
 */
package com.cloudstone.emenu.storage.file;

import java.io.File;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.storage.BaseStorage;

/**
 * @author xuhongfeng
 */
public class FileStorage extends BaseStorage {
    public File getCloudstoneDataDir() {
        String path = System.getProperty(Const.PARAM_CLOUDSTONE_DATA_DIR);
        return new File(path);
    }

}
