/**
 * @(#)OrderApiController.java, Jul 30, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.PayType;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.exception.PreconditionFailedException;
import com.cloudstone.emenu.util.JsonUtils;
import com.cloudstone.emenu.util.PrinterUtils;
import com.cloudstone.emenu.util.StringUtils;
import com.cloudstone.emenu.util.VelocityRender;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class OrderApiController extends BaseApiController {
    
    @Autowired
    private VelocityRender velocityRender;
    
    @RequestMapping(value="/api/orders", method=RequestMethod.GET)
    public @ResponseBody OrderVO getOrder(int orderId, HttpServletResponse response) {
        Order order  =orderLogic.getOrder(orderId);
        if (order == null) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND);
        }
        return orderWraper.wrap(order);
    }
    
    @RequestMapping(value="/api/pay-types", method=RequestMethod.GET)
    public @ResponseBody List<PayType> listPayTypes() {
        return orderLogic.listPayTypes();
    }
    
    @RequestMapping(value="/api/bills", method=RequestMethod.POST)
    public @ResponseBody Bill payBill(@RequestBody String body, HttpServletResponse response) {
        Bill bill = JsonUtils.fromJson(body, Bill.class);
        if (!StringUtils.isBlank(bill.getPrinter())) {
            try {
                PrinterUtils.print(bill.getPrinter(), velocityRender.renderBill(bill));
            } catch (Exception e) {
                throw new PreconditionFailedException("打印失败", e);
            }
        }
        return orderLogic.payBill(bill);
    }
}
