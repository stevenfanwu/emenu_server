/**
 * @(#)IMenuService.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishTag;
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
    public void deleteMenu(int id);
    public List<Menu> getAllMenu();
    public Menu getMenu(int id);
    
    public void addChapter(Chapter chapter);
    public void updateChapter(Chapter chapter);
    public void deleteChapter(int id);
    public List<Chapter> getAllChapter();
    public List<Chapter> listChapterByMenuId(int menuId);
    public Chapter getChapter(int id);
    
    public void addDish(Dish dish);
    public List<Dish> getAllDish();
    public Dish getDish(int id);
    public void deleteDish(int id);
    public void updateDish(Dish dish);
    public List<Dish> getDishByMenuPageId(int menuPageId);
    public List<IdName> getDishSuggestion();
    public void bindDish(int menuPageId, int dishId, int pos);
    public void unbindDish(int menuPageId, int dishId, int pos);
    
    public void addMenuPage(MenuPage page);
    public void updateMenuPage(MenuPage page);
    public void deleteMenuPage(int id);
    public List<MenuPage> getAllMenuPage();
    public List<MenuPage> listMenuPageByChapterId(int chapterId);
    public List<MenuPage> listMenuPageByMenuId(int menuId);
    public MenuPage getMenuPage(int id);
    
    public List<DishTag> listAllDishTag();
    public void addDishTag(DishTag tag);
    public void updateDishTag(DishTag tag);
    public void deleteDishTag(int id);
    public DishTag getDishTag(int id);
}