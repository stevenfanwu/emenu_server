/**
 * @(#)UserService.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IUserDb;

/**
 * @author xuhongfeng
 *
 */
@Service
public class UserService extends BaseService implements IUserService {
    @Autowired
    private IUserDb userDb;
    
    @Override
    public User getUserByName(String userName) {
        try {
            return userDb.getByName(userName);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void update(User user) {
        try {
            userDb.update(user);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public boolean modifyPassword(int userId, String password) {
        try {
            return userDb.modifyPassword(userId, password);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void add(User user) {
        try {
            userDb.add(user);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public User get(int userId) {
        try {
            return userDb.get(userId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<User> getAll() {
        try {
            return userDb.getAll();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void delete(int userId) {
        try {
            userDb.delete(userId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
}
