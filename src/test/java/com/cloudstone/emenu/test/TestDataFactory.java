/**
 * @(#)TestDataFactory.java, Aug 2, 2013. 
 * 
 */
package com.cloudstone.emenu.test;

import java.util.Random;

import org.apache.commons.codec.binary.Base64;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
public class TestDataFactory {
    private static final Random random = new Random();
    
    public static int genInt(int max) {
        return random.nextInt(max+1);
    }
    
    public static int genInt() {
        return genInt(100);
    }
    
    public static double genDouble(double max) {
        return max*random.nextDouble();
    }
    
    public static double genDouble() {
        return genDouble(10);
    }
    
    public static boolean genBoolean() {
        return random.nextBoolean();
    }
    
    public static String genString() {
        return genString(5);
    }
    
    public static String genString(int len) {
        return Base64.encodeBase64String(genBytes(len)).substring(0, len);
    }
    
    public static byte[] genBytes(int len){
        byte[] bytes = new byte[len];
        random.nextBytes(bytes);
        return bytes;
    }
    
    public static User genUser() {
        User user = new User();
        
        user.setComment(genString());
        user.setId(genInt());
        user.setName(genString());
        user.setPassword(genString());
        user.setRealName(genString());
        user.setType(genInt(Const.UserType.USER));
        
        return user;
    }
}
