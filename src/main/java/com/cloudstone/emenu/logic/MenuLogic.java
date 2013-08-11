/**
 * @(#)MenuLogic.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishNote;
import com.cloudstone.emenu.data.DishTag;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.exception.DbNotFoundException;
import com.cloudstone.emenu.exception.PreconditionFailedException;
import com.cloudstone.emenu.service.IMenuService;
import com.cloudstone.emenu.storage.db.IDishPageDb.DishPage;
import com.cloudstone.emenu.util.CollectionUtils;
import com.cloudstone.emenu.util.CollectionUtils.Tester;
import com.cloudstone.emenu.util.DataUtils;
import com.cloudstone.emenu.util.StringUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class MenuLogic extends BaseLogic {
    @Autowired
    private IMenuService menuService;
    
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
        MenuPage page = getMenuPage(menuPageId);
        if (page == null) {
            throw new PreconditionFailedException("该菜单页不存在");
        }
        if (isDishInChapter(dishId, page.getChapterId())) {
            throw new PreconditionFailedException("当前菜单分类已经添加过该菜品");
        }
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
    
    public Menu getCurrentMenu() {
        //TODO
        List<Menu> menus = getAllMenu();
        if (menus.size() > 0) {
            return menus.get(0);
        }
        return null;
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

    public Dish updateDishSoldout(int id, boolean soldout) {
        Dish dish = menuService.getDish(id);
        if (dish == null || dish.isDeleted()) {
            throw new DbNotFoundException("没有这个菜或者这个菜已经被删除了");
        }
        dish.setSoldout(soldout);
        menuService.updateDish(dish);
        return getDish(dish.getId(), true);
    }

    public void updateDishesSoldout(boolean soldout) {
        List<Dish> dishes = menuService.getAllDish();
        DataUtils.filterDeleted(dishes);
        for (Dish dish : dishes) {
            dish.setSoldout(soldout);
            menuService.updateDish(dish);
        }
    }

    /* ---------- chapter ---------- */
    public Chapter addChapter(Chapter chapter) {
        Chapter old = menuService.getChapterByName(chapter.getName());
        if (old!= null && old.getMenuId()==chapter.getMenuId() && !old.isDeleted()) {
            throw new DataConflictException("该分类已存在");
        }
        long now = System.currentTimeMillis();
        chapter.setUpdateTime(now);
        if (old != null && old.getMenuId()==chapter.getMenuId()) {
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
    
    public List<Chapter> listChapters(final int menuId, int dishId) {
        List<MenuPage> pages = listMenuPageByDishId(dishId);
        Set<Integer> chapterIds = new HashSet<Integer>();
        for (MenuPage p:pages) {
            chapterIds.add(p.getChapterId());
        }
        List<Chapter> chapters = menuService.listChapter(CollectionUtils.toIntArray(chapterIds));
        DataUtils.filterDeleted(chapters);
        CollectionUtils.filter(chapters, new Tester<Chapter>() {
            @Override
            public boolean test(Chapter data) {
                return data.getMenuId() == menuId;
            }
        });
        return chapters;
    }
    
    public boolean isDishInChapter(int dishId, int chapterId) {
        List<MenuPage> pages = listMenuPageByChapterId(chapterId);
        List<MenuPage> relations = listMenuPageByDishId(dishId);
        for (MenuPage p:pages) {
            for (MenuPage r:relations) {
                if (p.getId() == r.getId()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public List<DishPage> listDishPage(int dishId) {
        List<DishPage> dishPages = menuService.listDishPage(dishId);
        DataUtils.filterDeleted(dishPages);
        return dishPages;
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
    
    /* ---------- MenuPage ---------- */
    
    public List<MenuPage> listMenuPageByDishId(int dishId) {
        List<DishPage> relations = listDishPage(dishId);
        Set<Integer> pageIds = new HashSet<Integer>();
        for (DishPage r:relations) {
            pageIds.add(r.getMenuPageId());
        }
        List<MenuPage> pages = menuService.listMenuPage(CollectionUtils.toIntArray(pageIds));
        DataUtils.filterDeleted(pages);
        return pages;
    }
    
    public List<MenuPage> listMenuPageByChapterId(int chapterId) {
        List<MenuPage> datas = menuService.listMenuPageByChapterId(chapterId);
        DataUtils.filterDeleted(datas);
        
        for (int i=1; i<=datas.size(); i++) {
            MenuPage p = datas.get(i-1);
            if (p.getOrdinal() != i) {
                p.setOrdinal(i);
                innnerUpdateMenuPage(p);
            }
        }
        return datas;
    }
    
    private void innnerUpdateMenuPage(MenuPage p) {
        p.setUpdateTime(System.currentTimeMillis());
        menuService.updateMenuPage(p);
    }
    
    public MenuPage addMenuPage(MenuPage page) {
        List<MenuPage> pages = listMenuPageByChapterId(page.getChapterId());
        for (int i=page.getOrdinal(); i<=pages.size(); i++) {
            MenuPage p = pages.get(i-1);
            p.setOrdinal(i+1);
            innnerUpdateMenuPage(p);
        }
        long now = System.currentTimeMillis();
        page.setCreatedTime(now);
        page.setUpdateTime(now);
        menuService.addMenuPage(page);
        return menuService.getMenuPage(page.getId());
    }
    
    public void deleteMenuPage(int id) {
        MenuPage old = getMenuPage(id);
        if (old==null || old.isDeleted()) {
            throw new BadRequestError();
        }
        List<MenuPage> pages = listMenuPageByChapterId(old.getChapterId());
        for (int i=old.getOrdinal()+1; i<=pages.size(); i++) {
            MenuPage p = pages.get(i-1);
            p.setOrdinal(p.getOrdinal()-1);
            innnerUpdateMenuPage(p);
        }
        menuService.deleteMenuPage(id);
    }
    
    public MenuPage getMenuPage(int id) {
        return menuService.getMenuPage(id);
    }
    
    public MenuPage updateMenuPage(MenuPage page) {
        MenuPage old = getMenuPage(page.getId());
        if (old == null) {
            throw new BadRequestError();
        }
        if (old.getOrdinal() != page.getOrdinal()) {
            List<MenuPage> pages = listMenuPageByChapterId(page.getChapterId());
            if (page.getOrdinal() < old.getOrdinal()) {
                for (int i=page.getOrdinal(); i<=old.getOrdinal()-1; i++) {
                    MenuPage p = pages.get(i-1);
                    p.setOrdinal(p.getOrdinal()+1);
                    innnerUpdateMenuPage(p);
                }
            } else {
                for (int i=old.getOrdinal()+1; i<=page.getOrdinal(); i++) {
                    MenuPage p = pages.get(i-1);
                    p.setOrdinal(p.getOrdinal()-1);
                    innnerUpdateMenuPage(p);
                }
            }
        }
        innnerUpdateMenuPage(page);
        return menuService.getMenuPage(page.getId());
    }
    
    /* ---------- DishTag ---------- */
    public List<DishTag> listAllDishTag() {
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
    
    /* ---------- DishNote ---------- */
    public List<DishNote> listAllDishNote() {
        List<DishNote> notes = menuService.listAllDishNote();
        DataUtils.filterDeleted(notes);
        return notes;
    }
    
    public DishNote addDishNote(DishNote note) {
        DishNote old = menuService.getDishNoteByName(note.getName());
        if (old!= null && !old.isDeleted()) {
            throw new DataConflictException("该菜品备注已存在");
        }
        long now = System.currentTimeMillis();
        note.setUpdateTime(now);
        if (old != null) {
            note.setId(old.getId());
            note.setCreatedTime(old.getCreatedTime());
            menuService.updateDishNote(note);
        } else {
            note.setUpdateTime(now);
            menuService.addDishNote(note);
        }
        return menuService.getDishNote(note.getId());
    }
    public DishNote updateDishNote(DishNote note) {
        DishNote old = menuService.getDishNoteByName(note.getName());
        if (old!= null && old.getId()!=note.getId() && !old.isDeleted()) {
            throw new DataConflictException("该菜品备注已存在");
        }
        note.setUpdateTime(System.currentTimeMillis());
        menuService.updateDishNote(note);
        return menuService.getDishNote(note.getId());
    }
    public void deleteDishNote(int id) {
        menuService.deleteDishNote(id);
    }
}
