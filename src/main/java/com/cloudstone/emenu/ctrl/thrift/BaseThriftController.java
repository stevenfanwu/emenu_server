/**
 * @(#)BaseThriftController.java, Jul 25, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.thrift;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.com.cloudstone.menu.server.thrift.api.UserNotLoginException;

import com.cloudstone.emenu.ctrl.BaseController;
import com.cloudstone.emenu.data.ThriftSession;
import com.cloudstone.emenu.logic.ThriftLogic;
import com.cloudstone.emenu.storage.db.ThriftSessionDb;
import com.cloudstone.emenu.util.UnitUtils;

/**
 * @author xuhongfeng
 *
 */
public abstract class BaseThriftController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(BaseThriftController.class);
        
    protected static final TProtocolFactory inProtocolFactory = new TBinaryProtocol.Factory();  
    protected static final TProtocolFactory outProtocolFactory = new TBinaryProtocol.Factory();  
    
    @Autowired
    protected ThriftLogic thriftLogic;
    
    @Autowired
    protected ThriftSessionDb thriftSessionDb;
    
    protected ThriftSession authorize(String sessionId) throws UserNotLoginException {
        ThriftSession session = thriftSessionDb.get(sessionId);
        if (session == null) {
            throw new UserNotLoginException();
        }
        long now = System.currentTimeMillis();
        if (now - session.getActivateTime() > UnitUtils.HOUR) {
            session.setActivateTime(now);
            thriftSessionDb.put(sessionId, session);
        } else {
            session.setActivateTime(now);
        }
        return session;
    }
    
    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, TException {
        response.setContentType("application/x-thrift");
        InputStream in = request.getInputStream();
        OutputStream out = response.getOutputStream();

        TTransport transport = new TIOStreamTransport(in, out);
        TTransport inTransport = transport;
        TTransport outTransport = transport;
        TProtocol inProtocol = inProtocolFactory.getProtocol(inTransport);
        TProtocol outProtocol = outProtocolFactory.getProtocol(outTransport);

        getProcessor().process(inProtocol, outProtocol);

        out.flush();
    }
    
    protected abstract TProcessor getProcessor();
}
