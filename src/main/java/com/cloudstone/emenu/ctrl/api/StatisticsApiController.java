package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DailyStat;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.logic.StatisticsLogic;

@Controller
public class StatisticsApiController extends BaseApiController {

    @Autowired
    private StatisticsLogic statisticsLogic;

    @RequestMapping(value="/api/stats", method=RequestMethod.GET)
    public @ResponseBody List<DailyStat> getDailyStat(
            @RequestParam(value="time", defaultValue="0") long time,
            @RequestParam(value="page", defaultValue="0") int page,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        if (time == 0) {
            time = System.currentTimeMillis();
        }
        return statisticsLogic.listDailyStat(context, time, page);
    }

    @RequestMapping(value="/api/stats", method=RequestMethod.GET)
    public @ResponseBody DailyStat getTimeIntervalStat(
            @RequestParam(value="start") long startTime,
            @RequestParam(value="end") long endTime,
            HttpServletRequest request) {
        EmenuContext context = newContext(request);
        if (startTime <=0 || endTime <= 0 || startTime > endTime) {
            throw new BadRequestError();
        }
        return statisticsLogic.getStat(context, startTime, endTime);
    }
}
