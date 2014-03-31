/**
 * @(#)TableController.java, 2013-6-25. 
 *
 */
package com.cloudstone.emenu.ctrl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 */
@Controller
public class TableController extends BaseWebController {

    @RequestMapping("/table")
    public String tableManage(HttpServletRequest req, HttpServletResponse resp,
                              ModelMap model) {
        return sendView("table", req, resp, model);
    }

    @RequestMapping(value = {"/home", "operate", "/status"})
    public String tableStatus(HttpServletRequest req, HttpServletResponse resp,
                              ModelMap model) {
        User loginUser = (User) req.getSession().getAttribute("loginUser");
        if (loginUser.getType() == Const.UserType.SUPER_USER) {
            sendRedirect("/superadmin", resp);
            return null;
        } else {
            return sendView("status", req, resp, model);
        }
    }
}
