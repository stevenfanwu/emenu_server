/**
 * @(#)IdGenerator.java, 2013-7-1. 
 * 
 */
package com.cloudstone.emenu.util;

/**
 * @author xuhongfeng
 *
 */
public class IdGenerator {
    private static long lastId;

    public static synchronized long generateId() {
        long id = System.currentTimeMillis();
        if (id <= lastId) {
            id = lastId+1;
        }
        lastId = id;
        return id;
    }
}
