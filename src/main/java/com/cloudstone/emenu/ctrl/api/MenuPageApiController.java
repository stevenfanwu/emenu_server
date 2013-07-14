/**
 * @(#)MenuPageApiController.java, Jul 15, 2013. 
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

import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class MenuPageApiController extends BaseApiController {

    @RequestMapping(value="/api/pages", method=RequestMethod.POST)
    public @ResponseBody MenuPage addMenuPage(@RequestBody String body, HttpServletResponse resp) {
        MenuPage page = JsonUtils.fromJson(body, MenuPage.class);
        page = menuPageLogic.add(page);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return page;
    }
    
    @RequestMapping(value="/api/pages/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void delete(@PathVariable(value="id") long id,
            HttpServletResponse response) {
        menuPageLogic.deleteMenuPage(id);
    }

    @RequestMapping(value="/api/pages", method=RequestMethod.GET)
    public @ResponseBody List<MenuPage> getByMenuId(@RequestParam("chapterId") long chapterId) {
        return menuPageLogic.listMenuPage(chapterId);
    }
}
