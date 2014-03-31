/**
 * @(#)MenuApiController.java, 2013-7-8. 
 *
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishNote;
import com.cloudstone.emenu.data.DishTag;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 */
@Controller
public class MenuApiController extends BaseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(MenuApiController.class);

    @RequestMapping(value = "/api/menus", method = RequestMethod.POST)
    public
    @ResponseBody
    Menu addMenu(@RequestBody String body,
                 HttpServletRequest request,
                 HttpServletResponse resp) {
        EmenuContext context = newContext(request);
        Menu menu = JsonUtils.fromJson(body, Menu.class);
        menu = menuLogic.addMenu(context, menu);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return menu;
    }

    @RequestMapping(value = "/api/menus/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteMenu(@PathVariable(value = "id") int id,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.deleteMenu(context, id);
    }

    @RequestMapping(value = "/api/menus/unbind", method = RequestMethod.PUT)
    public
    @ResponseBody
    Dish unbindDish(@RequestParam("menuPageId") int menuPageId,
                    HttpServletRequest request,
                    @RequestParam("dishId") int dishId,
                    @RequestParam("pos") int pos) {
        EmenuContext context = newContext(request);
        menuLogic.unbindDish(context, menuPageId, dishId, pos);
        return Dish.getNullDish(pos);
    }

    @RequestMapping(value = "/api/menus/bind", method = RequestMethod.PUT)
    public
    @ResponseBody
    Dish bindDish(@RequestParam("menuPageId") int menuPageId,
                  @RequestParam("dishId") int dishId,
                  @RequestParam("pos") int pos,
                  HttpServletRequest request) {
        EmenuContext context = newContext(request);
        menuLogic.bindDish(context, menuPageId, dishId, pos);
        return menuLogic.getDish(context, dishId, false);
    }

    @RequestMapping(value = "/api/menus", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Menu> listMenu(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return menuLogic.getAllMenu(context);
    }

    @RequestMapping(value = "/api/menus/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Menu updateMenu(@PathVariable(value = "id") int id,
                    @RequestBody String body,
                    HttpServletRequest request,
                    HttpServletResponse response) {
        EmenuContext context = newContext(request);
        Menu menu = JsonUtils.fromJson(body, Menu.class);
        if (menu.getId() != id) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return menuLogic.updateMenu(context, menu);
    }

    @RequestMapping(value = "/api/pages", method = RequestMethod.POST)
    public
    @ResponseBody
    MenuPage addMenuPage(@RequestBody String body,
                         HttpServletRequest request,
                         HttpServletResponse resp) {
        EmenuContext context = newContext(request);
        MenuPage page = JsonUtils.fromJson(body, MenuPage.class);
        page = menuLogic.addMenuPage(context, page);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return page;
    }

    @RequestMapping(value = "/api/pages/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    MenuPage updateMenuPage(@PathVariable(value = "id") int id,
                            @RequestBody String body,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        EmenuContext context = newContext(request);
        MenuPage page = JsonUtils.fromJson(body, MenuPage.class);
        if (page.getId() != id) {
            throw new BadRequestError();
        }
        return menuLogic.updateMenuPage(context, page);
    }

    @RequestMapping(value = "/api/pages/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteMenuPage(@PathVariable(value = "id") int id,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.deleteMenuPage(context, id);
    }

    @RequestMapping(value = "/api/pages", method = RequestMethod.GET)
    public
    @ResponseBody
    List<MenuPage> getMenuPageByChapterId(
            @RequestParam("chapterId") int chapterId,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return menuLogic.listMenuPageByChapterId(context, chapterId);
    }

    @RequestMapping(value = "/api/chapters", method = RequestMethod.POST)
    public
    @ResponseBody
    Chapter addChapter(@RequestBody String body,
                       HttpServletRequest request,
                       HttpServletResponse resp) {
        EmenuContext context = newContext(request);
        Chapter chapter = JsonUtils.fromJson(body, Chapter.class);
        chapter = menuLogic.addChapter(context, chapter);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return chapter;
    }

    @RequestMapping(value = "/api/chapters/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteChapter(@PathVariable(value = "id") int id,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.deleteChapter(context, id);
    }

    @RequestMapping(value = "/api/chapters/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Chapter> listChapters(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return menuLogic.getAllChapter(context);
    }

    @RequestMapping(value = "/api/chapters", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Chapter> getChapters(
            HttpServletRequest request,
            @RequestParam(value = "menuId", defaultValue = "0") int menuId) {
        EmenuContext context = newContext(request);
        if (menuId == 0) {
            return menuLogic.getAllChapter(context);
        }
        return menuLogic.listChapterByMenuId(context, menuId);
    }

    @RequestMapping(value = "/api/chapters/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Chapter updateChapter(@PathVariable(value = "id") int id,
                          @RequestBody String body,
                          HttpServletRequest request,
                          HttpServletResponse response) {
        EmenuContext context = newContext(request);
        Chapter chapter = JsonUtils.fromJson(body, Chapter.class);
        if (chapter.getId() != id) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return menuLogic.updateChapter(context, chapter);
    }

    @RequestMapping(value = "/api/chapters/{id:[\\d]+}/up", method = RequestMethod.PUT)
    public void moveUpChapter(@PathVariable(value = "id") int id,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.move(context, id, true);
    }

    @RequestMapping(value = "/api/chapters/{id:[\\d]+}/down", method = RequestMethod.PUT)
    public void moveDownChapter(@PathVariable(value = "id") int id,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.move(context, id, false);
    }

    @RequestMapping(value = "/api/dishes/suggestion", method = RequestMethod.GET)
    public
    @ResponseBody
    List<IdName> getDishSuggestion(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return menuLogic.getDishSuggestion(context);
    }

    @RequestMapping(value = "/api/dishes", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Dish> listDish(
            @RequestParam(value = "menuPageId", required = false, defaultValue = "0") int menuPageId,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        if (menuPageId == 0) {
            return menuLogic.getAllDish(context);
        } else {
            return menuLogic.getDishByMenuPageId(context, menuPageId);
        }
    }

    @RequestMapping(value = "/api/dishes/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteDish(@PathVariable(value = "id") int dishId,
                           HttpServletRequest request,
                           HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.deleteDish(context, dishId);
    }

    @RequestMapping(value = "/api/dishes/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Dish updateDish(@PathVariable(value = "id") int dishId,
                    @RequestBody String body,
                    HttpServletRequest request,
                    HttpServletResponse response) {
        EmenuContext context = newContext(request);
        Dish dish = JsonUtils.fromJson(body, Dish.class);
        if (dish.getId() != dishId) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        if (dish.getMemberPrice() == 0) {
            dish.setMemberPrice(dish.getPrice());
        }
        return menuLogic.updateDish(context, dish);
    }

    @RequestMapping(value = "/api/dishes", method = RequestMethod.POST)
    public
    @ResponseBody
    Dish addDish(@RequestBody String body,
                 HttpServletRequest request,
                 HttpServletResponse response) {
        EmenuContext context = newContext(request);
        Dish dish = JsonUtils.fromJson(body, Dish.class);
        if (dish.getMemberPrice() == 0) {
            dish.setMemberPrice(dish.getPrice());
        }
        dish = menuLogic.addDish(context, dish);
        sendSuccess(response, HttpServletResponse.SC_CREATED);
        return dish;
    }

    @RequestMapping(value = "/api/dishes/{id:[\\d]+}/soldout", method = RequestMethod.PUT)
    public
    @ResponseBody
    Dish soldoutDish(@PathVariable(value = "id") int dishId,
                     HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return menuLogic.updateDishSoldout(context, dishId, true);
    }

    @RequestMapping(value = "/api/dishes/{id:[\\d]+}/unsoldout", method = RequestMethod.PUT)
    public
    @ResponseBody
    Dish unsoldoutDish(
            HttpServletRequest request,
            @PathVariable(value = "id") int dishId) {
        EmenuContext context = newContext(request);
        return menuLogic.updateDishSoldout(context, dishId, false);
    }

    @RequestMapping(value = "/api/dishes/unsoldout", method = RequestMethod.PUT)
    public void unsoldoutAllDishes(
            HttpServletRequest request,
            HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.updateDishesSoldout(context, false);
        sendSuccess(response, HttpServletResponse.SC_OK);
        return;
    }

    @RequestMapping(value = "/api/dishes/soldout", method = RequestMethod.PUT)
    public void soldoutAllDishes(
            HttpServletRequest request,
            HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.updateDishesSoldout(context, true);
        sendSuccess(response, HttpServletResponse.SC_OK);
        return;
    }

    @RequestMapping(value = "/api/dish/tags", method = RequestMethod.GET)
    public
    @ResponseBody
    List<DishTag> getAllDishTag(
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return menuLogic.listAllDishTag(context);
    }

    @RequestMapping(value = "/api/dish/tags", method = RequestMethod.POST)
    public
    @ResponseBody
    DishTag addDishTag(@RequestBody String body,
                       HttpServletRequest request) {
        EmenuContext context = newContext(request);
        DishTag tag = JsonUtils.fromJson(body, DishTag.class);
        tag = menuLogic.addDishTag(context, tag);
        return tag;
    }

    @RequestMapping(value = "/api/dish/tags/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    DishTag updateDishTag(@RequestBody String body,
                          @PathVariable(value = "id") int id,
                          HttpServletRequest request) {
        EmenuContext context = newContext(request);
        DishTag tag = JsonUtils.fromJson(body, DishTag.class);
        tag = menuLogic.updateDishTag(context, tag);
        return tag;
    }

    @RequestMapping(value = "/api/dish/tags/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteDishTag(@PathVariable(value = "id") int id,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.deleteDishTag(context, id);
    }

    @RequestMapping(value = "/api/dish/notes", method = RequestMethod.GET)
    public
    @ResponseBody
    List<DishNote> getAllDishNote(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return menuLogic.listAllDishNote(context);
    }

    @RequestMapping(value = "/api/dish/notes", method = RequestMethod.POST)
    public
    @ResponseBody
    DishNote addDishNote(@RequestBody String body,
                         HttpServletRequest request) {
        EmenuContext context = newContext(request);
        DishNote note = JsonUtils.fromJson(body, DishNote.class);
        note = menuLogic.addDishNote(context, note);
        return note;
    }

    @RequestMapping(value = "/api/dish/notes/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    DishNote updateDishNote(@RequestBody String body,
                            @PathVariable(value = "id") int id,
                            HttpServletRequest request
    ) {
        EmenuContext context = newContext(request);
        DishNote note = JsonUtils.fromJson(body, DishNote.class);
        note = menuLogic.updateDishNote(context, note);
        return note;
    }

    @RequestMapping(value = "/api/dish/notes/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteDishNote(@PathVariable(value = "id") int id,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        EmenuContext context = newContext(request);
        menuLogic.deleteDishNote(context, id);
    }
}