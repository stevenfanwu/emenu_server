/**
 * @(#)StatisticsController.java, 2013-6-25. 
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
public class StatisticsController extends BaseWebController {

    @RequestMapping("/stat")
    public String statistics(HttpServletRequest req, HttpServletResponse resp,
                             ModelMap model) {
        return sendView("statistics", req, resp, model);
    }

    @RequestMapping("/history")
    public String history(HttpServletRequest req, HttpServletResponse resp,
                          ModelMap model) {
        return sendView("history", req, resp, model);
    }

}
