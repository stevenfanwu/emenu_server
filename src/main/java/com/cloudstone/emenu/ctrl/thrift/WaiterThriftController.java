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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.cloudstone.emenu.EmenuContext;

/**
 * @author xuhongfeng
 */
@Controller
public class WaiterThriftController extends BaseThriftController {
    private static final Logger LOG = LoggerFactory.getLogger(WaiterThriftController.class);

    @RequestMapping(value = "/waiterservice.thrift", method = RequestMethod.POST)
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
            LOG.info("occupyTable, customerNum = " + customNumber);
            EmenuContext context = new EmenuContext();
            authorize(context, sessionId);
            String tableName = tableId;
            thriftLogic.occupyTable(context, tableName, customNumber);
            return true;
        }

        @Override
        public List<TableInfo> queryTableInfos(String sessionId)
                throws UserNotLoginException, PermissionDenyExcpetion,
                TException {
            LOG.info("queryTableInfos");
            EmenuContext context = new EmenuContext();
            authorize(context, sessionId);
            return thriftLogic.getAllTables(context);
        }

        @Override
        public boolean changeTable(String sessionId, String oldTableId,
                                   String newTableId) throws UserNotLoginException,
                PermissionDenyExcpetion, TableOccupiedException, TException {
            LOG.info("changeTable");
            EmenuContext context = new EmenuContext();
            authorize(context, sessionId);
            thriftLogic.changeTable(context, oldTableId, newTableId);
            return true;
        }

        @Override
        public boolean mergeTable(String sessionId, List<String> oldTableIds,
                                  String newTableId) throws UserNotLoginException,
                PermissionDenyExcpetion, TableOccupiedException, TException {
            LOG.info("mergeTable");
            //TODO
            return false;
        }

        @Override
        public boolean emptyTable(String sessionId, String tableName)
                throws UserNotLoginException, PermissionDenyExcpetion,
                TException {
            LOG.info("emptyTable");
            EmenuContext context = new EmenuContext();
            authorize(context, sessionId);
            thriftLogic.emptyTable(context, tableName);
            return true;
        }

        @Override
        public boolean callService(String sessionId, String tableId,
                                   ServiceType type) throws UserNotLoginException,
                TableEmptyException, TException {
            LOG.info("callService");
            //TODO
            return false;
        }
    }
}
