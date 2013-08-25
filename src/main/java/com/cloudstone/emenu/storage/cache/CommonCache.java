/**
 * @(#)CommonCache.java, Aug 25, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.logic.MenuLogic;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class CommonCache extends BaseCache {
    //TODO clear cache when current menu changed
    
    private final Map<Integer, String> categoryMap = new ConcurrentHashMap<Integer, String>();
    
    @Autowired
    private MenuLogic menuLogic;
    
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
        if (category == null) {
            category = "其它";
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

}
