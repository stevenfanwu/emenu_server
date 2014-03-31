/**
 * @(#)ErrorController.java, 2013-6-23. 
 *
 */
package com.cloudstone.emenu.ctrl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstone.emenu.exception.DbNotFoundException;
import com.cloudstone.emenu.exception.HttpStatusError;
import com.cloudstone.emenu.util.JsonUtils;
import com.cloudstone.emenu.util.StringUtils;

/**
 * @author xuhongfeng
 */
@Controller
public class ErrorController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);

    public static final String ATTR_STATUS_CODE = "javax.servlet.error.status_code";
    public static final String ATTR_EXCEPTION = "javax.servlet.error.exception";
    public static final String ATTR_REQUEST_URI = "javax.servlet.error.request_uri";

    @RequestMapping("/error")
    public String get(HttpServletRequest req, HttpServletResponse resp,
                      ModelMap model) throws IOException {
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
            apiError(req, resp, model, exception);
            return null;
        } else {
            if (exception != null) {
                model.put("message", exception.getMessage());
                if (exception instanceof HttpStatusError) {
                    HttpStatusError error = (HttpStatusError) exception;
                    resp.setStatus(error.getStatusCode());
                }
            }
            return "error";
        }
    }

    private int getStatusCode(HttpServletRequest req) {
        Throwable exception = (Throwable) req.getAttribute(ATTR_EXCEPTION);
        if (exception != null && exception instanceof HttpStatusError) {
            HttpStatusError e = (HttpStatusError) exception;
            return e.getStatusCode();
        }
        return (Integer) req.getAttribute(ATTR_STATUS_CODE);
    }

    private void apiError(HttpServletRequest req, HttpServletResponse resp,
                          ModelMap model, Throwable error) throws IOException {
        if (error != null && error instanceof HttpStatusError) {
            HttpStatusError e = (HttpStatusError) error;
            resp.setStatus(e.getStatusCode());
            resp.setContentType("application/json; charset=UTF-8");
            model.put("code", e.getStatusCode());
            if (e.getStatusCode() == 500 && StringUtils.isBlank(e.getMessage())) {
                model.put("message", "服务器错误");
            } else {
                model.put("message", e.getMessage());
            }
            resp.getWriter().write(JsonUtils.toJson(model));
            resp.getWriter().flush();
        } else {
            sendError(resp, getStatusCode(req));
        }
    }
}
