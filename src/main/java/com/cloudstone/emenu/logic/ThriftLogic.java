/**
 * @(#)ThriftLogic.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

import cn.com.cloudstone.menu.server.thrift.api.Goods;
import cn.com.cloudstone.menu.server.thrift.api.GoodsOrder;
import cn.com.cloudstone.menu.server.thrift.api.HasInvalidGoodsException;
import cn.com.cloudstone.menu.server.thrift.api.Img;
import cn.com.cloudstone.menu.server.thrift.api.Menu;
import cn.com.cloudstone.menu.server.thrift.api.MenuPage;
import cn.com.cloudstone.menu.server.thrift.api.Order;
import cn.com.cloudstone.menu.server.thrift.api.TableEmptyException;
import cn.com.cloudstone.menu.server.thrift.api.TableInfo;
import cn.com.cloudstone.menu.server.thrift.api.UnderMinChargeException;

import com.cloudstone.emenu.constant.Const.TableStatus;
import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.OrderDish;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.util.CollectionUtils;
import com.cloudstone.emenu.util.StringUtils;
import com.cloudstone.emenu.util.ThriftUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class ThriftLogic extends BaseLogic {

    public TableInfo getTableInfo(String tableId) {
        String tableName = tableId;
        Table table = tableService.getByName(tableName);
        return ThriftUtils.toTableInfo(table);
    }
    
    public Menu getCurrentMenu() {
        com.cloudstone.emenu.data.Menu m = menuService.getAllMenu().get(0);
        if (m == null) {
            return null;
        }
        //TODO logo
        Menu menu = new Menu(m.getId(), m.getName(), listMenuPage(m.getId()), "logo");
        
        return menu;
    }
    
    public List<MenuPage> listMenuPage(int menuId) {
        List<MenuPage> ret = new ArrayList<MenuPage>();
        List<Chapter> chapters = menuService.listChapterByMenuId(menuId);
        for (Chapter chapter:chapters) {
            List<com.cloudstone.emenu.data.MenuPage> pages =
                    menuService.listMenuPageByChapterId(chapter.getId());
            for (com.cloudstone.emenu.data.MenuPage page:pages) {
                List<Dish> dishes = menuService.getDishByMenuPageId(page.getId());
                List<Goods> goodsList = new ArrayList<Goods>();
                for (Dish dish:dishes) {
                    if (dish.getId() >= 0) {//<0 means Null Dish
                        Goods goods = new Goods();
                        goods.setId(dish.getId());
                        goods.setName(dish.getName());
                        goods.setShortName(dish.getName());
                        goods.setPrice(dish.getPrice());
                        goods.setIntroduction(dish.getDesc());
                        goods.setCategory(dish.getDishTag());
                        //TODO
//                        goods.setOnSales(dish.getMemberPrice()!=0 && dish.getMemberPrice()<dish.getPrice());
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
    
    public void submitOrder(Order order) throws TableEmptyException, HasInvalidGoodsException,
        UnderMinChargeException, TException {
        String tableName = order.getTableId();
        
        //check table
        Table table = tableService.getByName(tableName);
        if (table == null) {
            throw new TException("table not found");
        }
        if (table.getStatus() == TableStatus.EMPTY) {
            throw new TableEmptyException();
        }
        if (order.getPrice() < table.getMinCharge()) {
            throw new UnderMinChargeException();
        }
        
        //check goods
        List<Dish> dishes = new ArrayList<Dish>();
        for (GoodsOrder g:order.getGoods()) {
            int dishId = g.getId();
            Dish dish = menuService.getDish(dishId);
            if (dish == null) {
                throw new HasInvalidGoodsException();
            }
            dishes.add(dish);
        }
        
        com.cloudstone.emenu.data.Order orderValue = new com.cloudstone.emenu.data.Order();
        orderValue.setOriginPrice(order.getOriginalPrice());
        orderValue.setPrice(order.getPrice());
        orderValue.setTableId(table.getId());
        orderService.addOrder(orderValue);
        
        List<OrderDish> relations = new ArrayList<OrderDish>(dishes.size());
        for (int i=0; i<dishes.size(); i++) {
            Dish dish = dishes.get(i);
            GoodsOrder g = order.getGoods().get(i);
            
            OrderDish r = new OrderDish();
            r.setDishId(dish.getId());
            r.setOrderId(orderValue.getId());
            r.setNumber(g.getNumber());
            r.setPrice(g.getPrice());
            if (g.getRemarks() != null) {
                r.setRemarks(g.getRemarks().toArray(new String[0]));
            }
            r.setStatus(ThriftUtils.getOrderDishStatus(g));
            
            relations.add(r);
        }
        for (OrderDish r:relations) {
            orderService.addOrderDish(r);
        }
        
        //update table
        table.setOrderId(orderValue.getId());
        tableService.update(table);
    }
    
    public List<GoodsOrder> listGoodsInOrder(int orderId) {
        List<GoodsOrder> goods = new ArrayList<GoodsOrder>();
        List<OrderDish> relations = orderService.listOrderDish(orderId);
        for (OrderDish r:relations) {
            Dish dish = menuService.getDish(r.getDishId());
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
            g.setCategory(dish.getDishTag());
            g.setOrderid(orderId);
            //TODO
//            g.setOnSales(onSales)
//            g.setGoodstate(goodstate);
            goods.add(g);
        }
        return goods;
    }
}
