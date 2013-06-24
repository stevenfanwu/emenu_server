/**
 * @(#)UserController.java, 2013-6-24. 
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
public class UserController extends BaseWebController {
    
    @RequestMapping(value={"/", "/home", "/user", "/operate"})
    public String home(ModelMap model) {
        return "user";
    }

}
