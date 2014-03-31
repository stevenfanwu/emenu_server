/**
 * @(#)PrinterApiController.java, Aug 13, 2013. 
 *
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.DishRecord;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.PrintComponent;
import com.cloudstone.emenu.data.PrintTemplate;
import com.cloudstone.emenu.data.PrinterConfig;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.exception.NotFoundException;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.logic.RecordLogic;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 */
@Controller
public class PrinterApiController extends BaseApiController {

    @RequestMapping(value = "/api/printers/components", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PrintComponent> listComponents(
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return printerLogic.listComponents(context);
    }

    @RequestMapping(value = "/api/printers/components", method = RequestMethod.POST)
    public
    @ResponseBody
    PrintComponent addComponent(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrintComponent data = JsonUtils.fromJson(body, PrintComponent.class);
        return printerLogic.addComponent(context, data);
    }

    @RequestMapping(value = "/api/printers/components/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    PrintComponent updateComponent(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrintComponent data = JsonUtils.fromJson(body, PrintComponent.class);
        return printerLogic.updateComponent(context, data);
    }

    @RequestMapping(value = "/api/printers/components/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteComponent(@PathVariable(value = "id") int id,
                                HttpServletRequest request,
                                HttpServletResponse response) {
        EmenuContext context = newContext(request);
        printerLogic.deleteComponent(context, id);
    }

    @RequestMapping(value = "/api/printers/templates", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PrintTemplate> listTemplates(
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return printerLogic.listTemplate(context);
    }

    @RequestMapping(value = "/api/printers/templates", method = RequestMethod.POST)
    public
    @ResponseBody
    PrintTemplate addTemplate(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrintTemplate data = JsonUtils.fromJson(body, PrintTemplate.class);
        return printerLogic.addTemplate(context, data);
    }

    @RequestMapping(value = "/api/printers/templates/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    PrintTemplate updateTemplate(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrintTemplate data = JsonUtils.fromJson(body, PrintTemplate.class);
        return printerLogic.updateTemplate(context, data);
    }

    @RequestMapping(value = "/api/printers/templates/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deleteTemplate(
            @PathVariable(value = "id") int id,
            HttpServletRequest request,
            HttpServletResponse response) {
        EmenuContext context = newContext(request);
        printerLogic.deleteTemplate(context, id);
    }


    @RequestMapping(value = "/api/printers/configs", method = RequestMethod.GET)
    public
    @ResponseBody
    List<PrinterConfig> listConfigs(
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return printerLogic.listPrinterConfig(context);
    }

    @RequestMapping(value = "/api/printers/configs/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    PrinterConfig updateConfig(
            @RequestBody String body,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        PrinterConfig data = JsonUtils.fromJson(body, PrinterConfig.class);
        return printerLogic.updatePrinterConfig(context, data);
    }

    @RequestMapping(value = "/api/printers/print", method = RequestMethod.POST)
    public void print(@RequestParam("orderId") int orderId,
                      @RequestParam("printerId") int printerId,
                      @RequestParam("templateId") int templateId,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        EmenuContext context = newContext(request);
        Order order = orderLogic.getOrder(context, orderId);
        if (order == null) {
            throw new NotFoundException("该订单不存在");
        }
        PrinterConfig config = printerLogic.getPrinterConfig(context, printerId);
        if (config == null) {
            throw new NotFoundException("该打印机不存在");
        }
        User user = getLoginUser(request);
        if (order.getStatus() == 1) {
            Bill bill = orderLogic.getBillByOrderId(context, orderId);
            try {
                printerLogic.printBill(context, bill, user, config.getName(), templateId);
            } catch (Exception e) {
                throw new ServerError("打印失败");
            }
        } else {
            try {
                printerLogic.printOrder(context, orderWraper.wrap(context, order),
                        user, config.getName(), templateId);
            } catch (Exception e) {
                throw new ServerError("打印失败");
            }
        }
    }
}