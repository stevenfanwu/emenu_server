package com.cloudstone.emenu.ctrl.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DailyStat;
import com.cloudstone.emenu.logic.StatisticsLogic;

@Controller
public class StatisticsApiController extends BaseApiController {

    @Autowired
    private StatisticsLogic statisticsLogic;

    @RequestMapping(value="/api/stat", method=RequestMethod.GET)
    public @ResponseBody DailyStat getDailyStat(
            @RequestParam("time") long time,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        return statisticsLogic.getDailyStat(context, time);
    }
}
