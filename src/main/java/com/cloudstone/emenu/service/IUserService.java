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
    public User add(User user);
    public User update(User user);
    public boolean modifyPassword(long userId, String password);
    public User get(long userId);
    public List<User> getAll();
    public void delete(long userId);
}
