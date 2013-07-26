/**
 * @(#)ThriftUtils.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import cn.com.cloudstone.menu.server.thrift.api.UserType;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 *
 */
public class ThriftUtils {
    public static UserType getUserType(User user) {
        if (user.getType() == Const.UserType.USER) {
            return UserType.Waiter;
        } else {
            return UserType.Admin;
        }
    }
}
