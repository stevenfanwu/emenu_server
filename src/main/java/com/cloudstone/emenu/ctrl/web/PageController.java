/**
 * @(#)PageController.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.ServerConfig;
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
    private static final Logger LOG = LoggerFactory.getLogger(PageController.class);
 
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
        try {
            model.put("serial", licenceHelper.getSerial());
        } catch (Exception e) {
            LOG.error("", e);
        }
        model.put("serverVersion", ServerConfig.VERSION);
        model.put("dbVersion", ServerConfig.DB_VERSION);
        return sendView("licence", req, resp, model);
    }

    @RequestMapping(value="/licence", method=RequestMethod.POST)
    public void uploadLisence(HttpServletRequest request, HttpServletResponse response
            , @RequestParam("lisence") MultipartFile lisenceFile) {
        //TODO check file extension, file size
        try {
            licenceHelper.saveLisence(lisenceFile.getInputStream());
        } catch (IOException e) {
            //TODO show error
            LOG.error("", e);
        }
        sendRedirect("licence", response);
    }

    @RequestMapping(value="/404", method=RequestMethod.GET)
    public String notFound(HttpServletRequest req, HttpServletResponse resp
            , ModelMap model) {
        return sendView("404", req, resp, model);
    }

    @RequestMapping(value="/management", method=RequestMethod.GET)
    public String management(HttpServletRequest req, HttpServletResponse resp
            , ModelMap model) {
        return sendView("management", req, resp, model);
    }
    
    @RequestMapping(value="/about", method=RequestMethod.GET)
    public String about(HttpServletRequest req, HttpServletResponse resp
            , ModelMap model) {
        return sendView("about", req, resp, model);
    }
    
    @RequestMapping(value="/tablet", method=RequestMethod.GET)
    public String tablet(HttpServletRequest req, HttpServletResponse resp
            , ModelMap model) {
        return sendView("tablet", req, resp, model);
    }
    
    @RequestMapping(value="/signup", method=RequestMethod.GET)
    public String signup(HttpServletRequest req, HttpServletResponse resp
            , ModelMap model) {
        return sendView("signup", req, resp, model);
    }
    
    @RequestMapping(value={"/", "index"}, method=RequestMethod.GET)
    public String index(HttpServletRequest req, HttpServletResponse resp
            , ModelMap model) {
        return sendView("index", req, resp, model);
    }
}
