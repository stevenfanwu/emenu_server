/**
 * @(#)ProfileThriftController.java, Jul 25, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.thrift;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.cloudstone.menu.server.thrift.api.IMEINotAllowedException;
import cn.com.cloudstone.menu.server.thrift.api.IProfileService;
import cn.com.cloudstone.menu.server.thrift.api.Login;
import cn.com.cloudstone.menu.server.thrift.api.UserNotLoginException;
import cn.com.cloudstone.menu.server.thrift.api.WrongUsernameOrPasswordException;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class ProfileThriftController extends BaseThriftController {
    
    @RequestMapping(value="/*.thrift", method=RequestMethod.GET)
    public void get(HttpServletRequest request,
            HttpServletResponse response) throws IOException, TException {
        process(request, response);
    }
    
    @RequestMapping(value="/*.thrift", method=RequestMethod.PUT)
    public void put(HttpServletRequest request,
            HttpServletResponse response) throws IOException, TException {
        process(request, response);
    }
    
    @RequestMapping(value="/*.thrift", method=RequestMethod.DELETE)
    public void delete(HttpServletRequest request,
            HttpServletResponse response) throws IOException, TException {
        process(request, response);
    }
    
    @RequestMapping(value="/*.thrift", method=RequestMethod.POST)
    public void post(HttpServletRequest request,
            HttpServletResponse response) throws IOException, TException {
        process(request, response);
    }

    @Override
    protected TProcessor getProcessor() {
        return processor;
    }

    private TProcessor processor = new IProfileService.Processor<Service>(new Service());
    private class Service implements IProfileService.Iface {

        @Override
        public Login loginUser(String username, String pwd, String imei)
                throws WrongUsernameOrPasswordException,
                IMEINotAllowedException, TException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean logout(String sessionId) throws TException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean changePassword(String sessionId, String oldPwd,
                String newPwd) throws UserNotLoginException,
                WrongUsernameOrPasswordException, TException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public List<String> getAllUsers() throws TException {
            List<String> users = new ArrayList<String>();
            users.add("a");
            users.add("b");
            return users;
        }
    }
}
