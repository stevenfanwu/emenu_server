/**
 * @(#)ServerError.java, 2013-6-23. 
 * 
 */
package com.cloudstone.emenu.exception;

/**
 * @author xuhongfeng
 *
 */
public class ServerError extends RuntimeException {
    private static final long serialVersionUID = 8202952708899156300L;

    public ServerError() {
        super();
    }

    public ServerError(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerError(String message) {
        super(message);
    }

    public ServerError(Throwable cause) {
        super(cause);
    }
    
    
}
