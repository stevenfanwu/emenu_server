/**
 * @(#)OrderThriftController.java, Jul 26, 2013. 
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.cloudstone.menu.server.thrift.api.AException;
import cn.com.cloudstone.menu.server.thrift.api.GoodsOrder;
import cn.com.cloudstone.menu.server.thrift.api.HasInvalidGoodsException;
import cn.com.cloudstone.menu.server.thrift.api.IOrderService;
import cn.com.cloudstone.menu.server.thrift.api.Order;
import cn.com.cloudstone.menu.server.thrift.api.TableEmptyException;
import cn.com.cloudstone.menu.server.thrift.api.UnderMinChargeException;
import cn.com.cloudstone.menu.server.thrift.api.UserNotLoginException;

import com.cloudstone.emenu.constant.Const.TableStatus;
import com.cloudstone.emenu.data.Table;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class OrderThriftController extends BaseThriftController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderThriftController.class);

    @RequestMapping(value="/orderservice.thrift", method=RequestMethod.POST)
    public void thrift(HttpServletRequest request,
            HttpServletResponse response) throws IOException, TException {
        process(request, response);
    }

    @Override
    protected TProcessor getProcessor() {
        return processor;
    }

    private IOrderService.Processor<Service> processor = new IOrderService.Processor<Service>(new Service());
    private class Service implements IOrderService.Iface {

        @Override
        public boolean submitOrder(String sessionId, Order order)
                throws UserNotLoginException, TableEmptyException,
                HasInvalidGoodsException, UnderMinChargeException, TException {
            LOG.info("submitOrder");
            authorize(sessionId);
            thriftLogic.submitOrder(order);
            return true;
        }

        @Override
        public List<Order> queryOrder(String sessionId, String tableName)
                throws UserNotLoginException, TableEmptyException, TException {
            LOG.info("queryOrder, tableName = " + tableName);
            authorize(sessionId);
            
            // check table
            Table table = tableLogic.getByName(tableName);
            if (table == null) {
                LOG.error("table == null");
                throw new TException("table not found, tableName = " + tableName);
            }
            if (table.getStatus() == TableStatus.EMPTY) {
                LOG.error("TableEmptyException");
                throw  new TableEmptyException();
            }
            
            List<Order> ret = new ArrayList<Order>();
            int orderId = table.getOrderId();
            com.cloudstone.emenu.data.Order orderValue = orderLogic.getOrder(orderId);
            if (orderValue == null) {
                LOG.error("orderValue == null, orderId = " + orderId);
                return ret;
            }
            Order order = new Order();
            order.setOriginalPrice(orderValue.getOriginPrice());
            order.setPrice(orderValue.getPrice());
            order.setTableId(tableName);
            List<GoodsOrder> goods = thriftLogic.listGoodsInOrder(orderId);
            order.setGoods(goods);
            
            ret.add(order);
            
            LOG.info("query Order success!");
            return ret;
        }

        @Override
        public boolean cancelGoods(String sessionId, int orderId, int goodsId)
                throws UserNotLoginException, AException, TException {
            LOG.info("cancelGoods");
            // TODO Auto-generated method stub
            return false;
        }
    }
}
