/**
 * @(#)MainController.java, Jun 14, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author xuhongfeng
 *
 */
@Controller
public class MainController extends BaseWebController {

    @RequestMapping("/")
    public String home(ModelMap model) {
        return "home";
    }

}
