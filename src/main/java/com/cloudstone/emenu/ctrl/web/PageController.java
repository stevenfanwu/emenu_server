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

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Licence;
import com.cloudstone.emenu.logic.ConfigLogic;
import com.cloudstone.emenu.util.LicenceHelper;
import com.cloudstone.emenu.util.LicenceHelper.CheckResult;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class PageController extends BaseWebController {
    @Autowired
    private ConfigLogic configLogic;
    @Autowired
    private LicenceHelper licenceHelper;
    
    @RequestMapping("/upgrading")
    public String upgrading(HttpServletRequest req, HttpServletResponse resp
            , ModelMap model) {
        EmenuContext context = newContext(req);
        if (!configLogic.needUpgradeDb(context)) {
            sendRedirect("/", resp);
            return null;
        }
        return sendView("upgrading", req, resp, model);
    }

    @RequestMapping("/licence")
    public String licence(HttpServletRequest req, HttpServletResponse resp
            , ModelMap model) {
        CheckResult r = licenceHelper.checkLicence();
        
        Licence licence = r.getLicence();
        
        model.put("result", r);
        model.put("licence", licence);
        return sendView("licence", req, resp, model);
    }
}
