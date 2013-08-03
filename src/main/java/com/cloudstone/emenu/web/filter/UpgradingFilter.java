/**
 * @(#)UpgradingFilter.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloudstone.emenu.logic.ConfigLogic;
import com.cloudstone.emenu.storage.db.util.DbUpgrader;

/**
 * @author xuhongfeng
 *
 */
public class UpgradingFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(UpgradingFilter.class);
    
    @Autowired
    private ConfigLogic configLogic;
    
    @Autowired
    private DbUpgrader dbUpgrader;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        String url = req.getRequestURI().toLowerCase();
        
//        LOG.info("upgrading filter : " + url);
        if (url.startsWith("/upgrading")
                || url.startsWith("/static")) {
            chain.doFilter(request, response);
            return;
        }
        if (configLogic.needUpgradeDb()) {
            dbUpgrader.checkUpgrade();
            if (url.startsWith("/api") || url.endsWith("thrifs")) {
                resp.sendError(512);
            } else {
                resp.sendRedirect("/upgrading");
            }
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void destroy() {
    }

}
