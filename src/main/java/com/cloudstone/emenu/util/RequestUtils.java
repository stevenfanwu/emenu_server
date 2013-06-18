/**
 * @(#)RequestUtils.java, Jun 15, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xuhongfeng
 *
 */
public class RequestUtils {
    
    public static String getCookie(HttpServletRequest req, String name) {
        Cookie[] cookies = req.getCookies();
        if (!CollectionUtils.isEmpty(cookies)) {
            for (Cookie c:cookies) {
                if (c.getName().equals(name)) {
                    return c.getValue();
                }
            }
        }
        return null;
    }

    public static void removeCookie(HttpServletResponse resp, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        resp.addCookie(cookie);
    }
}
