/**
 * @(#)ServerError.java, 2013-6-23. 
 * 
 */
package com.cloudstone.emenu.exception;

/**
 * @author xuhongfeng
 *
 */
public class ServerError extends HttpStatusError {
    private static final long serialVersionUID = 8202952708899156300L;

    public ServerError() {
        super(500);
    }

    public ServerError(Throwable cause) {
        super(500, cause);
    }
}
