/**
 * @(#)UserLogic.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.logic;

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
    
    public User add(String userName, String encryptedPassword, int type) throws UserNameConflictedException {
        if (userService.getUserByName(userName) != null) {
            throw new UserNameConflictedException();
        }
        User user = User.newUser(type);
        user.setName(userName);
        user.setPassword(encryptedPassword);
        return userService.add(user);
    }
    
    public User update(long userId, String userName, String encryptedPassword,
            int type, String realName, String comment) {
        //TODO
        return null;
    }
    
    public User getUser(long userId) {
        return userService.get(userId);
    }
    
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
