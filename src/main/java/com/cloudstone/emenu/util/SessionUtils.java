/**
 * @(#)SessionUtils.java, Jun 16, 2013. 
 *
 */
package com.cloudstone.emenu.util;

import org.apache.commons.codec.binary.Base64;

import com.cloudstone.emenu.data.UserSession;

/**
 * @author xuhongfeng
 */
public class SessionUtils {

    public static UserSession decryptSession(String encryptedSession) {
        //TODO
        String sessionStr = new String(Base64.decodeBase64(encryptedSession));

        return UserSession.decode(sessionStr);
    }

    public static String encryptSession(UserSession session) {
        String sessionStr = session.encode();
        //TODO
        String encryptSession = Base64.encodeBase64URLSafeString(sessionStr.getBytes());

        return encryptSession;
    }
}
