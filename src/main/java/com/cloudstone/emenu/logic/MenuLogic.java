/**
 * @(#)MenuLogic.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.util.IdGenerator;

/**
 * @author xuhongfeng
 *
 */
@Component
public class MenuLogic extends BaseLogic {

    /* ---------- menu ---------- */
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
    }
    
    /* ---------- dish ---------- */
    public Dish addDish(Dish dish) {
        dish.setId(IdGenerator.generateId());
        return menuService.addDish(dish);
    }
    
    public Dish updateDish(Dish dish) {
        return menuService.updateDish(dish);
    }
    
    public List<Dish> getAllDish() {
        return menuService.getAllDish();
    }
    
    public List<Dish> getDishByMenuPageId(long menuPageId) {
        return menuService.getDishByMenuPageId(menuPageId);
    }
    
    public void deleteDish(long id) {
        menuService.deleteDish(id);
    }

    public Dish getDish(long id) {
        return menuService.getDish(id);
    }
    
    /* ---------- chapter ---------- */
    public Chapter addChapter(Chapter chapter) {
        chapter.setId(IdGenerator.generateId());
        menuService.addChapter(chapter);
        return menuService.getChapter(chapter.getId());
    }
    
    public Chapter getChapter(long id) {
        return menuService.getChapter(id);
    }
    
    public List<Chapter> getAllChapter() {
        return menuService.getAllChapter();
    }
    
    public Chapter updateChapter(Chapter chapter) {
        menuService.updateChapter(chapter);
        return menuService.getChapter(chapter.getId());
    }
    
    public void deleteChapter(final long id) {
        menuService.deleteChapter(id);
    }
    
    public List<Chapter> listChapterByMenuId(long menuId) {
        return menuService.listChapterByMenuId(menuId);
    }
    
    /* ---------- MenuPage ---------- */
    public MenuPage addMenuPage(MenuPage page) {
        page.setId(IdGenerator.generateId());
        menuService.addMenuPage(page);
        return menuService.getMenuPage(page.getId());
    }
    
    public void deleteMenuPage(long id) {
        menuService.deleteMenuPage(id);
    }
    
    public List<MenuPage> listMenuPage(long chapterId) {
        return menuService.listMenuPageByChapterId(chapterId);
    }
}
