/**
 * @(#)RequestUtils.java, Jun 15, 2013. 
 *
 */
package com.cloudstone.emenu.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuhongfeng
 */
public class RequestUtils {
    private static final Logger LOG = LoggerFactory.getLogger(RequestUtils.class);

    public static String getCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (!CollectionUtils.isEmpty(cookies)) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    LOG.info("get Cookie : " + c);
                    return c.getValue();
                }
            }
        }
        return null;
    }

    public static void addCookie(HttpServletResponse resp, String name,
                                 String value) {
        Cookie cookie = new Cookie(name, value);
        LOG.info("add Cookie : " + name + " : " + value);
        //TODO expire time
        cookie.setMaxAge(-1);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }

    public static void removeCookie(HttpServletResponse resp, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }

    public static String getUserAgent(HttpServletRequest req) {
        return req.getHeader("User-Agent");
    }
}
