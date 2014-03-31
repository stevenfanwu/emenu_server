/**
 * @(#)PreconditionFailedException.java, Aug 9, 2013. 
 *
 */
package com.cloudstone.emenu.exception;

/**
 * @author xuhongfeng
 */
public class PreconditionFailedException extends HttpStatusError {
    private static final long serialVersionUID = 6705920148012484570L;

    public PreconditionFailedException(String msg,
                                       Throwable throwable) {
        super(412, msg, throwable);
    }

    public PreconditionFailedException(String msg) {
        super(412, msg);
    }

}
