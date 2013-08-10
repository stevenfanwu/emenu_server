/**
 * @(#)TestController.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.logic.UserLogic;
import com.cloudstone.emenu.util.JsonUtils;
import com.cloudstone.emenu.util.PrinterUtils;

/**
 * 
 * just for test
 * 
 * @author xuhongfeng
 *
 */
@Controller
public class TestController extends BaseApiController {
    @Autowired
    private UserLogic userLogic;

    @RequestMapping(value="/api/test", method=RequestMethod.GET)
    public @ResponseBody String test(HttpServletRequest req) {
        User user = userLogic.getUser(1);
        return JsonUtils.toJson(user);
    }
    
    @RequestMapping(value="/test", method=RequestMethod.GET)
    public String print() throws Exception {
        String p = PrinterUtils.listPrinters()[0];
        PrinterUtils.print(p, "");
        return "test";
    }
    
}
