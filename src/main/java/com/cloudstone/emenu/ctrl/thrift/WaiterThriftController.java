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
import cn.com.cloudstone.menu.server.thrift.api.TableStatus;
import cn.com.cloudstone.menu.server.thrift.api.UserNotLoginException;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.util.ThriftUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class WaiterThriftController extends BaseThriftController {
    private static final Logger LOG = LoggerFactory.getLogger(WaiterThriftController.class);

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
            LOG.info("occupyTable");
            authorize(sessionId);
            String tableName = tableId;
            Table table = tableLogic.getByName(tableName);
            TableInfo info = ThriftUtils.toTableInfo(table);
            if (info == null) {
                throw new TException("table not found");
            }
            if (info.getStatus() != TableStatus.Empty) {
                throw new TableOccupiedException();
            }
            //TODO check table capacity
            //TODO save customNumber
            table.setStatus(Const.TableStatus.OCCUPIED);
            tableLogic.update(table);
            
            return true;
        }

        @Override
        public List<TableInfo> queryTableInfos(String sessionId)
                throws UserNotLoginException, PermissionDenyExcpetion,
                TException {
            LOG.info("queryTableInfos");
            authorize(sessionId);
            List<Table> tables = tableLogic.getAll();
            return ThriftUtils.toTableInfo(tables);
        }

        @Override
        public boolean changeTable(String sessionId, String oldTableId,
                String newTableId) throws UserNotLoginException,
                PermissionDenyExcpetion, TableOccupiedException, TException {
            LOG.info("changeTable");
            return false;
        }

        @Override
        public boolean mergeTable(String sessionId, List<String> oldTableIds,
                String newTableId) throws UserNotLoginException,
                PermissionDenyExcpetion, TableOccupiedException, TException {
            LOG.info("mergeTable");
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean emptyTable(String sessionId, String tableId)
                throws UserNotLoginException, PermissionDenyExcpetion,
                TException {
            LOG.info("emptyTable");
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public boolean callService(String sessionId, String tableId,
                ServiceType type) throws UserNotLoginException,
                TableEmptyException, TException {
            LOG.info("callService");
            return false;
        }
    }
}
