/**
 * @(#)UserApiController.java, Jun 16, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    //TODO encrypt password
    @RequestMapping(value="/api/login", method=RequestMethod.POST)
    public @ResponseBody User login(@RequestParam(value="userName") String userName,
            @RequestParam(value="password") String password,
            HttpServletRequest req, HttpServletResponse resp) {
        LOG.info("user login : " + userName);
        
        //TODO
        User user = new User();
        user.setName(userName);
        user.setUserId(user.getName().hashCode());
        
        AuthHelper.createSession(user, req, resp);
        
        return user;
    }

}
