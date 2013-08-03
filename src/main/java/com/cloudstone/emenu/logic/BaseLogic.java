/**
 * @(#)BaseLogic.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author xuhongfeng
 *
 */
public class BaseLogic {
    
    protected final ExecutorService threadPool = Executors.newCachedThreadPool();
    
    protected void runTask(Runnable task) {
        threadPool.submit(task);
    }
}
