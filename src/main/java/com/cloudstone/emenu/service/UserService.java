/**
 * @(#)UserService.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.service;

import org.springframework.stereotype.Service;

import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
@Service
public class UserService extends BaseService implements IUserService {
    
    @Override
    public User getUserByName(String userName) {
        return userDb.getUserByName(userName);
    }

    @Override
    public User add(User user) {
        return userDb.addUser(user);
    }
}
