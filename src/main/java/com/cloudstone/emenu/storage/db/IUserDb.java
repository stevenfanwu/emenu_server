/**
 * @(#)IUserDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
public interface IUserDb {

    public User getByName(String userName) throws SQLiteException;
    public User get(long userId) throws SQLiteException;
    public List<User> getAll() throws SQLiteException;
    public User add(User user) throws SQLiteException;
    public User update(User user) throws SQLiteException;
    public void delete(long userId) throws SQLiteException;
    public boolean modifyPassword(long userId, String password) throws SQLiteException;
}
