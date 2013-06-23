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
        USER(0), ADMIN(1), SUPER_USER(2);
        
        private int value;
        
        private UserType(int value) {
            this.value = value;
        }
        
        public int getValue() {
            return value;
        }
        
        public static UserType getByValue(int value) {
            for (UserType type:UserType.values()) {
                if (type.getValue() == value) {
                    return type;
                }
            }
            throw new RuntimeException("unknow UserType value");
        }
    };
    
    private long id;
    
    /* login name */
    private String name;
    
    /* encrypted */
    @JsonIgnore
    private String password;
    
    private UserType type = UserType.USER;
    
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

    public long getId() {
        return id;
    }

    public void setId(long userId) {
        this.id = userId;
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
