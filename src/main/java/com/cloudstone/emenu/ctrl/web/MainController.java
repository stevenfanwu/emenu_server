/**
 * @(#)MainController.java, Jun 14, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author xuhongfeng
 *
 */
@Controller
public class MainController extends BaseWebController {
    private static final Logger LOG = LoggerFactory.getLogger(MainController.class);
    
    @RequestMapping("/")
    public String home(ModelMap model) {
        return "home";
    }

    @RequestMapping("/login")
    public String login(ModelMap model) {
        return "login";
    }
}