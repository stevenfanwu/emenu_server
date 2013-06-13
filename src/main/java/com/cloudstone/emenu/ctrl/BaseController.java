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

import com.cloudstone.emenu.constant.Const;

/**
 * @author xuhongfeng
 *
 */
public class BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);

    protected void sendError(HttpServletResponse response, int statusCode) {
        try {
            response.sendError(statusCode);
        } catch (IOException e) {
            LOG.error("send error failed", e);
        }
    }
    
    protected File getWebHome() {
        return new File(System.getProperty(Const.PARAM_WEB_HOME_DIR));
    }

}
