/**
 * @(#)Utils.java, 2013-6-25. 
 *
 */
package com.cloudstone.emenu.web.velocitytool;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xuhongfeng
 */
@Component
public class Utils {
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    ThreadLocal<SimpleDateFormat> DATE_FORMATTER = new ThreadLocal<SimpleDateFormat>() {

        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }

        ;
    };

    public String formatDate(long timestamp) {
        return DATE_FORMATTER.get().format(new Date(timestamp));
    }

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