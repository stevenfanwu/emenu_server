/**
 * @(#)MenuLogic.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.util.IdGenerator;

/**
 * @author xuhongfeng
 *
 */
@Component
public class MenuLogic extends BaseLogic {
    private ChapterLogic chapterLogic;

    public Menu addMenu(Menu menu) {
        menu.setId(IdGenerator.generateId());
        menuService.addMenu(menu);
        return menuService.getMenu(menu.getId());
    }
    
    public Menu getMenu(long id) {
        return menuService.getMenu(id);
    }
    
    public List<Menu> getAllMenu() {
        return menuService.getAllMenu();
    }
    
    public Menu updateMenu(Menu menu) {
        menuService.updateMenu(menu);
        return menuService.getMenu(menu.getId());
    }
    
    public void deleteMenu(final long id) {
        menuService.deleteMenu(id);
        runTask(new Runnable() {
            @Override
            public void run() {
                chapterLogic.deleteChaptersByMenuId(id);
                //TODO handle dish
            }
        });
    }
}
