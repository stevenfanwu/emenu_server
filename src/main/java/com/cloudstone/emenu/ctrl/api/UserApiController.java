/**
 * @(#)UserApiController.java, Jun 16, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.util.AuthHelper;

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
    public @ResponseBody User login(@RequestParam(value="userName") String userName,
            @RequestParam(value="password") String password,
            HttpServletRequest req, HttpServletResponse resp) {
        LOG.info("user login : " + userName);
        
        User user = userLogic.login(userName, password);
        if (user == null) {
            sendError(resp, HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        } else {
            authHelper.onAuthSuccess(req, resp, user, true);
            return user;
        }
    }

    @RequestMapping(value="/api/users", method=RequestMethod.GET)
    public @ResponseBody User[] get() {
        return userLogic.getAllUsers().toArray(new User[0]);
    }
}
