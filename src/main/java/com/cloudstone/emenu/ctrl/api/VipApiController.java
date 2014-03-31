package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Vip;
import com.cloudstone.emenu.data.VipHistory;
import com.cloudstone.emenu.logic.VipLogic;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author carelife
 */
@Controller
public class VipApiController extends BaseApiController {
    private static final Logger LOG = LoggerFactory.getLogger(VipApiController.class);

    @Autowired
    private VipLogic vipLogic;

    @RequestMapping(value = "/api/vips", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Vip> getAll(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return vipLogic.getAll(context);
    }

    @RequestMapping(value = "/api/vips/{id:[\\d]+}", method = RequestMethod.GET)
    public
    @ResponseBody
    Vip get(@PathVariable(value = "id") int id,
            HttpServletRequest req,
            HttpServletResponse response) {
        EmenuContext context = newContext(req);
        return vipLogic.get(context, id);
    }

    @RequestMapping(value = "/api/vips/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(value = "id") int vipid,
                       HttpServletRequest request,
                       HttpServletResponse response) {
        EmenuContext context = newContext(request);
        vipLogic.delete(context, vipid);
    }

    @RequestMapping(value = "/api/vips/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Vip update(@PathVariable(value = "id") int vipid,
               @RequestBody String body,
               HttpServletRequest request,
               HttpServletResponse response) {
        EmenuContext context = newContext(request);
        Vip vip = JsonUtils.fromJson(body, Vip.class);
        if (vip.getId() != vipid) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return vipLogic.update(context, vip);
    }

    @RequestMapping(value = "/api/vips", method = RequestMethod.POST)
    public
    @ResponseBody
    Vip add(@RequestBody String body,
            HttpServletRequest request,
            HttpServletResponse response) {
        EmenuContext context = newContext(request);
        Vip vip = JsonUtils.fromJson(body, Vip.class);
        vip = vipLogic.add(context, vip);
        sendSuccess(response, HttpServletResponse.SC_CREATED);
        return vip;
    }

    @RequestMapping(value = "/api/vips/{id:[\\d]+}/recharge", method = RequestMethod.PUT)
    public double recharge(@PathVariable(value = "id") int vipid,
                           @RequestParam(value = "money") double money,
                           HttpServletRequest req, HttpServletResponse resp) {
        EmenuContext context = newContext(req);
        return vipLogic.recharge(context, vipid, money);
    }

    @RequestMapping(value = "/api/public/vip-names", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getVipNames(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return vipLogic.listVipNames(context);
    }

    //---------------HISTORY-----------------//
    @RequestMapping(value = "/api/viphistory", method = RequestMethod.POST)
    public void addVipHistory(@RequestBody String body,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        EmenuContext context = newContext(request);
        VipHistory vipHistory = JsonUtils.fromJson(body, VipHistory.class);
        vipLogic.addVipHistory(context, vipHistory);
        sendSuccess(response, HttpServletResponse.SC_CREATED);
    }

    @RequestMapping(value = "/api/viphistory", method = RequestMethod.GET)
    public
    @ResponseBody
    List<VipHistory> getAllHistory(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return vipLogic.getAllHistory(context);
    }

//    @RequestMapping(value="/api/viphistory/{id:[\\d]+}", method=RequestMethod.GET)
//    public @ResponseBody List<VipHistory> getHistoryByVipId(@PathVariable(value="id") int id,
//            HttpServletRequest req,
//            HttpServletResponse response) {
//        EmenuContext context = newContext(req);
//        return vipLogic.getHistoryByVipId(context, id);
//    }
}
