/**
 * @(#)WaiterThriftController.java, Jul 26, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.thrift;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.cloudstone.menu.server.thrift.api.IWaiterService;
import cn.com.cloudstone.menu.server.thrift.api.PermissionDenyExcpetion;
import cn.com.cloudstone.menu.server.thrift.api.ServiceType;
import cn.com.cloudstone.menu.server.thrift.api.TableEmptyException;
import cn.com.cloudstone.menu.server.thrift.api.TableInfo;
import cn.com.cloudstone.menu.server.thrift.api.TableOccupiedException;
import cn.com.cloudstone.menu.server.thrift.api.UserNotLoginException;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class WaiterThriftController extends BaseThriftController {

    @RequestMapping(value="/waiterservice.thrift", method=RequestMethod.POST)
    public void thrift(HttpServletRequest request,
            HttpServletResponse response) throws IOException, TException {
        process(request, response);
    }

    @Override
    protected TProcessor getProcessor() {
        return processor;
    }
    
    private IWaiterService.Processor<Service> processor
        = new IWaiterService.Processor<Service>(new Service());
    private class Service implements IWaiterService.Iface {

        @Override
        public boolean occupyTable(String sessionId, String tableId,
                int customNumber) throws UserNotLoginException,
                PermissionDenyExcpetion, TableOccupiedException, TException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public List<TableInfo> queryTableInfos(String sessionId)
                throws UserNotLoginException, PermissionDenyExcpetion,
                TException {
            authorize(sessionId);
            
            //TODO
            return null;
        }

        @Override
        public boolean changeTable(String sessionId, String oldTableId,
                String newTableId) throws UserNotLoginException,
                PermissionDenyExcpetion, TableOccupiedException, TException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean mergeTable(String sessionId, List<String> oldTableIds,
                String newTableId) throws UserNotLoginException,
                PermissionDenyExcpetion, TableOccupiedException, TException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean emptyTable(String sessionId, String tableId)
                throws UserNotLoginException, PermissionDenyExcpetion,
                TException {
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean callService(String sessionId, String tableId,
                ServiceType type) throws UserNotLoginException,
                TableEmptyException, TException {
            // TODO Auto-generated method stub
            return false;
        }
    }
}
