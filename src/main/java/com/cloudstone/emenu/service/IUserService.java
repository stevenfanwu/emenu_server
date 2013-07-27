/**
 * @(#)IUserService.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
public interface IUserService {
    public User getUserByName(String userName);
    public void add(User user);
    public boolean modifyPassword(int userId, String password);
    public User get(int userId);
    public List<User> getAll();
    public void delete(int userId);
    public void update(User user);
}
