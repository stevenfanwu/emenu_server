/**
 * @(#)MenuApiController.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class MenuApiController extends BaseApiController {

    @RequestMapping(value="/api/menus", method=RequestMethod.POST)
    public @ResponseBody Menu addMenu(@RequestBody String body, HttpServletResponse resp) {
        Menu menu = JsonUtils.fromJson(body, Menu.class);
        menu = menuLogic.addMenu(menu);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return menu;
    }
    
    @RequestMapping(value="/api/menus/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void deleteMenu(@PathVariable(value="id") long id,
            HttpServletResponse response) {
        menuLogic.deleteMenu(id);
    }
    
    @RequestMapping(value="/api/menus/unbind", method=RequestMethod.PUT)
    public @ResponseBody Dish unbindDish(@RequestParam("menuPageId") long menuPageId,
            @RequestParam("dishId") long dishId,
            @RequestParam("pos") int pos) {
        menuLogic.unbindDish(menuPageId, dishId, pos);
        return Dish.getNullDish(pos);
    }
    
    @RequestMapping(value="/api/menus/bind", method=RequestMethod.PUT)
    public @ResponseBody Dish bindDish(@RequestParam("menuPageId") long menuPageId,
            @RequestParam("dishId") long dishId,
            @RequestParam("pos") int pos) {
        menuLogic.bindDish(menuPageId, dishId, pos);
        return menuLogic.getDish(dishId);
    }

    @RequestMapping(value="/api/menus", method=RequestMethod.GET)
    public @ResponseBody List<Menu> listMenu() {
        return menuLogic.getAllMenu();
    }
    
    @RequestMapping(value="/api/menus/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody Menu updateMenu(@PathVariable(value="id") long id,
            @RequestBody String body, HttpServletResponse response) {
        Menu menu = JsonUtils.fromJson(body, Menu.class);
        if (menu.getId() != id) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return menuLogic.updateMenu(menu);
    }

    @RequestMapping(value="/api/pages", method=RequestMethod.POST)
    public @ResponseBody MenuPage addMenuPage(@RequestBody String body, HttpServletResponse resp) {
        MenuPage page = JsonUtils.fromJson(body, MenuPage.class);
        page = menuLogic.addMenuPage(page);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return page;
    }
    
    @RequestMapping(value="/api/pages/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void deleteMenuPage(@PathVariable(value="id") long id,
            HttpServletResponse response) {
        menuLogic.deleteMenuPage(id);
    }

    @RequestMapping(value="/api/pages", method=RequestMethod.GET)
    public @ResponseBody List<MenuPage> getMenuPageByChapterId(@RequestParam("chapterId") long chapterId) {
        return menuLogic.listMenuPage(chapterId);
    }

    @RequestMapping(value="/api/chapters", method=RequestMethod.POST)
    public @ResponseBody Chapter addChapter(@RequestBody String body, HttpServletResponse resp) {
        Chapter chapter = JsonUtils.fromJson(body, Chapter.class);
        chapter = menuLogic.addChapter(chapter);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return chapter;
    }
    
    @RequestMapping(value="/api/chapters/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void deleteChapter(@PathVariable(value="id") long id,
            HttpServletResponse response) {
        menuLogic.deleteChapter(id);
    }

    @RequestMapping(value="/api/chapters/all", method=RequestMethod.GET)
    public @ResponseBody List<Chapter> listChapters() {
        return menuLogic.getAllChapter();
    }
    
    @RequestMapping(value="/api/chapters", method=RequestMethod.GET)
    public @ResponseBody List<Chapter> getChapterByMenuId(@RequestParam("menuId") long menuId) {
        return menuLogic.listChapterByMenuId(menuId);
    }
    
    @RequestMapping(value="/api/chapters/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody Chapter updateChapter(@PathVariable(value="id") long id,
            @RequestBody String body, HttpServletResponse response) {
        Chapter chapter = JsonUtils.fromJson(body, Chapter.class);
        if (chapter.getId() != id) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return menuLogic.updateChapter(chapter);
    }
    
    
    @RequestMapping(value="/api/dishes/suggestion", method=RequestMethod.GET)
    public @ResponseBody List<IdName> getDishSuggestion() {
        return menuLogic.getDishSuggestion();
    }

    @RequestMapping(value="/api/dishes", method=RequestMethod.GET)
    public @ResponseBody List<Dish> listDish(
            @RequestParam(value="menuPageId", required=false, defaultValue="0") long menuPageId) {
        if (menuPageId == 0) {
            return menuLogic.getAllDish();
        } else {
            return menuLogic.getDishByMenuPageId(menuPageId);
        }
    }
    
    @RequestMapping(value="/api/dishes/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void deleteDish(@PathVariable(value="id") long dishId, HttpServletResponse response) {
        menuLogic.deleteDish(dishId);
    }
    
    @RequestMapping(value="/api/dishes/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody Dish updateDish(@PathVariable(value="id") long dishId,
            @RequestBody String body, HttpServletResponse response) {
        Dish dish = JsonUtils.fromJson(body, Dish.class);
        if (dish.getId() != dishId) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return menuLogic.updateDish(dish);
    }
    
    @RequestMapping(value="/api/dishes", method=RequestMethod.POST)
    public @ResponseBody Dish addDish(@RequestBody String body, HttpServletResponse response) {
        Dish dish = JsonUtils.fromJson(body, Dish.class);
        dish = menuLogic.addDish(dish);
        sendSuccess(response, HttpServletResponse.SC_CREATED);
        return dish;
    }
}
