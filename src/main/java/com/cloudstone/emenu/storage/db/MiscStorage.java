/**
 * @(#)MiscStorage.java, Aug 17, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;

/**
 * @author xuhongfeng
 */
@Repository
public class MiscStorage extends JsonDb {
    private static final Logger LOG = LoggerFactory.getLogger(MiscStorage.class);

    private static final String TABLE_NAME = "misc";

    private static final String KEY_CUSTOMER_NUMBER = "tcn";

    public MiscStorage() {
        super(TABLE_NAME);
    }

    public void setCustomerNumber(EmenuContext context, int tableId, int num) {
        String key = KEY_CUSTOMER_NUMBER + "-" + tableId;
        set(context, key, num);
        LOG.info("set " + key + " = " + num);
    }

    public int getCustomerNumber(EmenuContext context, int tableId) {
        String key = KEY_CUSTOMER_NUMBER + "-" + tableId;
        Integer num = get(context, key, Integer.class);
        LOG.info("get" + key + " = " + num);
        return num == null ? 0 : num;
    }
}
