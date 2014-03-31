/**
 * @(#)DbNotFoundException.java, 2013-7-6. 
 *
 */
package com.cloudstone.emenu.exception;

/**
 * @author xuhongfeng
 */
public class DbNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -3775642450622659951L;

    public DbNotFoundException() {
        super();
    }

    public DbNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DbNotFoundException(String message) {
        super(message);
    }

    public DbNotFoundException(Throwable cause) {
        super(cause);
    }
}
