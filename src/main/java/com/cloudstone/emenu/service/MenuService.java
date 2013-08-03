/**
 * @(#)MenuService.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishTag;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.exception.NotFoundException;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IDishPageDb.DishPage;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 *
 */
@Service
public class MenuService extends BaseService implements IMenuService {

    /* ---------- menu ---------- */
    @Override
    public Menu getMenuByName(String name) {
        try {
            return menuDb.getByName(name);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void addMenu(Menu menu) {
        try {
            menuDb.addMenu(menu);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void updateMenu(Menu menu) {
        try {
            menuDb.updateMenu(menu);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
        
    }
    

    @Override
    public void deleteMenu(int id) {
        try {
            menuDb.deleteMenu(id);
            deleteChaptersByMenuId(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Menu> getAllMenu() {
        try {
            return menuDb.getAllMenu();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public Menu getMenu(int id) {
        try {
            return menuDb.getMenu(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    /* --------- chapter ---------- */
    @Override
    public void addChapter(Chapter chapter) {
        try {
            chapterDb.addChapter(chapter);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void updateChapter(Chapter chapter) {
        try {
            chapterDb.updateChapter(chapter);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
        
    }

    @Override
    public void deleteChapter(int id) {
        try {
            chapterDb.deleteChapter(id);
            List<MenuPage> pages = listMenuPageByChapterId(id);
            for (MenuPage page:pages) {
                deleteMenuPage(page.getId());
            }
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Chapter> getAllChapter() {
        try {
            return chapterDb.getAllChapter();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public Chapter getChapterByName(String name) {
        try {
            return chapterDb.getChapterByName(name);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public Chapter getChapter(int id) {
        try {
            return chapterDb.getChapter(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Chapter> listChapterByMenuId(int menuId) {
        try {
            return chapterDb.listChapters(menuId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    public void deleteChaptersByMenuId(int menuId) {
        List<Chapter> chapters = listChapterByMenuId(menuId);
        for(Chapter chapter:chapters) {
            deleteChapter(chapter.getId());
        }
    }

    /* ---------- Dish ---------- */
    
    @Override
    public void bindDish(int menuPageId, int dishId, int pos) {
        try {
            dishPageDb.add(menuPageId, dishId, pos);
            checkDishInMenu(dishId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void unbindDish(int menuPageId, int dishId, int pos) {
        try {
            dishPageDb.delete(menuPageId, pos);
            checkDishInMenu(dishId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void addDish(Dish dish) {
        try {
            dishDb.add(dish);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Dish> getAllDish() {
        try {
            return dishDb.getAll();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void deleteDish(int id) {
        try {
            dishDb.delete(id);
            dishPageDb.deleteByDishId(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void updateDish(Dish dish) {
        try {
            dishDb.update(dish);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public Dish getDish(int id) {
        try {
            return dishDb.get(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public Dish getDishByName(String name) {
        try {
            return dishDb.getByName(name);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    /* --------- MenuPage ---------- */
    @Override
    public void addMenuPage(MenuPage page) {
        try {
            menuPageDb.addMenuPage(page);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void updateMenuPage(MenuPage page) {
        try {
            menuPageDb.updateMenuPage(page);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void deleteMenuPage(int id) {
        try {
            //delete page
            menuPageDb.deleteMenuPage(id);
            List<DishPage> relation = dishPageDb.getByMenuPageId(id);
            dishPageDb.deleteByMenuPageId(id);
            for (DishPage r:relation) {
                int dishId = r.getDishId();
                checkDishInMenu(dishId);
            }
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    private void checkDishInMenu(int dishId) throws SQLiteException {
        Dish dish = dishDb.get(dishId);
        if (dish != null) {
            int count = dishPageDb.countByDishId(dishId);
            int oldStatus = dish.getStatus();
            int status = count==0? Const.DishStatus.STATUS_INIT : Const.DishStatus.STATUS_IN_MENU;
            if (status != oldStatus) {
                dish.setStatus(status);
                dishDb.update(dish);
            }
        }
    }

    @Override
    public List<MenuPage> getAllMenuPage() {
        try {
            return menuPageDb.getAllMenuPage();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<MenuPage> listMenuPageByChapterId(int chapterId) {
        try {
            return menuPageDb.listMenuPages(chapterId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public List<MenuPage> listMenuPageByMenuId(int menuId) {
        try {
            List<MenuPage> ret = new ArrayList<MenuPage>();
            List<Chapter> chapters = chapterDb.listChapters(menuId);
            for (Chapter chapter:chapters) {
                List<MenuPage> pages = menuPageDb.listMenuPages(chapter.getId());
                ret.addAll(pages);
            }
            return ret;
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public MenuPage getMenuPage(int id) {
        try {
            return menuPageDb.getMenuPage(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public List<Dish> getDishByMenuPageId(int menuPageId) {
        List<Dish> ret = new ArrayList<Dish>();
        try {
            MenuPage page = getMenuPage(menuPageId);
            if (page == null) {
                throw new NotFoundException("");
            }
            Dish[] dishes = new Dish[page.getDishCount()];
            List<DishPage> relation = dishPageDb.getByMenuPageId(menuPageId);
            DataUtils.filterDeleted(relation);
            for (DishPage r:relation) {
                int dishId = r.getDishId();
                int pos = r.getPos();
                dishes[pos] = getDish(dishId);
            }
            for (int i=0; i<dishes.length; i++) {
                Dish dish = dishes[i];
                if (dish == null) {
                    dish = Dish.getNullDish(i);
                }
                ret.add(dish);
            }
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
        return ret;
    }
    
    @Override
    public List<IdName> getDishSuggestion() {
        try {
            return dishDb.getDishSuggestion();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    /* ---------- DishTag ---------- */
    

    @Override
    public List<DishTag> listAllDishTag() {
        try {
            return dishTagDb.listAll();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void addDishTag(DishTag tag) {
        try {
            dishTagDb.addDishTag(tag);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void updateDishTag(DishTag tag) {
        try {
            dishTagDb.updateDishTag(tag);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void deleteDishTag(int id) {
        try {
            dishTagDb.deleteDishTag(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public DishTag getDishTagByName(String name) {
        try {
            return dishTagDb.getDishTagByName(name);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public DishTag getDishTag(int id) {
        try {
            return dishTagDb.getDishTag(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
}
