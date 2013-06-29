/**
 * @(#)Utils.java, 2013-6-25. 
 * 
 */
package com.cloudstone.emenu.web.velocitytool;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuhongfeng
 *
 */
public class Utils {
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            LOG.warn("", e);
            return null;
        }
    }
    
    public String buildPageConfig(String pageName) {
        PageConfig config = new PageConfig();
        config.setName(pageName);
        return toJson(config);
    }
    
    public static class PageConfig {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}