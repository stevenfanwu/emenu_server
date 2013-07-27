/**
 * @(#)UserLogic.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.exception.UserNameConflictedException;

/**
 * @author xuhongfeng
 *
 */
@Component
public class UserLogic extends BaseLogic {
    
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
        if (user == null) {
            return null;
        }
        
        if (!user.getPassword().equals(encryptedPassword)) {
            return null;
        }
        
        return user;
    }
    
    public User add(User user) throws UserNameConflictedException {
        if (userService.getUserByName(user.getName()) != null) {
            throw new UserNameConflictedException();
        }
        userService.add(user);
        return userService.get(user.getId());
    }
    
    public User update(User user) {
        userService.update(user);
        return userService.get(user.getId());
    }
    
    public User getUser(int userId) {
        return userService.get(userId);
    }
    
    public List<User> getAll() {
        return userService.getAll();
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
