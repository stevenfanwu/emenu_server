/**
 * @(#)LicenceFilter.java, Oct 5, 2013. 
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

import com.cloudstone.emenu.util.LicenceHelper;

/**
 * @author xuhongfeng
 */
public class LicenceFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger(LicenceFilter.class);

    @Autowired
    private LicenceHelper licenceHelper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        String url = req.getRequestURI().toLowerCase();
        if (url.startsWith("/licence")
                || url.startsWith("/static")) {
            chain.doFilter(request, response);
            return;
        }

        if (licenceHelper.checkLicence().isSuccess()) {
            chain.doFilter(req, resp);
            return;
        }
        LOG.info("licence filter not pass");
        resp.sendRedirect("/licence");
        return;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

}
