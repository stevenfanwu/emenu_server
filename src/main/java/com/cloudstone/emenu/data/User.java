/**
 * @(#)User.java, Jun 15, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author xuhongfeng
 *
 */
public class User {
    public static enum UserType {
        USER, ADMIN, SUPER_USER
    };
    
    private long userId;
    
    /* login name */
    private String name;
    
    /* encrypted */
    @JsonIgnore
    private String password;
    
    private UserType type = UserType.USER;
    
    private String realName;
    
    /* 备注 */
    private String comment;

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
    
    public static User newUser(UserType type) {
        User user = new User();
        user.setType(type);
        return user;
    }
    
    /* ---------- getter and setter ---------- */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
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
