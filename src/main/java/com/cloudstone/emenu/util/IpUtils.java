/**
 * @(#)IPUtils.java, Jun 16, 2013. 
 *
 */
package com.cloudstone.emenu.util;

/**
 * @author xuhongfeng
 */
public class IpUtils {

    public static int toInt(String ip) {
        ip = ip.trim();
        String[] parts = ip.split("\\.");
        if (parts.length != 4) {
            return 0;
        }
        int n = 0;
        n |= (Integer.parseInt(parts[0])) << 24;
        n |= (Integer.parseInt(parts[1])) << 16;
        n |= (Integer.parseInt(parts[2])) << 8;
        n |= Integer.parseInt(parts[3]);
        return n;
    }
}
