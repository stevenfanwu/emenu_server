/**
 * @(#)ProfileThriftController.java, Jul 25, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.thrift;

import java.io.IOException;
import java.util.List;
import java.util.Random;

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
import cn.com.cloudstone.menu.server.thrift.api.UserType;
import cn.com.cloudstone.menu.server.thrift.api.WrongUsernameOrPasswordException;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.ThriftSession;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.util.ThriftUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class ProfileThriftController extends BaseThriftController {
    
    @RequestMapping(value="/profileservice.thrift", method=RequestMethod.POST)
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
            //TODO check imei
            User user = userLogic.login(username, pwd);
            if (user == null) {
                throw new WrongUsernameOrPasswordException();
            }
            
            //build session
            long ran = new Random().nextLong();
            String sessionId = String.valueOf(ran);
            ThriftSession session = new ThriftSession();
            session.setActivateTime(System.currentTimeMillis());
            session.setImei(imei);
            thriftSessionDb.put(sessionId, session);
            
            //build Login
            UserType type = ThriftUtils.getUserType(user);
            Login login = new Login(sessionId, username, type);
            return login;
        }

        @Override
        public boolean logout(String sessionId) throws TException {
            try {
                thriftSessionDb.remove(sessionId);
            } catch (SQLiteException e) {
                throw new TException(e);
            }
            return true;
        }

        @Override
        public boolean changePassword(String sessionId, String oldPwd,
                String newPwd) throws UserNotLoginException,
                WrongUsernameOrPasswordException, TException {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<String> getAllUsers() throws TException {
            return userLogic.listUserNames();
        }
    }
}
