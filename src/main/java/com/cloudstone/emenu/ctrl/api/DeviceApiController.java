/**
 * @(#)DeviceApiController.java, Aug 4, 2013. 
 *
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Pad;
import com.cloudstone.emenu.data.ThriftSession;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.logic.ThriftLogic;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 */
@Controller
public class DeviceApiController extends BaseApiController {
    @Autowired
    private ThriftLogic thriftLogic;

    @RequestMapping(value = "/api/pads/{id:[\\d]+}", method = RequestMethod.GET)
    public
    @ResponseBody
    Pad getPad(@PathVariable("id") int id, HttpServletRequest req) {
        EmenuContext context = newContext(req);
        return deviceLogic.getPad(context, id);
    }

    @RequestMapping(value = "/api/pads/{id:[\\d]+}", method = RequestMethod.DELETE)
    public void deletePad(@PathVariable("id") int id,
                          HttpServletRequest request,
                          HttpServletResponse resp) {
        EmenuContext context = newContext(request);
        deviceLogic.deletePad(context, id);
    }

    @RequestMapping(value = "/api/pads/{id:[\\d]+}", method = RequestMethod.PUT)
    public
    @ResponseBody
    Pad updatePad(@PathVariable("id") int id,
                  HttpServletRequest request,
                  @RequestBody String body) {
        EmenuContext context = newContext(request);
        Pad pad = JsonUtils.fromJson(body, Pad.class);
        if (id != pad.getId()) {
            throw new BadRequestError();
        }
        return deviceLogic.updatePad(context, pad);
    }

    @RequestMapping(value = "/api/pads", method = RequestMethod.POST)
    public
    @ResponseBody
    Pad updatePad(
            HttpServletRequest request,
            @RequestBody String body) {
        EmenuContext context = newContext(request);
        Pad pad = JsonUtils.fromJson(body, Pad.class);
        return deviceLogic.addPad(context, pad);
    }

    @RequestMapping(value = "/api/pads", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Pad> listAllPad(HttpServletRequest request) {
        EmenuContext context = newContext(request);
        List<Pad> pads = deviceLogic.listAllPad(context);
        for (Pad pad : pads) {
            ThriftSession session = thriftLogic.getLatestSession(context, pad.getImei());
            if (session != null && session.getUser() != null) {
                session.setUser(userLogic.getUser(context, session.getUser().getId()));
            }
            pad.setSession(session);
        }
        return pads;
    }
}
