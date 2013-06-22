/**
 * @(#)IUserDb.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
public interface IUserDb {

    public User getUserByName(String userName);
    public User addUser(User user);
}
