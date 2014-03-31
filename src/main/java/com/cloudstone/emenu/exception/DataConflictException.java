/**
 * @(#)DataConflictException.java, Aug 2, 2013. 
 *
 */
package com.cloudstone.emenu.exception;

import javax.servlet.http.HttpServletResponse;

/**
 * @author xuhongfeng
 */
public class DataConflictException extends HttpStatusError {
    private static final long serialVersionUID = 702039561449984471L;

    public DataConflictException(String msg) {
        super(HttpServletResponse.SC_CONFLICT, msg);
    }
}
