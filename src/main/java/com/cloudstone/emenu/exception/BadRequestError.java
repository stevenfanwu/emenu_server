/**
 * @(#)BadRequestError.java, Aug 3, 2013. 
 *
 */
package com.cloudstone.emenu.exception;

/**
 * @author xuhongfeng
 */
public class BadRequestError extends HttpStatusError {
    private static final long serialVersionUID = 8040163874106379282L;

    public BadRequestError() {
        super(400, "", null);
    }

}
