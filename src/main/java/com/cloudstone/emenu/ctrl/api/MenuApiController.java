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
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.Menu;
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
    public void delete(@PathVariable(value="id") long id,
            HttpServletResponse response) {
        menuLogic.deleteMenu(id);
    }

    @RequestMapping(value="/api/menus", method=RequestMethod.GET)
    public @ResponseBody List<Menu> get() {
        return menuLogic.getAllMenu();
    }
    
    @RequestMapping(value="/api/menus/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody Menu update(@PathVariable(value="id") long id,
            @RequestBody String body, HttpServletResponse response) {
        Menu menu = JsonUtils.fromJson(body, Menu.class);
        if (menu.getId() != id) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return menuLogic.updateMenu(menu);
    }
}
