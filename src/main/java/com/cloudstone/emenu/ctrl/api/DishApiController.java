/**
 * @(#)DishApiController.java, 2013-7-7. 
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

import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.util.JsonUtils;


/**
 * @author xuhongfeng
 *
 */
@Controller
public class DishApiController extends BaseApiController {

    @RequestMapping(value="/api/dishes")
    public @ResponseBody List<Dish> get() {
        return dishLogic.getAll();
    }
    
    @RequestMapping(value="/api/dishs/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void delete(@PathVariable(value="id") long dishId, HttpServletResponse response) {
        dishLogic.delete(dishId);
    }
    
    @RequestMapping(value="/api/dishs/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody Dish update(@PathVariable(value="id") long dishId,
            @RequestBody String body, HttpServletResponse response) {
        Dish dish = JsonUtils.fromJson(body, Dish.class);
        if (dish.getId() != dishId) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return dishLogic.update(dish);
    }
    
    @RequestMapping(value="/api/dishs", method=RequestMethod.POST)
    public @ResponseBody Dish add(@RequestBody String body, HttpServletResponse response) {
        Dish dish = JsonUtils.fromJson(body, Dish.class);
        dish = dishLogic.add(dish);
        sendSuccess(response, HttpServletResponse.SC_CREATED);
        return dish;
    }

}
