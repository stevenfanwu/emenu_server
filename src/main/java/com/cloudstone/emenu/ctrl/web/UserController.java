/**
 * @(#)UserController.java, 2013-6-24. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstone.emenu.constant.Const;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class UserController extends BaseWebController {
    
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
