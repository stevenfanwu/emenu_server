/**
 * @(#)TestController.java, Aug 4, 2013. 
 * 
 */

package com.cloudstone.emenu.ctrl.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.logic.UserLogic;
import com.cloudstone.emenu.storage.db.IBillDb;
import com.cloudstone.emenu.storage.db.util.DbTransaction;
import com.cloudstone.emenu.storage.db.util.SqliteDataSource;
import com.cloudstone.emenu.util.JsonUtils;
import com.cloudstone.emenu.util.PrinterUtils;

/**
 * just for test
 * 
 * @author xuhongfeng
 */
@Controller
public class TestController extends BaseApiController {
    @Autowired
    private UserLogic userLogic;

    @Autowired
    private IBillDb billDb;

    @Autowired
    private SqliteDataSource dataSource;

    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public @ResponseBody String test(HttpServletRequest req) {
        EmenuContext context = newContext(req);
        User user = userLogic.getUser(context, 1);
        return JsonUtils.toJson(user);
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String print() throws Exception {
        String p = PrinterUtils.listPrinters()[0];
        PrinterUtils.print(p, "");
        return "test";
    }

    @RequestMapping(value = "/api/testdish", method = RequestMethod.GET)
    public @ResponseBody Dish testDish(HttpServletRequest req) {
        EmenuContext context = newContext(req);
        Dish dish = new Dish();
        dish.setCreatedTime(199999);
        dish.setUpdateTime(122333333);
        dish.setId(1);
        dish.setName("ahehe");
        dish.setPrice(10d);
        dish.setUnit(1);
        dish.setSpicy(0);
        dish.setDesc("adada");
        dish.setImageId("12");
        dish.setSoldout(false);
        menuLogic.addDish(context, dish);
        Dish dish2 = menuLogic.updateDishSoldout(context, 1, true);
        return dish2;
    }

    @RequestMapping(value = "/api/testorder", method = RequestMethod.GET)
    public @ResponseBody OrderVO testOrder(HttpServletRequest req) {
        EmenuContext context = newContext(req);
        Order order = new Order();
        order.setCreatedTime(System.currentTimeMillis());
        order.setUpdateTime(System.currentTimeMillis());
        order.setPrice(152.2d);
        order.setOriginPrice(152.2d);
        order.setId(1);
        order.setDeleted(false);
        order.setTableId(1);
        orderLogic.addOrder(context, order);
        
        Table table = new Table();
        table.setCapacity(2);
        table.setId(1);
        table.setMinCharge(100.0d);
        table.setName("aa");
        table.setOrderId(1);
        table.setStatus(0);
        table.setTip(10.0d);
        tableLogic.add(context, table);
        
        return orderWraper.wrap(context, orderLogic.getOrder(context, 1));
    }
    
    
    @RequestMapping(value = "/api/testtrans", method = RequestMethod.GET)
    public @ResponseBody Bill testTrans(HttpServletRequest req) {
        EmenuContext context = newContext(req);
        Bill bill = new Bill();
        bill.setCost(152.2d);
        long now = System.currentTimeMillis();
        bill.setCreatedTime(now);
        bill.setUpdateTime(now);
        bill.setId(2);
        bill.setOrderId(1);
        bill.setDiscountDishIds(new int[] {
                1, 2
        });
        bill.setDiscount(2);
        bill.setPayType(0);
        bill.setDeleted(false);
        bill.setInvoice(false);
        bill.setRemarks("sa");
        bill.setTip(1.8d);
        bill.setOrder(orderWraper.wrap(context, orderLogic.getOrder(context, 1)));
        // Start transaction
        DbTransaction trans = dataSource.openTrans();
        trans.begin();
        billDb.add(context, bill);
        trans.commit();
        trans.close();
        return billDb.get(context, 2);
    }
}
