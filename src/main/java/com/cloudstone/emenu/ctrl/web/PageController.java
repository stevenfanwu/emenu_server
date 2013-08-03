/**
 * @(#)PageController.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstone.emenu.logic.ConfigLogic;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class PageController extends BaseWebController {
    @Autowired
    private ConfigLogic configLogic;
    
    @RequestMapping("/upgrading")
    public String upgrading(HttpServletRequest req, HttpServletResponse resp
            , ModelMap model) {
        if (!configLogic.needUpgradeDb()) {
            sendRedirect("/", resp);
            return null;
        }
        return sendView("upgrading", req, resp, model);
    }

}
