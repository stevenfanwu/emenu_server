/**
 * @(#)MenuController.java, 2013-6-25. 
 *
 */
package com.cloudstone.emenu.ctrl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xuhongfeng
 */
@Controller
public class MenuController extends BaseWebController {

    @RequestMapping("/menu")
    public String menuManage(HttpServletRequest req, HttpServletResponse resp,
                             ModelMap model) {
        return sendView("menu", req, resp, model);
    }

    @RequestMapping("/soldout")
    public String menuSoldout(HttpServletRequest req, HttpServletResponse resp,
                              ModelMap model) {
        return sendView("soldout", req, resp, model);
    }

    @RequestMapping("/dish/new")
    public String addDish(HttpServletRequest req, HttpServletResponse resp,
                          ModelMap model) {
        return sendView("editDish", req, resp, model);
    }
}
