/**
 * @(#)IgnorableJsonMapper.java, 2013-7-1. 
 *
 */
package com.cloudstone.emenu.util;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

/**
 * @author xuhongfeng
 */
public class IgnorableObjectMapper extends ObjectMapper {

    public IgnorableObjectMapper() {
        super();
        setDeserializationConfig(getDeserializationConfig().without(Feature.FAIL_ON_UNKNOWN_PROPERTIES));
        this.disable(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);
    }
}
