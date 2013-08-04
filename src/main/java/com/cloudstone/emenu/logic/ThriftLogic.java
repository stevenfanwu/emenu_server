/**
 * @(#)ThriftLogic.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
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

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishNote;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.Pad;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.ThriftSession;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.storage.cache.ThriftCache;
import com.cloudstone.emenu.storage.db.ThriftSessionDb;
import com.cloudstone.emenu.util.CollectionUtils;
import com.cloudstone.emenu.util.DataUtils;
import com.cloudstone.emenu.util.StringUtils;
import com.cloudstone.emenu.util.ThriftUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class ThriftLogic extends BaseLogic {
    @Autowired
    private TableLogic tableLogic;
    @Autowired
    private MenuLogic menuLogic;
    @Autowired
    private OrderLogic orderLogic;
    @Autowired
    private DeviceLogic deviceLogic;
    @Autowired
    private ThriftCache thriftCache;
    @Autowired
    private ThriftSessionDb thriftSessionDb;

    public TableInfo getTableInfo(String tableName) throws TException {
        Table table = tableLogic.getByName(tableName);
        if (table == null) {
            return null;
        }
        int customerNumber = 0;
        int orderId = table.getId();
        com.cloudstone.emenu.data.Order order = orderLogic.getOrder(orderId);
        if (order != null) {
            customerNumber = order.getCustomerNumber();
        }
        return new TableInfo(table.getName(), customerNumber,
                ThriftUtils.getTableStatus(table),orderId);
    }
    
    public List<TableInfo> getAllTables() throws TException {
        List<Table> tables = tableLogic.getAll();
        List<TableInfo> infos = new ArrayList<TableInfo>(tables.size());
        for (Table t:tables) {
            infos.add(getTableInfo(t.getName()));
        }
        return infos;
    }
    
    public void occupyTable(String tableName) throws TException, TableOccupiedException {
        TableInfo info = getTableInfo(tableName);
        if (info == null) {
            throw new TException("table not found");
        }
        if (info.getStatus() != TableStatus.Empty) {
            throw new TableOccupiedException();
        }
        Table table = tableLogic.getByName(tableName);
        table.setStatus(Const.TableStatus.OCCUPIED);
        tableLogic.update(table);
    }
    
    public void emptyTable(String tableName) throws TException {
        Table table = tableLogic.getByName(tableName);
        if (table == null) {
            throw new TException("table not found, tableName = " + tableName);
        }
        table.setOrderId(0);
        table.setStatus(Const.TableStatus.EMPTY);
        tableLogic.update(table);
    }
    
    public Menu getCurrentMenu() {
        com.cloudstone.emenu.data.Menu m = menuLogic.getCurrentMenu();
        if (m == null) {
            return null;
        }
        //TODO logo
        Menu menu = new Menu(m.getId(), m.getName(), listMenuPage(m.getId()), "logo");
        
        return menu;
    }
    
    public List<MenuPage> listMenuPage(int menuId) {
        List<MenuPage> ret = new ArrayList<MenuPage>();
        List<Chapter> chapters = menuLogic.listChapterByMenuId(menuId);
        for (Chapter chapter:chapters) {
            List<com.cloudstone.emenu.data.MenuPage> pages =
                    menuLogic.listMenuPageByChapterId(chapter.getId());
            for (com.cloudstone.emenu.data.MenuPage page:pages) {
                List<Dish> dishes = menuLogic.getDishByMenuPageId(page.getId());
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
                        goods.setSoldout(false);//TODO
                        goods.setNumberDecimalPermited(dish.isNonInt());
                        if (!StringUtils.isBlank(dish.getImageId())) {
                            List<Img> imgs = new ArrayList<Img>();
                            Img img = new Img("images/"+dish.getImageId()+"?width=250&height=250",
                                    "images/"+dish.getImageId()+"?width=600&height=450");
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
    
    public List<String> getAllNotes() throws TException {
        List<DishNote> notes = menuLogic.listAllDishNote();
        return DataUtils.pickNames(notes);
    }
    
    public void submitOrder(Order order) throws TableEmptyException, HasInvalidGoodsException,
        UnderMinChargeException, TException {
        //TODO transaction for zhuwei
        String tableName = order.getTableId();
        
        double price = 0;
        for (GoodsOrder o:order.getGoods()) {
            price += o.getPrice()*o.getNumber();
        }
        price = DataUtils.calMoney(price);
        order.setOriginalPrice(price);
        order.setPrice(price);
        
        //check table
        Table table = tableLogic.getByName(tableName);
        if (table == null) {
            throw new TException("table not found");
        }
        if (table.getStatus() == Const.TableStatus.EMPTY) {
            throw new TableEmptyException();
        }
        if (order.getOriginalPrice() < table.getMinCharge()) {
            throw new UnderMinChargeException();
        }
        
        //check goods
        List<Dish> dishes = new ArrayList<Dish>();
        for (GoodsOrder g:order.getGoods()) {
            int dishId = g.getId();
            Dish dish = menuLogic.getDish(dishId, false);
            if (dish == null || dish.isDeleted()) {
                throw new HasInvalidGoodsException();
            }
            dishes.add(dish);
        }
        
        com.cloudstone.emenu.data.Order orderValue = new com.cloudstone.emenu.data.Order();
        orderValue.setOriginPrice(order.getOriginalPrice());
        orderValue.setPrice(order.getPrice());
        orderValue.setTableId(table.getId());
        orderLogic.addOrder(orderValue);
        
        for (int i=0; i<dishes.size(); i++) {
            Dish dish = dishes.get(i);
            GoodsOrder g = order.getGoods().get(i);
            
            OrderDish r = new OrderDish();
            r.setOrderId(orderValue.getId());
            r.setDishId(dish.getId());
            r.setNumber(g.getNumber());
            r.setPrice(g.getPrice());
            if (g.getRemarks() != null) {
                r.setRemarks(g.getRemarks().toArray(new String[0]));
            }
            r.setStatus(ThriftUtils.getOrderDishStatus(g));
            orderLogic.addOrderDish(r);
        }
        table.setOrderId(orderValue.getId());
        tableLogic.update(table);
    }
    
    public Order getOrderByTable(String tableName) throws TableEmptyException, TException {
        
        // check table
        Table table = tableLogic.getByName(tableName);
        if (table == null) {
            throw new TException("table not found, tableName = " + tableName);
        }
        if (table.getStatus() == Const.TableStatus.EMPTY) {
            throw  new TableEmptyException();
        }
        
        int orderId = table.getOrderId();
        com.cloudstone.emenu.data.Order orderValue = orderLogic.getOrder(orderId);
        if (orderValue == null) {
            return null;
        }
        
        Order order = new Order();
        order.setOriginalPrice(orderValue.getOriginPrice());
        order.setPrice(orderValue.getPrice());
        order.setTableId(tableName);
        
        List<GoodsOrder> goods = listGoodsInOrder(orderId);
        order.setGoods(goods);
        
        return order;
    }
    
    public List<GoodsOrder> listGoodsInOrder(int orderId) {
        List<GoodsOrder> goods = new ArrayList<GoodsOrder>();
        List<OrderDish> relations = orderLogic.listOrderDish(orderId);
        for (OrderDish r:relations) {
            Dish dish = menuLogic.getDish(r.getDishId(), false);
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
            g.setCategory(thriftCache.getCategory(g.getId()));
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
    
    public boolean isValidImei(String imei) {
        return thriftCache.isValidImei(imei);
    }
    
    public User getLatestUser(String imei) {
        ThriftSession s = thriftSessionDb.getLatest(imei);
        if (s != null) {
            return s.getUser();
        }
        return null;
    }
    
    public ThriftSession getLatestSession(String imei) {
        return thriftSessionDb.getLatest(imei);
    }
    
    public void submitPadInfo(PadInfo info) {
        String imei = info.getIMEI();
        Pad pad = deviceLogic.getPad(imei);
        pad.setBatteryLevel(info.getBatteryLevel());
        deviceLogic.updatePad(pad);
    }
}
