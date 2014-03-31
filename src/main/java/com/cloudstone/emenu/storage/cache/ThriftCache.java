/**
 * @(#)ThriftCache.java, Aug 4, 2013. 
 *
 */
package com.cloudstone.emenu.storage.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Pad;
import com.cloudstone.emenu.logic.DeviceLogic;

/**
 * @author xuhongfeng
 */
@Repository
public class ThriftCache extends BaseCache {
    @Autowired
    private DeviceLogic deviceLogic;

    private final Set<String> imeiSet = new HashSet<String>();

    public boolean isValidImei(EmenuContext context, String imei) {
        if (imeiSet.isEmpty()) {
            List<Pad> pads = deviceLogic.listAllPad(context);
            for (Pad p : pads) {
                imeiSet.add(p.getImei());
            }
        }
        return imeiSet.contains(imei);
    }

    public void onPadChanged() {
        imeiSet.clear();
    }
}
