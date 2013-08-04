/**
 * @(#)DeviceApiController.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.Pad;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class DeviceApiController extends BaseApiController {

    @RequestMapping(value="/api/pads/{id:[\\d]+}", method=RequestMethod.GET)
    public @ResponseBody Pad getPad(@PathVariable("id") int id) {
        return deviceLogic.getPad(id);
    }

    @RequestMapping(value="/api/pads/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void deletePad(@PathVariable("id") int id, HttpServletResponse resp) {
        deviceLogic.deletePad(id);
    }

    @RequestMapping(value="/api/pads/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody Pad updatePad(@PathVariable("id") int id, @RequestBody String body) {
        Pad pad = JsonUtils.fromJson(body, Pad.class);
        if (id != pad.getId()) {
            throw new BadRequestError();
        }
        return deviceLogic.updatePad(pad);
    }
    
    @RequestMapping(value="/api/pads", method=RequestMethod.POST)
    public @ResponseBody Pad updatePad(@RequestBody String body) {
        Pad pad = JsonUtils.fromJson(body, Pad.class);
        return deviceLogic.addPad(pad);
    }
    
    @RequestMapping(value="/api/pads", method=RequestMethod.GET)
    public @ResponseBody List<Pad> listAllPad() {
        return deviceLogic.listAllPad();
    }
}
