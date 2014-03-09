/**
 * @(#)EmenuContext.java, Aug 17, 2013. 
 * 
 */
package com.cloudstone.emenu;

import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.util.DbTransaction;
import com.cloudstone.emenu.storage.db.util.SqliteDataSource;

/**
 * @author xuhongfeng
 *
 */
public class EmenuContext {
    private int loginUserId;
    // id of the restaurant the logged in user is associated with

    private int restaurantId;
    private DbTransaction transaction;
    
    public int getLoginUserId() {
        return loginUserId;
    }
    public void setLoginUserId(int loginUserId) {
        this.loginUserId = loginUserId;
    }
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }
    public DbTransaction getTransaction() {
        return transaction;
    }
    public void setTransaction(DbTransaction transaction) {
        this.transaction = transaction;
    }
    
    public void beginTransaction(SqliteDataSource dataSource) {
        if (transaction != null) {
            throw new RuntimeException();
        }
        transaction = dataSource.openTrans();
        try {
            transaction.begin();
        } catch (Throwable e) {
            dataSource.notifyTransactionDone();
            throw new ServerError(e);
        }
    }
    
    public void commitTransaction() {
        transaction.commit();
    }
    
    public void closeTransaction(SqliteDataSource dataSource) {
        try {
            transaction.close();
            transaction = null;
        } finally {
            dataSource.notifyTransactionDone();
        }
    }
}