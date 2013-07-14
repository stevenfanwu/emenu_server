/**
 * @(#)MenuPageLogic.java, Jul 15, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.util.IdGenerator;

/**
 * @author xuhongfeng
 *
 */
@Component
public class MenuPageLogic extends BaseLogic {

    public MenuPage add(MenuPage page) {
        page.setId(IdGenerator.generateId());
        menuPageService.addMenuPage(page);
        return menuPageService.getMenuPage(page.getId());
    }
    
    public void deleteMenuPage(long id) {
        menuPageService.deleteMenuPage(id);
    }
    
    public List<MenuPage> listMenuPage(long chapterId) {
        return menuPageService.listByChapterId(chapterId);
    }
}
