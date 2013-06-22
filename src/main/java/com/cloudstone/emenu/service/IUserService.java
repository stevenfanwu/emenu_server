/**
 * @(#)IUserService.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.service;

import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
public interface IUserService {
    public User getUserByName(String userName);
    public User add(User user);
}
