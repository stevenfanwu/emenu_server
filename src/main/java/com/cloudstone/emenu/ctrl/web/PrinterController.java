/**
 * @(#)PrinterController.java, 2013-6-25. 
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
public class PrinterController extends BaseWebController {

    @RequestMapping("/printer")
    public String get(HttpServletRequest req, HttpServletResponse resp,
                      ModelMap model) {
        return sendView("printer", req, resp, model);
    }

}
