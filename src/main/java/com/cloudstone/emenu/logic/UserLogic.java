/**
 * @(#)UserLogic.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.List;

import com.cloudstone.emenu.constant.Const.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
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
    public User login(EmenuContext context, String userName, String encryptedPassword) {
        User user = userDb.getByName(context, userName);
        if (user == null || user.isDeleted()) {
            return null;
        }
        
        if (!user.getPassword().equals(encryptedPassword)) {
            return null;
        }
        
        return user;
    }
    
    public User add(EmenuContext context, User user) {
        User oldUser = userDb.getByName(context, user.getName());
        if (oldUser != null && !oldUser.isDeleted()) {
            throw new DataConflictException("用户名已存在");
        }
        long now = System.currentTimeMillis();
        user.setUpdateTime(now);
        if (oldUser !=null) {
            user.setId(oldUser.getId());
            user.setCreatedTime(oldUser.getCreatedTime());
            userDb.update(context, user);
            modifyPassword(context, user.getId(), user.getPassword());
        } else {
            user.setCreatedTime(now);

            // When the logged in user is not the super user, which means it's not through the admin tool,
            // set the newly added user's restaurant to match the logged in user's.
            User loggedInUser = userDb.get(context, context.getLoginUserId());
            if (loggedInUser.getType() != UserType.SUPER_USER) {
               user.setRestaurantId(context.getRestaurantId());
            }

            userDb.add(context, user);
        }
        return userDb.get(context, user.getId());
    }
    
    public User update(EmenuContext context, User user) {
    	//TODO BUG?
        User oldUser = userDb.getByName(context, user.getName());
        if (oldUser!=null && oldUser.getId()!=user.getId() && !oldUser.isDeleted()) {
            throw new DataConflictException("用户名已存在");
        }
        long now = System.currentTimeMillis();
        user.setUpdateTime(now);
        userDb.update(context, user);
        return userDb.get(context, user.getId());
    }
    
    public User getUser(EmenuContext context, int userId) {
        return userDb.get(context, userId);
    }
    
    public List<User> getAll(EmenuContext context) {
        List<User> users = userDb.getAll(context);
        DataUtils.filterDeleted(users);
        return users;
    }
    
    public boolean modifyPassword(EmenuContext context, int userId, String password) {
        return userDb.modifyPassword(context, userId, password);
    }
    
    public void delete(EmenuContext context, int userId) {
    	//TODO BUG?
        userDb.delete(context, userId);
    }
    
    public List<String> listUserNames(EmenuContext context) {
        List<User> users = getAll(context);
        List<String> names = new ArrayList<String>(users.size());
        for (User user:users) {
            names.add(user.getName());
        }
        return names;
    }
}
