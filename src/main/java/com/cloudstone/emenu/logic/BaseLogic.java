/**
 * @(#)BaseLogic.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.storage.db.util.DbTransaction;
import com.cloudstone.emenu.storage.db.util.SqliteDataSource;

/**
 * @author xuhongfeng
 *
 */
public class BaseLogic {

    @Autowired
    private SqliteDataSource dataSource;

    protected final ExecutorService threadPool = Executors.newCachedThreadPool();

    protected void runTask(Runnable task) {
        threadPool.submit(task);
    }

    protected DbTransaction openTrans() {
        return dataSource.openTrans();
    }
}
