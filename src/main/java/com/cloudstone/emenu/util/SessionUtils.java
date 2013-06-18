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
        
        return UserSession.parseSession(sessionStr);
    }
}
