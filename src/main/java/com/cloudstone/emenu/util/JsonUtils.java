/**
 * @(#)JsonUtil.java, 2013-7-1. 
 *
 */
package com.cloudstone.emenu.util;

import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuhongfeng
 */
public class JsonUtils {
    private static final Logger LOG = LoggerFactory.getLogger(JsonUtils.class);

    private static final IgnorableObjectMapper MAPPER = new IgnorableObjectMapper();
    private static final JsonFactory FACTORY = new JsonFactory();

    public static String toJson(Object object) {
        try {
            return MAPPER.writeValueAsString(object);
        } catch (Exception e) {
            LOG.error("to json failed", e);
            return null;
        }
    }

    public static <T> T fromJson(JsonNode node, Class<T> clazz) {
        try {
            return MAPPER.readValue(node, clazz);
        } catch (Exception e) {
            LOG.error("from json failed", e);
            return null;
        }
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return MAPPER.readValue(json, clazz);
        } catch (Exception e) {
            LOG.error("from json failed", e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static String getString(String json, String key) {
        try {
            Map<String, Object> map = MAPPER.readValue(json, Map.class);
            return (String) map.get(key);
        } catch (Exception e) {
            LOG.error("from json failed", e);
            return null;
        }
    }
}
