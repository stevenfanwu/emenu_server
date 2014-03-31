/**
 * @(#)BaseWraper.java, Jul 30, 2013. 
 *
 */
package com.cloudstone.emenu.wrap;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.logic.MenuLogic;
import com.cloudstone.emenu.logic.OrderLogic;
import com.cloudstone.emenu.logic.RecordLogic;
import com.cloudstone.emenu.logic.TableLogic;
import com.cloudstone.emenu.logic.UserLogic;

/**
 * @author xuhongfeng
 */
public class BaseWraper {
    @Autowired
    protected UserLogic userLogic;
    @Autowired
    protected TableLogic tableLogic;
    @Autowired
    protected MenuLogic menuLogic;
    @Autowired
    protected OrderLogic orderLogic;
    @Autowired
    protected RecordLogic recordLogic;

}
