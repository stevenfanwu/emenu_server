/**
 * @(#)SqlUtils.java, 2013-7-7. 
 * 
 */
package com.cloudstone.emenu.storage.db.util;

import com.cloudstone.emenu.util.StringUtils;

/**
 * @author xuhongfeng
 *
 */
public class SqlUtils {

    public static boolean intToBoolean(int flag) {
        return flag == 0 ? false: true;
    }
    
    public static int booleanToInt(boolean flag) {
        return flag? 1 : 0;
    }
    
    public static long[] strToIds(String str) {
        if (StringUtils.isBlank(str)) {
            return new long[0];
        } else {
            String[] ss = str.split(",");
            long[] r = new long[ss.length];
            for (int i=0; i<r.length; i++) {
                r[i] = Long.valueOf(ss[i]);
            }
            return r;
        }
    }
    
    public static String idsToStr(long[] ids) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (long id:ids) {
            if (!first) {
                sb.append(",");
            }
            first = false;
            sb.append(id);
        }
        return sb.toString();
    }
}
