/**
 * @(#)VelocityRender.java, Aug 9, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;

/**
 * @author xuhongfeng
 *
 */
@Component
public class VelocityRender {
    @Autowired
    private VelocityConfigurer velocityConfigurer;
    
    public String renderBill(Bill bill) {
        VelocityEngine engine = velocityConfigurer.getVelocityEngine();
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("bill", bill);
        model.put("dishes", new Dish[0]);
        return VelocityEngineUtils.
                mergeTemplateIntoString(engine, "print_bill.vm", "UTF-8", model);
    }
} 
