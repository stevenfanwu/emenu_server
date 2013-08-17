/**
 * @(#)VelocityRender.java, Aug 9, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.OrderDishVO;
import com.cloudstone.emenu.web.velocitytool.Utils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class VelocityRender {
    @Autowired
    private VelocityConfigurer velocityConfigurer;
    @Autowired
    private Utils utils;
    
    public String renderBill(Bill bill, User user, List<OrderDishVO> dishes, String template) {
        VelocityEngine engine = velocityConfigurer.getVelocityEngine();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("bill", bill);
        model.put("dishes", dishes);
        model.put("order", bill.getOrder());
        model.put("table", bill.getOrder().getTable());
        model.put("time", utils.formatDate(bill.getCreatedTime()));
        String userName = user.getRealName();
        if (userName == null) {
            userName = "";
        }
        model.put("userName", userName);
        return VelocityEngineUtils.
                mergeTemplateIntoString(engine, "print_bill.vm", "UTF-8", model);
    }
} 
