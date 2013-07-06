/**
 * @(#)UserApiController.java, Jun 16, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.exception.UserNameConflictedException;
import com.cloudstone.emenu.util.AuthHelper;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class UserApiController extends BaseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(UserApiController.class);
    
    @Autowired
    private AuthHelper authHelper;
    
    //TODO encrypt password
    @RequestMapping(value="/api/login", method=RequestMethod.POST)
    public @ResponseBody User login(@RequestParam(value="name") String name,
            @RequestParam(value="password") String password,
            HttpServletRequest req, HttpServletResponse resp) {
        LOG.info("user login : " + name);
        
        User user = userLogic.login(name, password);
        if (user == null) {
            sendError(resp, HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        } else {
            authHelper.onAuthSuccess(req, resp, user, true);
            return user;
        }
    }

    @RequestMapping(value="/api/users", method=RequestMethod.GET)
    public @ResponseBody List<User> get() {
        return userLogic.getAll();
    }
    
    @RequestMapping(value="/api/users/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void delete(@PathVariable(value="id") long userId,
            @RequestBody String body, HttpServletResponse response) {
        userLogic.delete(userId);
    }
    
    @RequestMapping(value="/api/users/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody User update(@PathVariable(value="id") long userId,
            @RequestBody String body, HttpServletResponse response) {
        User user = JsonUtils.fromJson(body, User.class);
        if (user.getId() != userId) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return userLogic.update(user);
    }
    
    @RequestMapping(value="/api/users", method=RequestMethod.POST)
    public @ResponseBody User add(@RequestBody String body, HttpServletResponse response) {
        User user = JsonUtils.fromJson(body, User.class);
        //password is ignored by json mapper
        user.setPassword(JsonUtils.getString(body, "password"));
        
        LOG.info("add user :" + JsonUtils.toJson(user));
        try {
            user = userLogic.add(user);
            sendSuccess(response, HttpServletResponse.SC_CREATED);
            return user;
        } catch (UserNameConflictedException e) {
            sendError(response, HttpServletResponse.SC_CONFLICT);
            return null;
        }
    }
    
    @RequestMapping(value="/api/users/{id:[\\d]+}/password", method=RequestMethod.PUT)
    public void password(@PathVariable(value="id") long userId,
            @RequestParam(value="oldPassword") String oldPassword,
            @RequestParam(value="newPassword") String newPassword,
            HttpServletRequest req, HttpServletResponse resp) {
        User loginUser = getLoginUser(req);
        if (loginUser==null
                || loginUser.getId()!=userId
                || userLogic.login(loginUser.getName(), oldPassword) == null) {
            sendError(resp, HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        userLogic.modifyPassword(userId, newPassword);
    }
}
