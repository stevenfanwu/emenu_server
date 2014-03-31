/**
 * @(#)Dispatcher.java, Jun 1, 2013. 
 *
 */
package com.cloudstone.emenu;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.DispatcherServlet;


/**
 * @author xuhongfeng
 */
public class Dispatcher extends DispatcherServlet {
    private static final long serialVersionUID = 1245712434233720170L;

    private static final Logger LOG = LoggerFactory.getLogger(Dispatcher.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
}
