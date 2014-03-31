/**
 * @(#)NotLoginException.java, Aug 3, 2013. 
 *
 */
package com.cloudstone.emenu.exception;

/**
 * @author xuhongfeng
 */
public class NotLoginException extends HttpStatusError {
    private static final long serialVersionUID = 5208486876498901380L;

    public NotLoginException(String msg) {
        super(401, msg);
    }

}
