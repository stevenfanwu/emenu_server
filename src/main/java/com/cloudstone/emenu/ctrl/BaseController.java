/**
 * @(#)BaseController.java, Jun 1, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.User;
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
    
    protected void sendSuccess(HttpServletResponse resp, int statusCode) {
        resp.setStatus(statusCode);
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
    
    protected void sendRedirect(String url, HttpServletResponse response) {
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            LOG.warn("redirect to \"" + url + "\" failed", e);
        }
    }
    
    protected File getWebHome() {
        return new File(System.getProperty(Const.PARAM_WEB_HOME_DIR));
    }

    protected void sendFile(HttpServletResponse response, String path) throws IOException {
        File file = new File(getWebHome(), path);
        LOG.info(file.getAbsolutePath());

        InputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(is);

            response.getOutputStream().write(bytes);
        } catch (FileNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
    
    protected User getLoginUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute("loginUser");
    }
}
