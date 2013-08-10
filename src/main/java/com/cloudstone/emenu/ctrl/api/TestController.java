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

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.logic.UserLogic;
import com.cloudstone.emenu.service.IOrderService;
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
    private IOrderService orderService;

    @Autowired
    private SqliteDataSource dataSource;

    @RequestMapping(value = "/api/test", method = RequestMethod.GET)
    public @ResponseBody String test(HttpServletRequest req) {
        User user = userLogic.getUser(1);
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
        Dish dish = new Dish();
        dish.setCreatedTime(199999);
        dish.setUpdateTime(122333333);
        dish.setId(2);
        dish.setName("ahehe");
        dish.setPrice(10d);
        dish.setUnit(1);
        dish.setSpicy(0);
        dish.setDesc("adada");
        dish.setImageId("12");
        dish.setSoldout(false);
        menuLogic.addDish(dish);
        Dish dish2 = menuLogic.updateDishSoldout(1, true);
        return dish2;
    }

    @RequestMapping(value = "/api/testtrans", method = RequestMethod.GET)
    public @ResponseBody Bill testTrans(HttpServletRequest req) {
        Bill bill = new Bill();
        bill.setCost(10.0d);
        long now = System.currentTimeMillis();
        bill.setCreatedTime(now);
        bill.setUpdateTime(now);
        bill.setId(123);
        bill.setOrderId(231);
        bill.setDiscountDishIds(new int[] {
                1, 3, 5
        });
        bill.setDiscount(3);
        bill.setPayType(0);
        bill.setDeleted(false);
        bill.setInvoice(false);
        bill.setRemarks("sa");
        bill.setTip(1d);
        // Start transaction
        DbTransaction trans = dataSource.openTrans();
        trans.begin();
        orderService.addBill(trans, bill);
        trans.commit();
        trans.close();
        return new Bill();
    }
}
