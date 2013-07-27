/**
 * @(#)MenuLogic.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishTag;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.file.ImageStorage;

/**
 * @author xuhongfeng
 *
 */
@Component
public class MenuLogic extends BaseLogic {
    //TODO
    @Autowired
    private ImageStorage imageStorage;

    /* ---------- menu ---------- */
    public Menu addMenu(Menu menu) {
        menuService.addMenu(menu);
        return menuService.getMenu(menu.getId());
    }
    
    public void bindDish(int menuPageId, int dishId, int pos) {
        menuService.bindDish(menuPageId, dishId, pos);
    }
    
    public void unbindDish(int menuPageId, int dishId, int pos) {
        menuService.unbindDish(menuPageId, dishId, pos);
    }
    
    public Menu getMenu(int id) {
        return menuService.getMenu(id);
    }
    
    public List<Menu> getAllMenu() {
        return menuService.getAllMenu();
    }
    
    public Menu updateMenu(Menu menu) {
        menuService.updateMenu(menu);
        return menuService.getMenu(menu.getId());
    }
    
    public void deleteMenu(final int id) {
        menuService.deleteMenu(id);
    }
    
    /* ---------- dish ---------- */
    public Dish addDish(Dish dish) {
        menuService.addDish(dish);
        return menuService.getDish(dish.getId());
    }
    
    public Dish updateDish(Dish dish) {
        menuService.updateDish(dish);
        return menuService.getDish(dish.getId());
    }
    
    public List<IdName> getDishSuggestion() {
        return menuService.getDishSuggestion();
    }
    
    public List<Dish> getAllDish() {
        List<Dish> dishes = menuService.getAllDish();
        for (Dish dish:dishes) {
            try {
                imageStorage.saveDishImage(dish.getImageData());
            } catch (IOException e) {
                throw new ServerError(e);
            }
        }
        return dishes;
    }
    
    public List<Dish> getDishByMenuPageId(int menuPageId) {
        return menuService.getDishByMenuPageId(menuPageId);
    }
    
    public void deleteDish(int id) {
        menuService.deleteDish(id);
    }

    public Dish getDish(int id) {
        return menuService.getDish(id);
    }
    
    /* ---------- chapter ---------- */
    public Chapter addChapter(Chapter chapter) {
        menuService.addChapter(chapter);
        return menuService.getChapter(chapter.getId());
    }
    
    public Chapter getChapter(int id) {
        return menuService.getChapter(id);
    }
    
    public List<Chapter> getAllChapter() {
        return menuService.getAllChapter();
    }
    
    public Chapter updateChapter(Chapter chapter) {
        menuService.updateChapter(chapter);
        return menuService.getChapter(chapter.getId());
    }
    
    public void deleteChapter(final int id) {
        menuService.deleteChapter(id);
    }
    
    public List<Chapter> listChapterByMenuId(int menuId) {
        return menuService.listChapterByMenuId(menuId);
    }
    
    /* ---------- MenuPage ---------- */
    public MenuPage addMenuPage(MenuPage page) {
        menuService.addMenuPage(page);
        return menuService.getMenuPage(page.getId());
    }
    
    public void deleteMenuPage(int id) {
        menuService.deleteMenuPage(id);
    }
    
    public List<MenuPage> listMenuPage(int chapterId) {
        return menuService.listMenuPageByChapterId(chapterId);
    }
    
    /* ---------- DishTag ---------- */
    public List<DishTag> listAllDisTag() {
        return menuService.listAllDishTag();
    }
    
    public DishTag addDishTag(DishTag tag) {
        menuService.addDishTag(tag);
        return menuService.getDishTag(tag.getId());
    }
    public DishTag updateDishTag(DishTag tag) {
        menuService.updateDishTag(tag);
        return menuService.getDishTag(tag.getId());
    }
    public void deleteDishTag(int id) {
        menuService.deleteDishTag(id);
    }
}
