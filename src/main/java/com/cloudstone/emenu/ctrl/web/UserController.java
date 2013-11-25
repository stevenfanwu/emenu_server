/**
 * @(#)UserController.java, 2013-6-24. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.util.AuthHelper;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class UserController extends BaseWebController {
    
    @Autowired
    private AuthHelper authHelper;
    
    @RequestMapping("/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp,
            ModelMap model) {
        authHelper.removeCoolies(resp);
        sendRedirect("/login", resp);
        return null;
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest req, HttpServletResponse resp,
            ModelMap model) {
        if (authHelper.isLogin(req, resp)) {
            sendRedirect("/home", resp);
            return null;
        }
        return sendView("login", req, resp, model);
    }
    
    @RequestMapping(value={"/user", "/admin"})
    public String menuManage(HttpServletRequest req, HttpServletResponse resp,
            ModelMap model) {
        return sendView("user", req, resp, model);
    }

    @Override
    protected String sendView(String viewName, HttpServletRequest req,
            HttpServletResponse resp, ModelMap model) {
        model.addAttribute("UserType", new Const.UserType());
        return super.sendView(viewName, req, resp, model);
    }
    
}
