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
import com.cloudstone.emenu.data.Bill.BillArchive;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.PayType;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.service.IOrderService;
import com.cloudstone.emenu.storage.db.util.DbTransaction;
import com.cloudstone.emenu.util.DataUtils;


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
    private IOrderService orderService;
    
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
    
    public Bill payBill(Bill bill) {
        //TODO transaction for zhuwei
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
        List<Dish> dishes = listDishes(order.getId());
        BillArchive archive = BillArchive.build(order, table, dishes);
        bill.setArchive(archive);
        long now = System.currentTimeMillis();
        bill.setCreatedTime(now);
        bill.setUpdateTime(now);
        //Start transaction
        DbTransaction trans = openTrans();
        trans.begin();
        orderService.addBill(trans, bill);
        table.setStatus(TableStatus.EMPTY);
        tableLogic.update(trans, table);
        trans.commit();
        //End transaction
        return orderService.getBill(bill.getId());
    }
    
    public Bill getBillByOrderId(int orderId) {
        return orderService.getBillByOrderId(orderId);
    }
    
    public void changeTable(Table from, Table to) {
    }
}
