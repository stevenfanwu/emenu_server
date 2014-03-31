/**
 * @(#)UserThriftController.java, Jun 1, 2013. 
 *
 */
package com.cloudstone.emenu.ctrl.thrift;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.cloudstone.menu.server.thrift.api.IPadInfoService;
import cn.com.cloudstone.menu.server.thrift.api.PadInfo;

import com.cloudstone.emenu.EmenuContext;

/**
 * @author xuhongfeng
 */
@Controller
public class PadThriftController extends BaseThriftController {
    private static final Logger LOG = LoggerFactory.getLogger(PadThriftController.class);

    @Override
    protected TProcessor getProcessor() {
        return processor;
    }

    @RequestMapping(value = "/padinfoservice.thrift", method = RequestMethod.POST)
    public void thrift(HttpServletRequest request,
                       HttpServletResponse response) throws IOException, TException {
        process(request, response);
    }

    private class Service implements IPadInfoService.Iface {

        @Override
        public boolean submitPadInfo(PadInfo info) throws TException {
            EmenuContext context = new EmenuContext();
            context.setRestaurantId(info.getRestaurentId());
            thriftLogic.submitPadInfo(context, info);
            return true;
        }
    }

    private IPadInfoService.Processor<Service> processor =
            new IPadInfoService.Processor<PadThriftController.Service>(new Service());
}
