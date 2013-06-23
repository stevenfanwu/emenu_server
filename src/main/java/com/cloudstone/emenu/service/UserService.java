/**
 * @(#)UserService.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.service;

import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.exception.ServerError;

/**
 * @author xuhongfeng
 *
 */
@Service
public class UserService extends BaseService implements IUserService {
    
    @Override
    public User getUserByName(String userName) {
        try {
            return userDb.getUserByName(userName);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public User add(User user) {
        try {
            return userDb.addUser(user);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
}
