package com.cloudstone.emenu.ctrl.api;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Restaurant;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.util.JsonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class RestaurantApiController extends BaseApiController {

    @RequestMapping(value = "/api/restaurants", method = RequestMethod.POST)
    public
    @ResponseBody
    Restaurant add(@RequestBody String body,
                   HttpServletRequest request,
                   HttpServletResponse resp) {
        EmenuContext context = newContext(request);
        Restaurant restaurant = JsonUtils.fromJson(body, Restaurant.class);
        restaurant = restaurantLogic.add(context, restaurant);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return restaurant;
    }

    @RequestMapping(value = "/api/restaurants", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Restaurant> get(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return restaurantLogic.getAll(context);
    }

    @RequestMapping(value = "/api/restaurants/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Restaurant update(@PathVariable(value = "id") int userId,
                @RequestBody String body,
                HttpServletRequest request,
                HttpServletResponse response) {
        EmenuContext context = newContext(request);
        User loginUser = getLoginUser(request);
        if (loginUser.getType() != Const.UserType.SUPER_USER) {
            sendError(response, HttpServletResponse.SC_FORBIDDEN);
            return null;
        }
        Restaurant restaurant = JsonUtils.fromJson(body, Restaurant.class);
        return restaurantLogic.update(context, restaurant);
    }

    @RequestMapping(value = "/api/restaurants/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") int id,
                          HttpServletRequest request,
                          HttpServletResponse resp) {
        EmenuContext context = newContext(request);
        restaurantLogic.delete(context, id);
    }

}
