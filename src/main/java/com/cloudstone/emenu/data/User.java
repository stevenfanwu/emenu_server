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
    private long userId;
    
    /* login name */
    private String name;
    
    /* encrypted */
    @JsonIgnore
    private String password;

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
}
