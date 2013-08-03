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
import com.cloudstone.emenu.service.UserService;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class UserLogic extends BaseLogic {
    @Autowired
    private UserService userService;
    
    /**
     * 
     * return null on authentication failed
     * 
     * @param userName
     * @param encryptedPassword
     * @return
     */
    public User login(String userName, String encryptedPassword) {
        User user = userService.getUserByName(userName);
        if (user == null || user.isDeleted()) {
            return null;
        }
        
        if (!user.getPassword().equals(encryptedPassword)) {
            return null;
        }
        
        return user;
    }
    
    public User add(User user) {
        User oldUser = userService.getUserByName(user.getName());
        if (oldUser != null && !oldUser.isDeleted()) {
            throw new DataConflictException("用户名已存在");
        }
        long now = System.currentTimeMillis();
        user.setUpdateTime(now);
        if (oldUser !=null) {
            user.setId(oldUser.getId());
            user.setCreatedTime(oldUser.getCreatedTime());
            userService.update(user);
        } else {
            user.setCreatedTime(now);
            userService.add(user);
        }
        return userService.get(user.getId());
    }
    
    public User update(User user) {
        User oldUser = userService.getUserByName(user.getName());
        if (oldUser!=null && oldUser.getId()!=user.getId() && !oldUser.isDeleted()) {
            throw new DataConflictException("用户名已存在");
        }
        long now = System.currentTimeMillis();
        user.setUpdateTime(now);
        userService.update(user);
        return userService.get(user.getId());
    }
    
    public User getUser(int userId) {
        return userService.get(userId);
    }
    
    public List<User> getAll() {
        List<User> users = userService.getAll();
        DataUtils.filterDeleted(users);
        return users;
    }
    
    public boolean modifyPassword(int userId, String password) {
        return userService.modifyPassword(userId, password);
    }
    
    public void delete(int userId) {
        userService.delete(userId);
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
