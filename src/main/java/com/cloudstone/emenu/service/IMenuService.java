/**
 * @(#)IMenuService.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishNote;
import com.cloudstone.emenu.data.DishTag;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.storage.db.IDishPageDb.DishPage;

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
    public Menu getMenuByName(String name);
    
    public void addChapter(Chapter chapter);
    public void updateChapter(Chapter chapter);
    public void deleteChapter(int id);
    public List<Chapter> getAllChapter();
    public List<Chapter> listChapterByMenuId(int menuId);
    public List<Chapter> listChapter(int[] ids);
    public Chapter getChapter(int id);
    public Chapter getChapterByName(String name);
    
    public void addDish(Dish dish);
    public List<Dish> getAllDish();
    public Dish getDish(int id);
    public Dish getDishByName(String name);
    public void deleteDish(int id);
    public void updateDish(Dish dish);
    public List<Dish> getDishByMenuPageId(int menuPageId);
    public List<IdName> getDishSuggestion();
    public void bindDish(int menuPageId, int dishId, int pos);
    public void unbindDish(int menuPageId, int dishId, int pos);
    public List<DishPage> listDishPage(int dishId);
    
    public void addMenuPage(MenuPage page);
    public void updateMenuPage(MenuPage page);
    public void deleteMenuPage(int id);
    public List<MenuPage> getAllMenuPage();
    public List<MenuPage> listMenuPageByChapterId(int chapterId);
    public List<MenuPage> listMenuPageByMenuId(int menuId);
    public List<MenuPage> listMenuPage(int[] ids);
    public MenuPage getMenuPage(int id);
    
    public List<DishTag> listAllDishTag();
    public void addDishTag(DishTag tag);
    public void updateDishTag(DishTag tag);
    public void deleteDishTag(int id);
    public DishTag getDishTag(int id);
    public DishTag getDishTagByName(String name);
    
    public List<DishNote> listAllDishNote();
    public void addDishNote(DishNote note);
    public void updateDishNote(DishNote note);
    public void deleteDishNote(int id);
    public DishNote getDishNote(int id);
    public DishNote getDishNoteByName(String name);
}