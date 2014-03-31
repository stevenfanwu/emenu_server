/**
 * @(#)MemberController.java, 2013-6-25. 
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
public class MemberController extends BaseWebController {

    @RequestMapping("/member")
    public String member(HttpServletRequest req, HttpServletResponse resp,
                         ModelMap model) {
        return sendView("member", req, resp, model);
    }

    @RequestMapping("/superadmin")
    public String superAdmin(HttpServletRequest req, HttpServletResponse resp, ModelMap model) {
        return sendView("superadmin", req, resp, model);
    }
}
