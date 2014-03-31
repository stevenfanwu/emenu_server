/**
 * @(#)NumberUtils.java, Jun 16, 2013. 
 *
 */
package com.cloudstone.emenu.util;

/**
 * @author xuhongfeng
 */
public class NumberUtils {

    public static long toLong(String s, long failedValue) {
        try {
            return Long.parseLong(s);
        } catch (Throwable e) {
            return failedValue;
        }
    }

    public static int toInt(String s, int failedValue) {
        try {
            return Integer.parseInt(s);
        } catch (Throwable e) {
            return failedValue;
        }
    }
}
