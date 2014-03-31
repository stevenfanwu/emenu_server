/**
 * @(#)MD5Utils.java, Jul 28, 2013. 
 *
 */
package com.cloudstone.emenu.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * @author xuhongfeng
 */
public class MD5Utils {

    private static ThreadLocal<MessageDigest> DIGEST = new ThreadLocal<MessageDigest>() {
        protected MessageDigest initialValue() {
            try {
                return MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public static String md5(byte[] bytes) {
        MessageDigest digest = DIGEST.get();
        digest.reset();
        digest.update(bytes);
        byte[] output = digest.digest();
        return Base64.encodeBase64URLSafeString(output);
    }
}
