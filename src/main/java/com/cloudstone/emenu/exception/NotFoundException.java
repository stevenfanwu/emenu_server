/**
 * @(#)NotFoundException.java, Aug 2, 2013. 
 * 
 */
package com.cloudstone.emenu.exception;

/**
 * @author xuhongfeng
 *
 */
public class NotFoundException extends HttpStatusError {

    private static final long serialVersionUID = -2868964411310955205L;

    public NotFoundException(String msg) {
        super(404, msg);
    }

}
