/**
 * @(#)PrinterApiController.java, Aug 13, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.PrintComponent;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class PrinterApiController extends BaseApiController {

    @RequestMapping(value="/api/printers/components", method=RequestMethod.GET)
    public @ResponseBody List<PrintComponent> listComponents() {
        return printerLogic.listComponents();
    }
    
    @RequestMapping(value="/api/printers/components", method=RequestMethod.POST)
    public @ResponseBody PrintComponent addComponent(@RequestBody String body) {
        PrintComponent data = JsonUtils.fromJson(body, PrintComponent.class);
        return printerLogic.addComponent(data);
    }
}
