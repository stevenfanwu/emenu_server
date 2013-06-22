/**
 * @(#)BaseLogic.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.logic;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.service.IUserService;

/**
 * @author xuhongfeng
 *
 */
public class BaseLogic {
    @Autowired
    protected IUserService userService;
}
