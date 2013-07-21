/**
 * @(#)ErrorController.java, 2013-6-23. 
 * 
 */
package com.cloudstone.emenu.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstone.emenu.exception.DbNotFoundException;
import com.cloudstone.emenu.exception.HttpStatusError;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class ErrorController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);
    
    public static final String ATTR_STATUS_CODE = "javax.servlet.error.status_code";
    public static final String ATTR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ATTR_REQUEST_URI = "javax.servlet.error.request_uri";

    @RequestMapping("/error")
    public String get(HttpServletRequest req, HttpServletResponse resp) {
        Throwable exception = (Throwable) req.getAttribute(ATTR_EXCEPTION);
        String requestUrl = (String) req.getAttribute(ATTR_REQUEST_URI);
        
        LOG.error("url :  + " + requestUrl);
        
        if (exception != null) {
            if (exception.getClass() == DbNotFoundException.class) {
                return "/init";
            }
            LOG.error("", exception);
        }
        
        if (requestUrl.contains("/api/")) {
            apiError(req, resp);
            return null;
        }
        //TODO
        
        return "";
    }
    
    private int getStatusCode(HttpServletRequest req) {
        Throwable exception = (Throwable) req.getAttribute(ATTR_EXCEPTION);
        if (exception != null && exception instanceof HttpStatusError) {
            HttpStatusError e = (HttpStatusError) exception;
            return e.getStatusCode();
        }
        return (Integer)req.getAttribute(ATTR_STATUS_CODE);
    }
    
    private void apiError(HttpServletRequest req, HttpServletResponse resp) {
        sendError(resp, getStatusCode(req));
    }
}
