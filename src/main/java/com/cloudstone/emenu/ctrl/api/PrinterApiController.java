/**
 * @(#)PrinterApiController.java, Aug 13, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    @RequestMapping(value="/api/printers/components/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody PrintComponent updateComponent(@RequestBody String body) {
        PrintComponent data = JsonUtils.fromJson(body, PrintComponent.class);
        return printerLogic.updateComponent(data);
    }
    
    @RequestMapping(value="/api/printers/components/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void deleteComponent(@PathVariable(value="id") int id,
            HttpServletResponse response) {
        printerLogic.deleteComponent(id);
    }
}