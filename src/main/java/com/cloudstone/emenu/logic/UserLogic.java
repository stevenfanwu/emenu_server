/**
 * @(#)UserLogic.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.storage.db.IUserDb;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class UserLogic extends BaseLogic {
    @Autowired
    private IUserDb userDb;
    
    /**
     * 
     * return null on authentication failed
     * 
     * @param userName
     * @param encryptedPassword
     * @return
     */
    public User login(String userName, String encryptedPassword) {
        User user = userDb.getByName(userName);
        if (user == null || user.isDeleted()) {
            return null;
        }
        
        if (!user.getPassword().equals(encryptedPassword)) {
            return null;
        }
        
        return user;
    }
    
    public User add(User user) {
        User oldUser = userDb.getByName(user.getName());
        if (oldUser != null && !oldUser.isDeleted()) {
            throw new DataConflictException("用户名已存在");
        }
        long now = System.currentTimeMillis();
        user.setUpdateTime(now);
        if (oldUser !=null) {
            user.setId(oldUser.getId());
            user.setCreatedTime(oldUser.getCreatedTime());
            userDb.update(user);
        } else {
            user.setCreatedTime(now);
            userDb.add(user);
        }
        return userDb.get(user.getId());
    }
    
    public User update(User user) {
        User oldUser = userDb.getByName(user.getName());
        if (oldUser!=null && oldUser.getId()!=user.getId() && !oldUser.isDeleted()) {
            throw new DataConflictException("用户名已存在");
        }
        long now = System.currentTimeMillis();
        user.setUpdateTime(now);
        userDb.update(user);
        return userDb.get(user.getId());
    }
    
    public User getUser(int userId) {
        return userDb.get(userId);
    }
    
    public List<User> getAll() {
        List<User> users = userDb.getAll();
        DataUtils.filterDeleted(users);
        return users;
    }
    
    public boolean modifyPassword(int userId, String password) {
        return userDb.modifyPassword(userId, password);
    }
    
    public void delete(int userId) {
        userDb.delete(userId);
    }
    
    public List<String> listUserNames() {
        List<User> users = getAll();
        List<String> names = new ArrayList<String>(users.size());
        for (User user:users) {
            names.add(user.getName());
        }
        return names;
    }
}
