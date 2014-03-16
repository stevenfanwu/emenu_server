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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.cloudstone.menu.server.thrift.api.IMEINotAllowedException;
import cn.com.cloudstone.menu.server.thrift.api.IProfileService;
import cn.com.cloudstone.menu.server.thrift.api.Login;
import cn.com.cloudstone.menu.server.thrift.api.UserNotLoginException;
import cn.com.cloudstone.menu.server.thrift.api.UserType;
import cn.com.cloudstone.menu.server.thrift.api.WrongUsernameOrPasswordException;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.ThriftSession;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.util.ThriftUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class ProfileThriftController extends BaseThriftController {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileThriftController.class);
    
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
            LOG.info("login, imei = " + imei);
            EmenuContext context = new EmenuContext();
            User user = userLogic.login(context, username, pwd);
            
            if (user == null) {
                throw new WrongUsernameOrPasswordException();
            }
            context.setLoginUserId(user.getId());
            context.setRestaurantId(user.getRestaurantId());
            if (!thriftLogic.isValidImei(context, imei)) {
                throw new IMEINotAllowedException();
            }
            
            //build session
            long ran = new Random().nextLong();
            String sessionId = String.valueOf(ran);
            ThriftSession session = new ThriftSession();
            session.setActivateTime(System.currentTimeMillis());
            session.setImei(imei);
            session.setUser(user);
            session.setSessionId(sessionId);
            thriftSessionDb.put(context, sessionId, session);
            
            //build Login
            UserType type = ThriftUtils.getUserType(user);
            Login login = new Login(sessionId, username, type);
            return login;
        }

        @Override
        public boolean logout(String sessionId) throws TException {
            EmenuContext context = new EmenuContext();
            thriftSessionDb.remove(context, sessionId);
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
            EmenuContext context = new EmenuContext();
            return userLogic.listUserNames(context);
        }
    }
}
