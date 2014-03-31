/**
 * @(#)AuthHelper.java, Jun 15, 2013. 
 *
 */
package com.cloudstone.emenu.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.UserSession;
import com.cloudstone.emenu.logic.UserLogic;

/**
 * @author xuhongfeng
 */
@Component
public class AuthHelper {
    private static final Logger LOG = LoggerFactory.getLogger(AuthHelper.class);
    private static final String COOKIE_SESSION = "SESS";
    private static final String COOKIE_USER_ID = "UID";

    private static final String SESSION_ACTIVE_TIME = "active_time";

    @Autowired
    private UserLogic userLogic;


    public boolean isLogin(HttpServletRequest req,
                           HttpServletResponse resp) {
        boolean ret = checkLogin(req, resp);
        if (!ret) {
            removeCoolies(resp);
        }
        return ret;
    }

    public void removeCoolies(HttpServletResponse resp) {
        RequestUtils.removeCookie(resp, COOKIE_USER_ID);
        RequestUtils.removeCookie(resp, COOKIE_SESSION);
    }

    private boolean checkLogin(HttpServletRequest req, HttpServletResponse resp) {
        String encryptedSession = RequestUtils.getCookie(req, COOKIE_SESSION);
        String userIdStr = RequestUtils.getCookie(req, COOKIE_USER_ID);
        String ipStr = req.getRemoteAddr();//TODO wrong ip

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
        int userId = NumberUtils.toInt(userIdStr, -1);
        boolean checkResult = session.checkSession(userId, ip);
        if (checkResult) {
            //check active time
            long now = System.currentTimeMillis();
            Long activeTime = (Long) req.getSession().getAttribute(SESSION_ACTIVE_TIME);
            if (activeTime == null || (now - activeTime) > getSpanTime(req)) {
                return false;
            }
            req.getSession().setAttribute(SESSION_ACTIVE_TIME, now);

            User loginUser = userLogic.getUser(new EmenuContext(), userId);
            onAuthSuccess(req, resp, loginUser, false);
        }
        return checkResult;
    }

    public void onAuthSuccess(HttpServletRequest req, HttpServletResponse resp,
                              User loginUser, boolean needCreateSession) {
        /* put attrs in servlet session */
        HttpSession session = req.getSession();
        session.setAttribute("loginUser", loginUser);

        if (needCreateSession) {
            createSession(loginUser.getId(), req, resp);
        }
    }

    private void createSession(int userId, HttpServletRequest req,
                               HttpServletResponse resp) {
        UserSession session = new UserSession();
        session.setCreatedTime(System.currentTimeMillis());
        session.setIp(IpUtils.toInt(req.getRemoteAddr()));
        session.setUserId(userId);

        RequestUtils.addCookie(resp, COOKIE_SESSION, SessionUtils.encryptSession(session));
        RequestUtils.addCookie(resp, COOKIE_USER_ID, String.valueOf(userId));

        req.getSession().setAttribute(SESSION_ACTIVE_TIME, System.currentTimeMillis());
    }

    private static final long SPAN_WEB = 30 * UnitUtils.MINUTE;
    private static final long SPAN_CLOUD_HAND = UnitUtils.DAY;

    private long getSpanTime(HttpServletRequest req) {
        String userAgent = RequestUtils.getUserAgent(req);
        if (Const.UserAgent.CLOUD_HAND.equals(userAgent)) {
            return SPAN_CLOUD_HAND;
        }
        return SPAN_WEB;
    }
}
