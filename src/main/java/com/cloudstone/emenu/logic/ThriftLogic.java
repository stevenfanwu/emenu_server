/**
 * @(#)ThriftLogic.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.com.cloudstone.menu.server.thrift.api.Goods;
import cn.com.cloudstone.menu.server.thrift.api.GoodsOrder;
import cn.com.cloudstone.menu.server.thrift.api.HasInvalidGoodsException;
import cn.com.cloudstone.menu.server.thrift.api.Img;
import cn.com.cloudstone.menu.server.thrift.api.Menu;
import cn.com.cloudstone.menu.server.thrift.api.MenuPage;
import cn.com.cloudstone.menu.server.thrift.api.Order;
import cn.com.cloudstone.menu.server.thrift.api.PadInfo;
import cn.com.cloudstone.menu.server.thrift.api.TableEmptyException;
import cn.com.cloudstone.menu.server.thrift.api.TableInfo;
import cn.com.cloudstone.menu.server.thrift.api.TableOccupiedException;
import cn.com.cloudstone.menu.server.thrift.api.TableStatus;
import cn.com.cloudstone.menu.server.thrift.api.UnderMinChargeException;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishNote;
import com.cloudstone.emenu.data.DishRecord;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.Pad;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.ThriftSession;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.misc.PollingManager;
import com.cloudstone.emenu.data.misc.PollingManager.PollingMessage;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.storage.cache.ThriftCache;
import com.cloudstone.emenu.storage.db.ThriftSessionDb;
import com.cloudstone.emenu.util.CollectionUtils;
import com.cloudstone.emenu.util.DataUtils;
import com.cloudstone.emenu.util.StringUtils;
import com.cloudstone.emenu.util.ThriftUtils;
import com.cloudstone.emenu.wrap.OrderWraper;

/**
 * @author xuhongfeng
 *
 */
@Component
public class ThriftLogic extends BaseLogic {
    private static final Logger LOG = LoggerFactory.getLogger(ThriftLogic.class);
    
    @Autowired
    private TableLogic tableLogic;
    @Autowired
    private MenuLogic menuLogic;
    @Autowired
    private OrderLogic orderLogic;
    @Autowired
    private DeviceLogic deviceLogic;
    @Autowired
    private PrinterLogic printerLogic;
    @Autowired
    private UserLogic userLogic;
    @Autowired
    private RecordLogic recordLogic;
    @Autowired
    private ThriftCache thriftCache;
    @Autowired
    private ThriftSessionDb thriftSessionDb;
    @Autowired
    private OrderWraper orderWraper;
    @Autowired
    private PollingManager pollingManager;

    public TableInfo getTableInfo(EmenuContext context, String tableName) throws TException {
        Table table = tableLogic.getByName(context, tableName);
        if (table == null) {
            return null;
        }
        int customerNumber = 0;
        int orderId = table.getId();
        com.cloudstone.emenu.data.Order order = orderLogic.getOrder(context, orderId);
        if (order != null) {
            customerNumber = order.getCustomerNumber();
        }
        return new TableInfo(table.getName(), customerNumber,
                ThriftUtils.getTableStatus(table),orderId);
    }
    
    public List<TableInfo> getAllTables(EmenuContext context) throws TException {
        List<Table> tables = tableLogic.getAll(context);
        List<TableInfo> infos = new ArrayList<TableInfo>(tables.size());
        for (Table t:tables) {
            infos.add(getTableInfo(context, t.getName()));
        }
        return infos;
    }
    
    public void occupyTable(EmenuContext context, String tableName,
            int customerNumber) throws TException, TableOccupiedException {
        TableInfo info = getTableInfo(context, tableName);
        if (info == null) {
            throw new TException("table not found");
        }
        if (info.getStatus() != TableStatus.Empty) {
            throw new TableOccupiedException();
        }
        Table table = tableLogic.getByName(context, tableName);
        table.setStatus(Const.TableStatus.OCCUPIED);
        tableLogic.update(context, table);
        tableLogic.setCustomerNumber(context, table.getId(), customerNumber);
        
        pollingManager.putMessage(new PollingMessage(PollingMessage.TYPE_OCCUPY_TABLE, table));
    }
    
    public void emptyTable(EmenuContext context, String tableName) throws TException {
        Table table = tableLogic.getByName(context, tableName);
        if (table == null) {
            throw new TException("table not found, tableName = " + tableName);
        }
        tableLogic.clearTable(context, table);
    }
    
    public Menu getCurrentMenu(EmenuContext context) {
        com.cloudstone.emenu.data.Menu m = menuLogic.getCurrentMenu(context);
        if (m == null) {
            return null;
        }
        //TODO logo
        Menu menu = new Menu(m.getId(), m.getName(), listMenuPage(context, m.getId()), "logo");
        
        return menu;
    }
    
    public List<MenuPage> listMenuPage(EmenuContext context, int menuId) {
        List<MenuPage> ret = new ArrayList<MenuPage>();
        List<Chapter> chapters = menuLogic.listChapterByMenuId(context, menuId);
        for (Chapter chapter:chapters) {
            List<com.cloudstone.emenu.data.MenuPage> pages =
                    menuLogic.listMenuPageByChapterId(context, chapter.getId());
            for (com.cloudstone.emenu.data.MenuPage page:pages) {
                List<Dish> dishes = menuLogic.getDishByMenuPageId(context, page.getId());
                List<Goods> goodsList = new ArrayList<Goods>();
                for (Dish dish:dishes) {
                    if (dish.getId() >= 0) {//<0 means Null Dish
                        Goods goods = new Goods();
                        goods.setId(dish.getId());
                        goods.setName(dish.getName());
                        goods.setShortName(dish.getName());
                        goods.setPrice(dish.getPrice());
                        goods.setIntroduction(dish.getDesc());
                        goods.setCategory(chapter.getName());
                        goods.setOnSales(dish.isSpecialPrice());
                        goods.setSpicy(dish.getSpicy());
                        goods.setSoldout(dish.isSoldout());
                        goods.setNumberDecimalPermited(dish.isNonInt());
                        if (!StringUtils.isBlank(dish.getImageId())) {
                            List<Img> imgs = new ArrayList<Img>();
                            Img img = new Img("images/"+dish.getImageId(),
                                    "images/"+dish.getImageId());
                            imgs.add(img);
                            goods.setImgs(imgs);
                        }
                        goodsList.add(goods);
                    }
                }
                MenuPage p = new MenuPage(page.getId(), ThriftUtils.getPageLayoutType(page), goodsList);
                ret.add(p);
            }
        }
        return ret;
    }
    
    public List<String> getAllNotes(EmenuContext context) throws TException {
        List<DishNote> notes = menuLogic.listAllDishNote(context);
        return DataUtils.pickNames(notes);
    }
    
    public void submitOrder(EmenuContext context, Order order) throws TableEmptyException, HasInvalidGoodsException,
        UnderMinChargeException, TException {
        String tableName = order.getTableId();
        boolean isAddOrder = false;
        
        //check goods
        List<Dish> dishes = new ArrayList<Dish>();
        for (GoodsOrder g : order.getGoods()) {
            int dishId = g.getId();
            Dish dish = menuLogic.getDish(context, dishId, false);
            if (dish == null || dish.isDeleted()) {
                throw new HasInvalidGoodsException();
            }
            g.setPrice(dish.getPrice());
            dishes.add(dish);

        }
        
        double price = 0;
        for (GoodsOrder o:order.getGoods()) {
            price += o.getPrice()*o.getNumber();
        }
        price = DataUtils.calMoney(price);
        order.setOriginalPrice(price);
        order.setPrice(price);
        
        //check table
        Table table = tableLogic.getByName(context, tableName);
        if (table == null) {
            throw new TException("table not found");
        }
        if (table.getStatus() == Const.TableStatus.EMPTY) {
            throw new TableEmptyException();
        }
        if (order.getOriginalPrice() < table.getMinCharge()) {
            throw new UnderMinChargeException();
        }
        
        com.cloudstone.emenu.data.Order orderValue = new com.cloudstone.emenu.data.Order();
        orderValue.setOriginPrice(order.getOriginalPrice());
        orderValue.setPrice(order.getPrice());
        orderValue.setTableId(table.getId());
        
        int userId = context.getLoginUserId();
        
        //merge order if necessary
        com.cloudstone.emenu.data.Order oldOrder = null;
        if (table.getOrderId() != 0) {
            oldOrder = orderLogic.getOrder(context, table.getOrderId());
        }
        if (oldOrder != null) {
            isAddOrder = true;
            oldOrder.setOriginPrice(orderValue.getOriginPrice() + oldOrder.getOriginPrice());
            oldOrder.setPrice(orderValue.getPrice() + oldOrder.getPrice());
            oldOrder.setUserId(userId);
            for (int i=0; i<dishes.size(); i++) {
                DishRecord record = new DishRecord();
                Dish dish = dishes.get(i);
                GoodsOrder g = order.getGoods().get(i);
                record.setTime(System.currentTimeMillis());
                record.setDishId(dish.getId());
                record.setCount((int) g.getNumber());
                record.setOrderId(oldOrder.getId());
                recordLogic.addAddDishRecord(context, record);    
            }
        }
        orderValue.setUserId(userId);
        
        List<OrderDish> relations = new ArrayList<OrderDish>();
        for (int i=0; i<dishes.size(); i++) {
            Dish dish = dishes.get(i);
            GoodsOrder g = order.getGoods().get(i);
            
            OrderDish r = new OrderDish();
            r.setDishId(dish.getId());
            r.setNumber(g.getNumber());
            r.setPrice(g.getPrice());
            if (g.getRemarks() != null) {
                r.setRemarks(g.getRemarks().toArray(new String[0]));
            }
            r.setStatus(ThriftUtils.getOrderDishStatus(g));
            relations.add(r);
            
            
        }
        List<OrderDish> needUpdate = new ArrayList<OrderDish>();
        List<OrderDish> needInsert = new ArrayList<OrderDish>();
        if (oldOrder != null) {
            List<OrderDish> oldRelations = orderLogic.listOrderDishes(context, oldOrder.getId());
            for (OrderDish oldR:oldRelations) {
                OrderDish found = null;
                for (OrderDish r : relations) {
                    if (r.getDishId() == oldR.getDishId()) {
                        found = r;
                        break;
                    }
                }
                if (found != null) {
                    oldR.setNumber(oldR.getNumber() + found.getNumber());
                    if (found.getRemarks()!=null && found.getRemarks().length>0) {
                        oldR.setRemarks(found.getRemarks());
                    }
                    needUpdate.add(oldR);
                    relations.remove(found);
                }
            }
        }
        for (OrderDish r:relations) {
            needInsert.add(r);
        }
        
        int customerNumber = tableLogic.getCustomerNumber(context, table.getId());
        context.beginTransaction(dataSource);
        try {
            if (oldOrder != null) {
                oldOrder.setCustomerNumber(customerNumber);
                orderValue.setCustomerNumber(customerNumber);
                orderValue.setId(oldOrder.getId());
                orderLogic.updateOrder(context, oldOrder);
                for (OrderDish r:needUpdate) {
                    orderLogic.updateOrderDish(context, r);
                }
                for (OrderDish r:needInsert) {
                    r.setOrderId(oldOrder.getId());
                    orderLogic.addOrderDish(context, r);
                }
                table.setOrderId(oldOrder.getId());
            } else {
                orderValue.setCustomerNumber(customerNumber);
                orderLogic.addOrder(context, orderValue);
                for (OrderDish r:needInsert) {
                    r.setOrderId(orderValue.getId());
                    orderLogic.addOrderDish(context, r);
                }
                table.setOrderId(orderValue.getId());
            }
            tableLogic.update(context, table);
            context.commitTransaction();
        } finally {
            context.closeTransaction(dataSource);
        }
        OrderVO orderVOALL = null;
        if(oldOrder != null) {
            orderVOALL = orderWraper.wrap(context, oldOrder);
        } else {
            orderVOALL = orderWraper.wrap(context, orderValue);
        }
        
        List<OrderDish> relationsAdd = new ArrayList<OrderDish>();
        for (int i=0; i<dishes.size(); i++) {
            Dish dish = dishes.get(i);
            GoodsOrder g = order.getGoods().get(i);
            
            OrderDish r = new OrderDish();
            r.setDishId(dish.getId());
            r.setNumber(g.getNumber());
            r.setPrice(g.getPrice());
            if (g.getRemarks() != null) {
                r.setRemarks(g.getRemarks().toArray(new String[0]));
            }
            r.setStatus(ThriftUtils.getOrderDishStatus(g));
            relationsAdd.add(r);
        }
        for (OrderDish r:relationsAdd) {
            r.setOrderId(orderValue.getId());
        }
        orderValue.setCreatedTime(System.currentTimeMillis());
        orderValue.setUpdateTime(System.currentTimeMillis());
        OrderVO orderVOADD = orderWraper.wrap(context, orderValue, relationsAdd);

        pollingManager.putMessage(
                new PollingMessage(PollingMessage.TYPE_NEW_ORDER, orderVOALL));
        try {
            if (!isAddOrder)
                printerLogic.printOrder(context, orderVOADD,
                        userLogic.getUser(context, context.getLoginUserId()));
            else
                printerLogic.printAddOrder(context, orderVOADD,
                        userLogic.getUser(context, context.getLoginUserId()));
        } catch (Exception e) {
            LOG.error("", e);
        }
    }
    
    public Order getOrderByTable(EmenuContext context, String tableName) throws TableEmptyException, TException {
        
        // check table
        Table table = tableLogic.getByName(context, tableName);
        if (table == null) {
            throw new TException("table not found, tableName = " + tableName);
        }
        if (table.getStatus() == Const.TableStatus.EMPTY) {
            throw  new TableEmptyException();
        }
        
        int orderId = table.getOrderId();
        com.cloudstone.emenu.data.Order orderValue = orderLogic
                .getOrder(context, orderId);
        if (orderValue == null) {
            return null;
        }
        
        Order order = new Order();
        order.setOriginalPrice(orderValue.getOriginPrice());
        order.setPrice(orderValue.getPrice());
        order.setTableId(tableName);
        
        List<GoodsOrder> goods = listGoodsInOrder(context, orderId);
        order.setGoods(goods);
        
        return order;
    }
    
    public List<GoodsOrder> listGoodsInOrder(EmenuContext context, int orderId) {
        List<GoodsOrder> goods = new ArrayList<GoodsOrder>();
        List<OrderDish> relations = orderLogic.listOrderDishes(context, orderId);
        for (OrderDish r:relations) {
            Dish dish = menuLogic.getDish(context, r.getDishId(), false);
            if (dish == null) {
                continue;
            }
            GoodsOrder g = new GoodsOrder();
            g.setId(dish.getId());
            g.setNumber(r.getNumber());
            g.setPrice(r.getPrice());
            g.setRemarks(CollectionUtils.arrayToList(r.getRemarks()));
            g.setName(dish.getName());
            g.setShortName(dish.getName());
            g.setCategory(menuLogic.getCategory(context, g.getId()));
            g.setOrderid(orderId);
            g.setOnSales(dish.isSpecialPrice());
            //TODO
//            g.setGoodstate(goodstate);
            goods.add(g);
        }
        return goods;
    }
    
    public void onPadChanged() {
        thriftCache.onPadChanged();
    }
    
    public boolean isValidImei(EmenuContext context, String imei) {
        return thriftCache.isValidImei(context, imei);
    }
    
    public User getLatestUser(EmenuContext context, String imei) {
        ThriftSession s = thriftSessionDb.getLatest(context, imei);
        if (s != null) {
            return s.getUser();
        }
        return null;
    }
    
    public ThriftSession getLatestSession(EmenuContext context, String imei) {
        return thriftSessionDb.getLatest(context, imei);
    }
    
    public void submitPadInfo(EmenuContext context, PadInfo info) throws TException {
        String imei = info.getIMEI();
        Pad pad = deviceLogic.getPad(context, imei);
        if (pad == null) {
            throw new TException("pad not found");
        }
        pad.setBatteryLevel(info.getBatteryLevel());
        deviceLogic.updatePad(context, pad);
    }
    
    public void changeTable(EmenuContext context, String oldTablename, String newTableName)
            throws TableOccupiedException, TException {
        Table from = tableLogic.getByName(context, oldTablename);
        if (from==null || from.isDeleted()) {
            throw new TException("桌子不存在: + " + oldTablename);
        }
        Table to = tableLogic.getByName(context, newTableName);
        if (to==null || to.isDeleted()) {
            throw new TException("桌子不存在: + " + newTableName);
        }
        if (to.getStatus() != Const.TableStatus.EMPTY) {
            throw new TableOccupiedException();
        }
        try {
            tableLogic.changeTable(context, from.getId(), to.getId());
        } catch (Throwable e) {
            throw new TException(e.getMessage());
        }
    }
}
