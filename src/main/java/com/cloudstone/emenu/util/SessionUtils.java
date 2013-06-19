/**
 * @(#)SessionUtils.java, Jun 16, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import com.cloudstone.emenu.data.UserSession;

/**
 * @author xuhongfeng
 *
 */
public class SessionUtils {

    public static UserSession decryptSession(String encryptedSession) {
        //TODO
        String sessionStr = encryptedSession;
        
        return UserSession.decode(sessionStr);
    }

    public static String encryptSession(UserSession session) {
        String sessionStr = session.encode();
        //TODO
        String encryptSession = sessionStr;
        
        return encryptSession;
    }
}
