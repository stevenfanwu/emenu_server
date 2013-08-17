/**
 * @(#)IUserDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
public interface IUserDb extends IDb {

    public User getByName(String userName) ;
    public User get(int userId) ;
    public List<User> getAll() ;
    public User add(User user) ;
    public User update(User user) ;
    public boolean modifyPassword(int userId, String password) ;
}
