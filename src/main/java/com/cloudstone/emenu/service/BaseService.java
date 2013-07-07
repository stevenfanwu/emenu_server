/**
 * @(#)BaseService.java, 2013-6-20. 
 * 
 */
package com.cloudstone.emenu.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.storage.db.IDishDb;
import com.cloudstone.emenu.storage.db.ITableDb;
import com.cloudstone.emenu.storage.db.IUserDb;

/**
 * @author xuhongfeng
 *
 */
public class BaseService {
    
    @Autowired
    protected IUserDb userDb;
    @Autowired
    protected ITableDb tableDb;
    @Autowired
    protected IDishDb dishDb;

}
