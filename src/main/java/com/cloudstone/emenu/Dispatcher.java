/**
 * @(#)Dispatcher.java, Jun 1, 2013. 
 * 
 */
package com.cloudstone.emenu;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.servlet.DispatcherServlet;

import com.cloudstone.emenu.constant.Const;


/**
 * @author xuhongfeng
 *
 */
public class Dispatcher extends DispatcherServlet {
    private static final long serialVersionUID = 1245712434233720170L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        String homeDir = config.getServletContext().getRealPath("WEB-INF");
        System.setProperty(Const.PARAM_WEB_HOME_DIR, homeDir);
        super.init(config);
    }
}
