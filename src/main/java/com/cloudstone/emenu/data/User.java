/**
 * @(#)User.java, Jun 15, 2013. 
 *
 */
package com.cloudstone.emenu.data;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.cloudstone.emenu.constant.Const.UserType;

/**
 * @author xuhongfeng
 */
public class User extends IdName {

    /* encrypted */
    @JsonIgnore
    private String password;

    private int type;

    private String realName;

    /* 备注 */
    private String comment = "";

    /* ---------- static ---------- */
    public static User newSuperUser() {
        return newUser(UserType.SUPER_USER);
    }

    public static User newAdminUser() {
        return newUser(UserType.ADMIN);
    }

    public static User newUser() {
        return newUser(UserType.USER);
    }

    public static User newUser(int type) {
        User user = new User();
        user.setType(type);
        return user;
    }
    
    /* ---------- getter and setter ---------- */

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
