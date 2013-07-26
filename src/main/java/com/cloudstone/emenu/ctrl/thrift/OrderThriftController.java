/**
 * @(#)OrderThriftController.java, Jul 26, 2013. 
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

import cn.com.cloudstone.menu.server.thrift.api.AException;
import cn.com.cloudstone.menu.server.thrift.api.HasInvalidGoodsException;
import cn.com.cloudstone.menu.server.thrift.api.IOrderService;
import cn.com.cloudstone.menu.server.thrift.api.Order;
import cn.com.cloudstone.menu.server.thrift.api.TableEmptyException;
import cn.com.cloudstone.menu.server.thrift.api.UnderMinChargeException;
import cn.com.cloudstone.menu.server.thrift.api.UserNotLoginException;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class OrderThriftController extends BaseThriftController {

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
            // TODO Auto-generated method stub
            return false;
        }

        @Override
        public List<Order> queryOrder(String sessionId, String tableId)
                throws UserNotLoginException, TableEmptyException, TException {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public boolean cancelGoods(String sessionId, int orderId, int goodsId)
                throws UserNotLoginException, AException, TException {
            // TODO Auto-generated method stub
            return false;
        }
    }
}
