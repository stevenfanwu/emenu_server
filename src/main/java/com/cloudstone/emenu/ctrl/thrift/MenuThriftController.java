/**
 * @(#)MenuThriftController.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.thrift;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.cloudstone.menu.server.thrift.api.IMenuService;
import cn.com.cloudstone.menu.server.thrift.api.IMenuService.Processor;
import cn.com.cloudstone.menu.server.thrift.api.Menu;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class MenuThriftController extends BaseThriftController {
    private static final Logger LOG = LoggerFactory.getLogger(MenuThriftController.class);

    @RequestMapping(value="/menuservice.thrift", method=RequestMethod.POST)
    public void thrift(HttpServletRequest request,
            HttpServletResponse response) throws IOException, TException {
        process(request, response);
    }

    @Override
    protected TProcessor getProcessor() {
        return processor;
    }
    
    private Processor<Service> processor = new Processor<MenuThriftController.Service>(new Service());
    
    private class Service implements IMenuService.Iface {

        @Override
        public Menu getCurrentMenu() throws TException {
            LOG.info("getCurrentMenu");
            return thriftLogic.getCurrentMenu();
        }

        @Override
        public List<String> getAllNotes() throws TException {
            LOG.info("getAllNotes");
            return null;
        }
    }
}
