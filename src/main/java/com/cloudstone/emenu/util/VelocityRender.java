/**
 * @(#)VelocityRender.java, Aug 9, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

/**
 * @author xuhongfeng
 *
 */
public class VelocityRender {
    private final Template template;
    private final VelocityContext context;

    public VelocityRender(String templatePath) {
        template = Velocity.getTemplate(templatePath);
        context = new VelocityContext();
    }
    
    public VelocityRender put(String key, Object value) {
        context.put(key, value);
        return this;
    }
    
    public String render() {
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        return sw.toString();
    }
}
