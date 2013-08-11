/**
 * @(#)OrderLogic.java, Jul 29, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.constant.Const.TableStatus;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.PayType;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.exception.PreconditionFailedException;
import com.cloudstone.emenu.service.IOrderService;
import com.cloudstone.emenu.storage.db.util.DbTransaction;
import com.cloudstone.emenu.util.DataUtils;
import com.cloudstone.emenu.util.PrinterUtils;
import com.cloudstone.emenu.util.StringUtils;
import com.cloudstone.emenu.util.VelocityRender;
import com.cloudstone.emenu.wrap.OrderWraper;


/**
 * @author xuhongfeng
 *
 */
@Component
public class OrderLogic extends BaseLogic {
    private static final Logger LOG = LoggerFactory.getLogger(OrderLogic.class);
    
    @Autowired
    private TableLogic tableLogic;
    @Autowired
    private MenuLogic menuLogic;
    
    @Autowired
    protected OrderWraper orderWraper;
    
    @Autowired
    private IOrderService orderService;
    
    @Autowired
    private VelocityRender velocityRender;
    
    public List<OrderDish> listOrderDish(int orderId) {
        List<OrderDish> datas = orderService.listOrderDish(orderId);
        DataUtils.filterDeleted(datas);
        return datas;
    }
    
    public void addOrderDish(OrderDish orderDish) {
        long now = System.currentTimeMillis();
        orderDish.setCreatedTime(now);
        orderDish.setUpdateTime(now);
        orderService.addOrderDish(orderDish);
    }
    
    public Order getOrder(int orderId) {
        return orderService.getOrder(orderId);
    }
    
    public void addOrder(Order order) {
        if (orderService.getOrder(order.getId()) != null) {
            throw new DataConflictException("请勿重复下单");
        }
        long now = System.currentTimeMillis();
        order.setUpdateTime(now);
        order.setCreatedTime(now);
        orderService.addOrder(order);
    }
    
    public List<Dish> listDishes(int orderId) {
        return orderService.listDishes(orderId);
    }
    
    public List<OrderDish> listOrderDishes(int orderId) {
        List<OrderDish> datas = orderService.listOrderDish(orderId);
        DataUtils.filterDeleted(datas);
        return datas;
    }
    
    public List<PayType> listPayTypes() {
        List<PayType> datas = orderService.listPayTypes();
        DataUtils.filterDeleted(datas);
        return datas;
    }
    
    public List<Bill> listBills() {
        List<Bill> datas = orderService.listBills();
        DataUtils.filterDeleted(datas);
        return datas;
    }
    
    public Bill payBill(Bill bill, User user) {
        
        if (getBillByOrderId(bill.getOrderId()) != null) {
            throw new DataConflictException("请勿重复提交订单");
        }
        Order order = getOrder(bill.getOrderId());
        if (order == null) {
            throw new BadRequestError();
        }
        Table table = tableLogic.get(order.getTableId());
        if (table == null || table.getStatus() != TableStatus.OCCUPIED) {
            throw new BadRequestError();
        }
        OrderVO orderVO = orderWraper.wrap(order);
        bill.setOrder(orderVO);
        long now = System.currentTimeMillis();
        bill.setCreatedTime(now);
        bill.setUpdateTime(now);
        //Start transaction
        DbTransaction trans = openTrans();
        trans.begin();
        try {
            orderService.addBill(trans, bill);
            table.setStatus(TableStatus.EMPTY);
            table.setOrderId(0);
            tableLogic.update(trans, table);
            
            if (!StringUtils.isBlank(bill.getPrinter())) {
                try {
                    PrinterUtils.print(bill.getPrinter(), velocityRender.renderBill(bill, user));
                } catch (Exception e) {
                    throw new PreconditionFailedException("打印失败", e);
                }
            }
            
            trans.commit();
            //End transaction
        } finally {
            trans.close();
        }
        return orderService.getBill(bill.getId());
    }
    
    public Bill getBillByOrderId(int orderId) {
        return orderService.getBillByOrderId(orderId);
    }
    
    public void changeTable(Table from, Table to) {
    }
}
