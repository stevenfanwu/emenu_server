/**
 * @(#)ThriftCache.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.cache;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.Pad;
import com.cloudstone.emenu.logic.DeviceLogic;
import com.cloudstone.emenu.logic.MenuLogic;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class ThriftCache extends BaseCache {
    //TODO clear cache when current menu changed
    
    private final Map<Integer, String> categoryMap = new ConcurrentHashMap<Integer, String>();
    
    private final Set<String> imeiSet = new HashSet<String>();
    
    @Autowired
    private MenuLogic menuLogic;
    @Autowired
    private DeviceLogic deviceLogic;
    
    private int currentMenuId = -1;
    
    public String getCategory(EmenuContext context, int goodId) {
        String category = categoryMap.get(goodId);
        if (category == null) {
            int menuId = getCurrentMenuId(context);
            List<Chapter> chapters = menuLogic.listChapters(context, menuId, goodId);
            if (chapters.size() > 0) {
                category = chapters.get(0).getName();
                categoryMap.put(goodId, category);
            }
        }
        return category;
    }
    
    public void onCurrentMenuChanged() {
        currentMenuId = -1;
        categoryMap.clear();
    }
    
    public int getCurrentMenuId(EmenuContext context) {
        if (currentMenuId == -1) {
            Menu menu = menuLogic.getCurrentMenu(context);
            if (menu != null) {
                currentMenuId = menu.getId();
            }
        }
        return currentMenuId;
    }
    
    public boolean isValidImei(EmenuContext context, String imei) {
        if (imeiSet.isEmpty()) {
            List<Pad> pads = deviceLogic.listAllPad(context);
            for (Pad p:pads) {
                imeiSet.add(p.getImei());
            }
        }
        return imeiSet.contains(imei);
    }
    
    public void onPadChanged() {
        imeiSet.clear();
    }
}
