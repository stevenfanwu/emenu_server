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
import cn.com.cloudstone.menu.server.thrift.api.HasInvalidGoodsException;
import cn.com.cloudstone.menu.server.thrift.api.IOrderService;
import cn.com.cloudstone.menu.server.thrift.api.Order;
import cn.com.cloudstone.menu.server.thrift.api.TableEmptyException;
import cn.com.cloudstone.menu.server.thrift.api.UnderMinChargeException;
import cn.com.cloudstone.menu.server.thrift.api.UserNotLoginException;

import com.cloudstone.emenu.EmenuContext;

/**
 * @author xuhongfeng
 */
@Controller
public class OrderThriftController extends BaseThriftController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderThriftController.class);

    @RequestMapping(value = "/orderservice.thrift", method = RequestMethod.POST)
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
            EmenuContext context = new EmenuContext();
            authorize(context, sessionId);
            thriftLogic.submitOrder(context, order);
            return true;
        }

        @Override
        public List<Order> queryOrder(String sessionId, String tableName)
                throws UserNotLoginException, TableEmptyException, TException {
            LOG.info("queryOrder, tableName = " + tableName);
            EmenuContext context = new EmenuContext();
            authorize(context, sessionId);

            List<Order> ret = new ArrayList<Order>();

            Order order = thriftLogic.getOrderByTable(context, tableName);
            if (order != null) {
                ret.add(order);
            }

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
