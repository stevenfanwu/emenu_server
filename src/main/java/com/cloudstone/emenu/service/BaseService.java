/**
 * @(#)BaseService.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.storage.db.IChapterDb;
import com.cloudstone.emenu.storage.db.IDishDb;
import com.cloudstone.emenu.storage.db.IDishPageDb;
import com.cloudstone.emenu.storage.db.IMenuDb;
import com.cloudstone.emenu.storage.db.IMenuPageDb;
import com.cloudstone.emenu.storage.db.ITableDb;
import com.cloudstone.emenu.storage.db.IUserDb;

/**
 * @author xuhongfeng
 *
 */
public class BaseService {
    
    @Autowired
    protected IUserDb userDb;
    @Autowired
    protected ITableDb tableDb;
    @Autowired
    protected IDishDb dishDb;
    @Autowired
    protected IMenuDb menuDb;
    @Autowired
    protected IChapterDb chapterDb;
    @Autowired
    protected IMenuPageDb menuPageDb;
    @Autowired
    protected IDishPageDb dishPageDb;

    protected final ExecutorService threadPool = Executors.newCachedThreadPool();
    
    protected void runTask(Runnable task) {
        threadPool.submit(task);
    }
}
