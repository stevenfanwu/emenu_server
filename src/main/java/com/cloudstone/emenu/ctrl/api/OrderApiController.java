/**
 * @(#)OrderApiController.java, Jul 30, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.constant.Const.TableStatus;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.PayType;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class OrderApiController extends BaseApiController {
    
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
        if (orderLogic.getBillByOrderId(bill.getOrderId()) != null) {
            sendError(response, HttpServletResponse.SC_CONFLICT);
            return null;
        }
        Order order = orderLogic.getOrder(bill.getOrderId());
        if (order == null) {
            sendError(response, HttpServletResponse.SC_PRECONDITION_FAILED);
            return null;
        }
        Table table = tableLogic.get(order.getTableId());
        if (table == null) {
            sendError(response, HttpServletResponse.SC_PRECONDITION_FAILED);
            return null;
        }
        table.setStatus(TableStatus.EMPTY);
        tableLogic.update(table);
        
        return orderLogic.addBill(bill);
    }

}
