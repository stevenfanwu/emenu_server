/**
 * @(#)UnitUtils.java, Jun 16, 2013. 
 *
 */
package com.cloudstone.emenu.util;

/**
 * @author xuhongfeng
 */
public class UnitUtils {

    public static final long SECOND = 1000;

    public static final long MINUTE = 60 * SECOND;

    public static final long HOUR = 60 * MINUTE;

    public static final long DAY = 24 * HOUR;

    public static final long WEEK = 7 * DAY;

    public static final long BYTE = 1;

    public static final long KBYTE = 1024 * BYTE;

    public static final long MBYTE = 1024 * KBYTE;

    public static final long GBYTE = 1024 * MBYTE;

    public static String getSize(long size) {
        if (size < KBYTE) {
            return size + "B";
        } else if (size < MBYTE) {
            return size / KBYTE + "K";
        } else if (size < GBYTE) {
            return size / MBYTE + "M";
        }
        return size / GBYTE + "GB";
    }

    public static String getSize(long size, int percentage) {
        return getSize(size)
                + (percentage >= 100 ? "" : "  --" + percentage + "%");
    }

    public static double getSizeInMegaByte(long size) {
        return size * 1.0 / MBYTE;
    }

    public static String getSizeInKB(long size) {
        if (size < KBYTE) {
            return size + "B";
        }
        return size / KBYTE + "K";
    }

    public static int getSeconds(long time) {
        return Math.round(((float) time / SECOND));
    }

    public static int getHours(long time) {
        return Math.round(((float) time / HOUR));
    }

    public static int getMinutes(long time) {
        return Math.round(((float) time / MINUTE));
    }

    public static String getDuration(long timeMs) {
        if (timeMs / UnitUtils.DAY > 0) {
            return "days ... !";
        }
        int h = (int) (timeMs / UnitUtils.HOUR);
        int m = (int) ((timeMs % UnitUtils.HOUR) / UnitUtils.MINUTE);
        int s = (int) ((timeMs % UnitUtils.MINUTE) / UnitUtils.SECOND);
        if (h == 0) {
            return toStr(m) + ":" + toStr(s);
        } else {
            return toStr(h) + ":" + toStr(m) + ":" + toStr(s);
        }
    }

    private static String toStr(int x) {
        if (x / 10 == 0) {
            return "0" + x;
        } else {
            return Integer.toString(x);
        }
    }

    public static long getDayByMillis(long millis) {
        return (millis + 8 * HOUR) / (long) DAY;
    }

    /**
     * @return timestamp of 0'clock
     */
    public static long getDayStart(long time) {
        long day = getDayByMillis(time);
        return day * DAY - 8 * HOUR;
    }
}
