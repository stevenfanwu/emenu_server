/**
 * @(#)ThriftLogic.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import cn.com.cloudstone.menu.server.thrift.api.Goods;
import cn.com.cloudstone.menu.server.thrift.api.Img;
import cn.com.cloudstone.menu.server.thrift.api.Menu;
import cn.com.cloudstone.menu.server.thrift.api.MenuPage;
import cn.com.cloudstone.menu.server.thrift.api.TableInfo;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Table;
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
                        goods.setCategory(chapter.getName());
                        goods.setPrice(dish.getPrice());
                        goods.setIntroduction(dish.getDesc());
                        goods.setCategory(dish.getDishTag());
                        goods.setOnSales(dish.getMemberPrice()!=0 && dish.getMemberPrice()<dish.getPrice());
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
}
