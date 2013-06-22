/**
 * @(#)BaseController.java, Jun 1, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.logic.UserLogic;

/**
 * @author xuhongfeng
 *
 */
public class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
    
    @Autowired
    protected UserLogic userLogic;

    protected void sendError(HttpServletResponse response, int statusCode) {
        try {
            response.sendError(statusCode);
        } catch (IOException e) {
            LOG.error("send error failed", e);
        }
    }
    
    /**
     *  send a error message with status code 412
     *  frontend may alert the message
     *  
     * @param resp
     * @param msg
     */
    protected void sendAlert(HttpServletResponse resp, String msg) {
    }
    
    protected File getWebHome() {
        return new File(System.getProperty(Const.PARAM_WEB_HOME_DIR));
    }

}
