/**
 * @(#)BaseLogic.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.service.IDishService;
import com.cloudstone.emenu.service.IMenuService;
import com.cloudstone.emenu.service.ITableService;
import com.cloudstone.emenu.service.IUserService;
import com.cloudstone.emenu.storage.db.IChapterService;

/**
 * @author xuhongfeng
 *
 */
public class BaseLogic {
    
    @Autowired
    protected IUserService userService;
    @Autowired
    protected ITableService tableService;
    @Autowired
    protected IDishService dishService;
    @Autowired
    protected IMenuService menuService;
    @Autowired
    protected IChapterService chapterService;
    
    protected final ExecutorService threadPool = Executors.newCachedThreadPool();
    
    protected void runTask(Runnable task) {
        threadPool.submit(task);
    }
}
