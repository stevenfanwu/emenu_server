/**
 * @(#)PrinterApiController.java, Aug 13, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.EmenuContext;
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
    public @ResponseBody List<PrintComponent> listComponents(
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return printerLogic.listComponents(context);
    }
    
    @RequestMapping(value="/api/printers/components", method=RequestMethod.POST)
    public @ResponseBody PrintComponent addComponent(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrintComponent data = JsonUtils.fromJson(body, PrintComponent.class);
        return printerLogic.addComponent(context, data);
    }
    
    @RequestMapping(value="/api/printers/components/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody PrintComponent updateComponent(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrintComponent data = JsonUtils.fromJson(body, PrintComponent.class);
        return printerLogic.updateComponent(context, data);
    }
    
    @RequestMapping(value="/api/printers/components/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void deleteComponent(@PathVariable(value="id") int id,
            HttpServletRequest request,
            HttpServletResponse response) {
        EmenuContext context = newContext(request);
        printerLogic.deleteComponent(context, id);
    }

    @RequestMapping(value="/api/printers/templates", method=RequestMethod.GET)
    public @ResponseBody List<PrintTemplate> listTemplates(
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return printerLogic.listTemplate(context);
    }
    
    @RequestMapping(value="/api/printers/templates", method=RequestMethod.POST)
    public @ResponseBody PrintTemplate addTemplate(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrintTemplate data = JsonUtils.fromJson(body, PrintTemplate.class);
        return printerLogic.addTemplate(context, data);
    }
    
    @RequestMapping(value="/api/printers/templates/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody PrintTemplate updateTemplate(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrintTemplate data = JsonUtils.fromJson(body, PrintTemplate.class);
        return printerLogic.updateTemplate(context, data);
    }
    
    @RequestMapping(value="/api/printers/templates/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void deleteTemplate(
            @PathVariable(value="id") int id,
            HttpServletRequest request,
            HttpServletResponse response) {
        EmenuContext context = newContext(request);
        printerLogic.deleteTemplate(context, id);
    }
    

    @RequestMapping(value="/api/printers/configs", method=RequestMethod.GET)
    public @ResponseBody List<PrinterConfig> listConfigs(
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return printerLogic.listPrinterConfig(context);
    }
    
    @RequestMapping(value="/api/printers/configs/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody PrinterConfig updateConfig(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrinterConfig data = JsonUtils.fromJson(body, PrinterConfig.class);
        return printerLogic.updatePrinterConfig(context, data);
    }
}