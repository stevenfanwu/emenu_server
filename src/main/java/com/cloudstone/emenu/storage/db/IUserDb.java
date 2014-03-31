/**
 * @(#)IUserDb.java, 2013-6-20. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 */
public interface IUserDb extends IDb {

    public User getByName(EmenuContext context, String userName);

    public User get(EmenuContext context, int userId);

    public List<User> getAll(EmenuContext context);

    public User add(EmenuContext context, User user);

    public User update(EmenuContext context, User user);

    public boolean modifyPassword(EmenuContext context, int userId, String password);
}
