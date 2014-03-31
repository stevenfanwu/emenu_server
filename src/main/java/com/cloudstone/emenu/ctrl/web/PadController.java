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
 */
@Controller
public class PadController extends BaseWebController {

    @RequestMapping("/device")
    public String deviceManage(HttpServletRequest req, HttpServletResponse resp,
                               ModelMap model) {
        return sendView("device", req, resp, model);
    }

    @RequestMapping("/monitor")
    public String padMonitor(HttpServletRequest req, HttpServletResponse resp,
                             ModelMap model) {
        return sendView("monitor", req, resp, model);
    }
}
