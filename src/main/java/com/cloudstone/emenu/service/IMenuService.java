/**
 * @(#)IMenuService.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.MenuPage;

/**
 * @author xuhongfeng
 *
 */
public interface IMenuService {
    public void addMenu(Menu menu);
    public void updateMenu(Menu menu);
    public void deleteMenu(long id);
    public List<Menu> getAllMenu();
    public Menu getMenu(long id);
    
    public void addChapter(Chapter chapter);
    public void updateChapter(Chapter chapter);
    public void deleteChapter(long id);
    public List<Chapter> getAllChapter();
    public List<Chapter> listChapterByMenuId(long menuId);
    public Chapter getChapter(long id);
    
    public Dish addDish(Dish dish);
    public List<Dish> getAllDish();
    public Dish getDish(long id);
    public void deleteDish(long id);
    public Dish updateDish(Dish dish);
    public List<Dish> getDishByMenuPageId(long menuPageId);
    public List<IdName> getDishSuggestion();
    public void bindDish(long menuPageId, long dishId, int pos);
    public void unbindDish(long menuPageId, long dishId, int pos);
    
    public void addMenuPage(MenuPage page);
    public void updateMenuPage(MenuPage page);
    public void deleteMenuPage(long id);
    public List<MenuPage> getAllMenuPage();
    public List<MenuPage> listMenuPageByChapterId(long chapterId);
    public MenuPage getMenuPage(long id);
}