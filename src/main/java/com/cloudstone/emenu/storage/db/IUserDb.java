/**
 * @(#)IUserDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
public interface IUserDb {

    public User getUserByName(String userName) throws SQLiteException;
    public User get(long userId) throws SQLiteException;
    public User addUser(User user) throws SQLiteException;
}
