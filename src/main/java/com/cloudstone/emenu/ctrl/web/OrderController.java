/**
 * @(#)OrderController.java, Jul 29, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloudstone.emenu.constant.Const.TableStatus;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.Table;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class OrderController extends BaseWebController {
    
    @RequestMapping("/bill")
    public String payBill(HttpServletRequest req, HttpServletResponse resp,
            @RequestParam("tableId") int tableId, ModelMap model) {
        Table table = tableLogic.get(tableId);
        if (table == null) {
            sendError(resp, 404);
            return null;
        }
        Order order = orderLogic.getOrder(table.getOrderId());
        if (order == null) {
            table.setStatus(TableStatus.EMPTY);
            tableLogic.update(null, table);
            sendError(resp, HttpServletResponse.SC_PRECONDITION_FAILED);
            return null;
        }
        model.put("order", orderWraper.wrap(order));
        return sendView("bill", req, resp, model);
    }

}
