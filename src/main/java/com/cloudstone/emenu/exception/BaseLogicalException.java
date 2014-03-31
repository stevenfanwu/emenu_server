/**
 * @(#)BaseLogicalException.java, 2013-6-20. 
 *
 */
package com.cloudstone.emenu.exception;

/**
 * LogicalException is not a error, throw this when violating some logical
 * restrictions, like creating a existing user
 *
 * @author xuhongfeng
 */
public class BaseLogicalException extends Exception {

    private static final long serialVersionUID = -3522567106607510995L;

    public BaseLogicalException() {
        super();
    }

    public BaseLogicalException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseLogicalException(String message) {
        super(message);
    }

    public BaseLogicalException(Throwable cause) {
        super(cause);
    }
}
