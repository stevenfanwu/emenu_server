/**
 * @(#)BaseWebController.java, Jun 14, 2013. 
 *
 */
package com.cloudstone.emenu.ctrl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;

import com.cloudstone.emenu.ctrl.BaseController;

/**
 * @author xuhongfeng
 */
public class BaseWebController extends BaseController {

    protected String sendView(String viewName, HttpServletRequest req,
                              HttpServletResponse resp, ModelMap model) {
        model.addAttribute("loginUser", req.getSession().getAttribute("loginUser"));
        return viewName;
    }
}
