/**
 * @(#)MainController.java, Jun 14, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstone.emenu.util.AuthHelper;


/**
 * @author xuhongfeng
 *
 */
@Controller
public class MainController extends BaseWebController {
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse resp) {
        if (AuthHelper.isLogin(req, resp)) {
            sendRedirect("/home", resp);
            return null;
        }
        return "login";
    }
}