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
import com.cloudstone.emenu.data.Bill.BillArchive;
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
    public @ResponseBody
    String test(HttpServletRequest req) {
        User user = userLogic.getUser(1);
        return JsonUtils.toJson(user);
    }
    
    @RequestMapping(value="/test", method=RequestMethod.GET)
    public String print() throws Exception {
        String p = PrinterUtils.listPrinters()[0];
        PrinterUtils.print(p, "");
        return "test";
    }
    

    @RequestMapping(value = "/api/testdb", method = RequestMethod.GET)
    public @ResponseBody
    Bill testPayBill(HttpServletRequest req) {
        Bill bill = new Bill();
        bill.setCost(10.0d);
        long now = System.currentTimeMillis();
        bill.setCreatedTime(now);
        bill.setUpdateTime(now);
        bill.setId(123);
        bill.setOrderId(231);
        bill.setDiscountDishIds(new int[]{1,3,5});
        bill.setDiscount(3);
        bill.setPayType(0);
        bill.setArchive(new BillArchive());
        bill.setDeleted(false);
        bill.setInvoice(false);
        bill.setRemarks("sa");
        bill.setTip(1d);
        // Start transaction
        DbTransaction trans = dataSource.openTrans();
        trans.begin();
        orderService.addBill(trans, bill);
        Bill bill2 = new Bill();
        bill2.setCost(10.0d);
        now = System.currentTimeMillis();
        bill2.setCreatedTime(now);
        bill2.setUpdateTime(now);
        bill2.setId(123);
        bill2.setOrderId(231);
        bill2.setDiscountDishIds(new int[]{1,3,5});
        bill2.setDiscount(3);
        bill2.setPayType(0);
        bill2.setArchive(new BillArchive());
        bill2.setDeleted(false);
        bill2.setInvoice(false);
        orderService.addBill(trans, bill2);
        
        trans.commit();
        return new Bill();
    }
}
