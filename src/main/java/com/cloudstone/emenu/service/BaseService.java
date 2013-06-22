/**
 * @(#)BaseService.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.storage.db.IUserDb;

/**
 * @author xuhongfeng
 *
 */
public class BaseService {
    
    @Autowired
    protected IUserDb userDb;

}
