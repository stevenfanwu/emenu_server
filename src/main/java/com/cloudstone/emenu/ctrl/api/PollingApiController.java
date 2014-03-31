/**
 * @(#)PollingApiController.java, Aug 28, 2013. 
 *
 */
package com.cloudstone.emenu.ctrl.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.misc.PollingManager;
import com.cloudstone.emenu.data.misc.PollingManager.PollingMessage;

/**
 * @author xuhongfeng
 */
@Controller
public class PollingApiController extends BaseApiController {
    @Autowired
    private PollingManager pollingManager;

    @RequestMapping(value = "/api/polling", method = RequestMethod.GET)
    public
    @ResponseBody
    PollingMessage polling() {
        return pollingManager.query();
    }
}
