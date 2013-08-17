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
import com.cloudstone.emenu.data.PrintTemplate;
import com.cloudstone.emenu.data.PrinterConfig;
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

    @RequestMapping(value="/api/printers/templates", method=RequestMethod.GET)
    public @ResponseBody List<PrintTemplate> listTemplates() {
        return printerLogic.listTemplate();
    }
    
    @RequestMapping(value="/api/printers/templates", method=RequestMethod.POST)
    public @ResponseBody PrintTemplate addTemplate(@RequestBody String body) {
        PrintTemplate data = JsonUtils.fromJson(body, PrintTemplate.class);
        return printerLogic.addTemplate(data);
    }
    
    @RequestMapping(value="/api/printers/templates/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody PrintTemplate updateTemplate(@RequestBody String body) {
        PrintTemplate data = JsonUtils.fromJson(body, PrintTemplate.class);
        return printerLogic.updateTemplate(data);
    }
    
    @RequestMapping(value="/api/printers/templates/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void deleteTemplate(@PathVariable(value="id") int id,
            HttpServletResponse response) {
        printerLogic.deleteTemplate(id);
    }
    

    @RequestMapping(value="/api/printers/configs", method=RequestMethod.GET)
    public @ResponseBody List<PrinterConfig> listConfigs() {
        return printerLogic.listPrinterConfig();
    }
    
    @RequestMapping(value="/api/printers/configs/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody PrinterConfig updateConfig(@RequestBody String body) {
        PrinterConfig data = JsonUtils.fromJson(body, PrinterConfig.class);
        return printerLogic.updatePrinterConfig(data);
    }
}