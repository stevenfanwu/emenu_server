/**
 * @(#)MenuLogic.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishTag;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.service.MenuService;
import com.cloudstone.emenu.util.DataUtils;
import com.cloudstone.emenu.util.StringUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class MenuLogic extends BaseLogic {
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private ImageLogic imageLogic;
    
    /* ---------- menu ---------- */
    public Menu addMenu(Menu menu) {
        Menu old = menuService.getMenuByName(menu.getName());
        if (old!=null && !old.isDeleted()) {
            throw new DataConflictException("该菜单已存在");
        }
        
        long now = System.currentTimeMillis();
        menu.setUpdateTime(now);
        if (old != null) {
            menu.setId(old.getId());
            menu.setCreatedTime(old.getCreatedTime());
            menuService.updateMenu(menu);
        } else {
            menu.setCreatedTime(now);
            menuService.addMenu(menu);
        }
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
        List<Menu> menus = menuService.getAllMenu();
        DataUtils.filterDeleted(menus);
        return menus;
    }
    
    public Menu updateMenu(Menu menu) {
        Menu old = menuService.getMenuByName(menu.getName());
        if (old!=null && old.getId()!=menu.getId() && !old.isDeleted()) {
            throw new DataConflictException("该菜单已存在");
        }
        menu.setUpdateTime(System.currentTimeMillis());
        menuService.updateMenu(menu);
        return menuService.getMenu(menu.getId());
    }
    
    public void deleteMenu(final int id) {
        menuService.deleteMenu(id);
    }
    
    /* ---------- dish ---------- */
    public Dish addDish(Dish dish) {
        Dish old = menuService.getDishByName(dish.getName());
        if (old!=null && !old.isDeleted()) {
            throw new DataConflictException("该菜品已存在");
        }
        long now = System.currentTimeMillis();
        dish.setUpdateTime(now);
        
        //save image
        String uriData = dish.getUriData();
        if (!StringUtils.isBlank(uriData)) {
            String imageId = imageLogic.saveDishImage(uriData);
            dish.setImageId(imageId);
        }
        
        if (old != null) {
            dish.setId(old.getId());
            dish.setCreatedTime(old.getCreatedTime());
            menuService.updateDish(dish);
        } else {
            dish.setCreatedTime(now);
            menuService.addDish(dish);
        }
        //get with uriData
        return getDish(dish.getId(), true);
    }
    
    public Dish updateDish(Dish dish) {
        Dish old = menuService.getDishByName(dish.getName());
        if (old!=null && old.getId()!=dish.getId() && !old.isDeleted()) {
            throw new DataConflictException("该菜品已存在");
        }
        dish.setUpdateTime(System.currentTimeMillis());
        //save image
        if (!StringUtils.isBlank(dish.getUriData())) {
            String imageId = imageLogic.saveDishImage(dish.getUriData());
            dish.setImageId(imageId);
        }
        //save to db
        menuService.updateDish(dish);
        //get with uriData
        return getDish(dish.getId(), true);
    }
    
    public List<IdName> getDishSuggestion() {
        List<IdName> names = menuService.getDishSuggestion();
        DataUtils.filterDeleted(names);
        return names;
    }
    
    public List<Dish> getAllDish() {
        List<Dish> dishes = menuService.getAllDish();
        DataUtils.filterDeleted(dishes);
        return dishes;
    }
    
    public List<Dish> getDishByMenuPageId(int menuPageId) {
        return menuService.getDishByMenuPageId(menuPageId);
    }
    
    public void deleteDish(int id) {
        menuService.deleteDish(id);
    }

    public Dish getDish(int id, boolean withUriData) {
        Dish dish = menuService.getDish(id);
        String imageId = dish.getImageId();
        if (withUriData && !StringUtils.isBlank(imageId)) {
            String uriData = imageLogic.getDishUriData(imageId);
            dish.setUriData(uriData);
        }
        return dish;
    }
    
    /* ---------- chapter ---------- */
    public Chapter addChapter(Chapter chapter) {
        Chapter old = menuService.getChapterByName(chapter.getName());
        if (old!= null && old.getMenuId()==chapter.getMenuId() && !old.isDeleted()) {
            throw new DataConflictException("该分类已存在");
        }
        long now = System.currentTimeMillis();
        chapter.setUpdateTime(now);
        if (old != null) {
            chapter.setId(old.getId());
            chapter.setCreatedTime(old.getCreatedTime());
            menuService.updateChapter(chapter);
        } else {
            chapter.setCreatedTime(now);
            menuService.addChapter(chapter);
        }
        return menuService.getChapter(chapter.getId());
    }
    
    public Chapter getChapter(int id) {
        return menuService.getChapter(id);
    }
    
    public List<Chapter> getAllChapter() {
        List<Chapter> chapters = menuService.getAllChapter();
        DataUtils.filterDeleted(chapters);
        return chapters;
    }
    
    public Chapter updateChapter(Chapter chapter) {
        Chapter old = menuService.getChapterByName(chapter.getName());
        if (old!=null && old.getId()!=chapter.getId() && chapter.getMenuId()==old.getMenuId()
                && !old.isDeleted()) {
            throw new DataConflictException("该分类已存在");
        }
        chapter.setUpdateTime(System.currentTimeMillis());
        menuService.updateChapter(chapter);
        return menuService.getChapter(chapter.getId());
    }
    
    public void deleteChapter(final int id) {
        menuService.deleteChapter(id);
    }
    
    public List<Chapter> listChapterByMenuId(int menuId) {
        List<Chapter> chapters = menuService.listChapterByMenuId(menuId);
        DataUtils.filterDeleted(chapters);
        return chapters;
    }
    
    public List<MenuPage> listMenuPageByChapterId(int chapterId) {
        List<MenuPage> datas = menuService.listMenuPageByChapterId(chapterId);
        DataUtils.filterDeleted(datas);
        return datas;
    }
    
    /* ---------- MenuPage ---------- */
    public MenuPage addMenuPage(MenuPage page) {
        long now = System.currentTimeMillis();
        page.setCreatedTime(now);
        page.setUpdateTime(now);
        menuService.addMenuPage(page);
        return menuService.getMenuPage(page.getId());
    }
    
    public void deleteMenuPage(int id) {
        menuService.deleteMenuPage(id);
    }
    
    public List<MenuPage> listMenuPage(int chapterId) {
        List<MenuPage> pages = menuService.listMenuPageByChapterId(chapterId);
        DataUtils.filterDeleted(pages);
        return pages;
    }
    
    /* ---------- DishTag ---------- */
    public List<DishTag> listAllDisTag() {
        List<DishTag> tags = menuService.listAllDishTag();
        DataUtils.filterDeleted(tags);
        return tags;
    }
    
    public DishTag addDishTag(DishTag tag) {
        DishTag old = menuService.getDishTagByName(tag.getName());
        if (old!= null && !old.isDeleted()) {
            throw new DataConflictException("该标签已存在");
        }
        long now = System.currentTimeMillis();
        tag.setUpdateTime(now);
        if (old != null) {
            tag.setId(old.getId());
            tag.setCreatedTime(old.getCreatedTime());
            menuService.updateDishTag(tag);
        } else {
            tag.setUpdateTime(now);
            menuService.addDishTag(tag);
        }
        return menuService.getDishTag(tag.getId());
    }
    public DishTag updateDishTag(DishTag tag) {
        DishTag old = menuService.getDishTagByName(tag.getName());
        if (old!= null && old.getId()!=tag.getId() && !old.isDeleted()) {
            throw new DataConflictException("该标签已存在");
        }
        tag.setUpdateTime(System.currentTimeMillis());
        menuService.updateDishTag(tag);
        return menuService.getDishTag(tag.getId());
    }
    public void deleteDishTag(int id) {
        menuService.deleteDishTag(id);
    }
}
