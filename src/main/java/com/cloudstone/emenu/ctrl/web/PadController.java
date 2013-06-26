/**
 * @(#)PadController.java, 2013-6-25. 
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
 *
 */
@Controller
public class PadController extends BaseWebController {
    
    @RequestMapping("/pad")
    public String padManage(HttpServletRequest req, HttpServletResponse resp,
            ModelMap model) {
        return sendView("pad", req, resp, model);
    }
    
    @RequestMapping("/monitor")
    public String padMonitor(HttpServletRequest req, HttpServletResponse resp,
            ModelMap model) {
        return sendView("model", req, resp, model);
    }
}
