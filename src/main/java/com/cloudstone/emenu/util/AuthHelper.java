/**
 * @(#)AuthHelper.java, Jun 15, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.UserSession;

/**
 * @author xuhongfeng
 *
 */
public class AuthHelper {
    private static final Logger LOG = LoggerFactory.getLogger(AuthHelper.class);
    private static final String COOKIE_SESSION = "session";
    private static final String COOKIE_USER_ID = "userId";
    
    //TODO
    private static final long SESSION_TIME = 30*UnitUtils.MINUTE;

    public static boolean isLogin(HttpServletRequest req,
            HttpServletResponse resp) {
        boolean ret = checkLogin(req);
        if (!ret) {
            removeCoolies(resp);
        }
        return ret;
    }
    
    public static void createSession(User user, HttpServletRequest req,
            HttpServletResponse resp) {
        UserSession session = new UserSession();
        session.setCreatedTime(System.currentTimeMillis());
        session.setIp(IpUtils.toInt(req.getRemoteAddr()));
        session.setUserId(user.getUserId());
        
        RequestUtils.addCookie(resp, COOKIE_SESSION, SessionUtils.encryptSession(session));
        RequestUtils.addCookie(resp, COOKIE_USER_ID, String.valueOf(user.getUserId()));
    }
    
    private static void removeCoolies(HttpServletResponse resp) {
        RequestUtils.removeCookie(resp, COOKIE_USER_ID);
        RequestUtils.removeCookie(resp, COOKIE_SESSION);
    }
    
    private static boolean checkLogin(HttpServletRequest req) {
        String encryptedSession = RequestUtils.getCookie(req, COOKIE_SESSION);
        String userIdStr = RequestUtils.getCookie(req, COOKIE_USER_ID);
        String ipStr = req.getRemoteAddr();
        
        LOG.info(String.format("checkLogin: userId=%s, encryptedSession=%s, ip=%s",
                userIdStr, encryptedSession, ipStr));
        
        if (StringUtils.isBlank(encryptedSession) || StringUtils.isBlank(userIdStr)) {
            return false;
        }
        UserSession session = SessionUtils.decryptSession(encryptedSession);
        if (session == null) {
            return false;
        }
        
        int ip = IpUtils.toInt(ipStr);
        long userId = NumberUtils.toLong(userIdStr, -1);
        return session.checkSession(userId, ip, SESSION_TIME);
    }
}
