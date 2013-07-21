/**
 * @(#)HttpStatusError.java, Jul 19, 2013. 
 * 
 */
package com.cloudstone.emenu.exception;

/**
 * @author xuhongfeng
 *
 */
public class HttpStatusError extends RuntimeException {

    private static final long serialVersionUID = -3436821334709854996L;

    private final int statusCode;

    public HttpStatusError(int statusCode) {
        super();
        this.statusCode = statusCode;
    }
    
    public HttpStatusError(int statusCode, Throwable e) {
        super(e);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
    
}
