/**
 * @(#)VelocityRender.java, Aug 9, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.OrderDishVO;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.web.velocitytool.Utils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class VelocityRender {
    @Autowired
    private Utils utils;
    
    public String renderBill(Bill bill, User user, List<OrderDishVO> dishes, String template) {
        
        VelocityContext context = new VelocityContext();
        context.put("bill", bill);
        context.put("dishes", dishes);
        context.put("order", bill.getOrder());
        context.put("table", bill.getOrder().getTable());
        context.put("time", utils.formatDate(bill.getCreatedTime()));
        String userName = user.getRealName();
        if (userName == null) {
            userName = "";
        }
        context.put("userName", userName);
        return render(context, template);
    }
    
    public String renderOrder(OrderVO order, User user, List<OrderDishVO> dishes, String template) {
        VelocityContext context = new VelocityContext();
        context.put("dishes", dishes);
        context.put("order", order);
        context.put("table", order.getTable());
        context.put("time", utils.formatDate(order.getUpdateTime()));
        String userName = user.getRealName();
        if (userName == null) {
            userName = "";
        }
        context.put("userName", userName);
        return render(context, template);
    }
    
    private String render(VelocityContext context, String template) {
        RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();            
        StringReader reader = new StringReader(template);
        SimpleNode node;
        try {
            node = runtimeServices.parse(reader, "Template name");
        } catch (ParseException e) {
            throw new ServerError(e);
        }
        Template t = new Template();
        t.setRuntimeServices(runtimeServices);
        t.setData(node);
        t.initDocument();
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        writer.write("\n\n\n\n\n");
        return writer.toString();
    }
} 
