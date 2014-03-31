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
import com.cloudstone.emenu.data.DishStat;
import com.cloudstone.emenu.data.GeneralStat;
import com.cloudstone.emenu.data.MenuStat;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.logic.StatisticsLogic;

@Controller
public class StatisticsApiController extends BaseApiController {

    @Autowired
    private StatisticsLogic statisticsLogic;

    @RequestMapping(value = "/api/menu-stats", method = RequestMethod.GET)
    public
    @ResponseBody
    List<MenuStat> getMenuStat(
            @RequestParam(value = "time", defaultValue = "0") long time,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "start", defaultValue = "0") long startTime,
            @RequestParam(value = "end", defaultValue = "0") long endTime,
            HttpServletRequest request) {
        if (time < 0 || page < 0 || startTime < 0
                || endTime < 0 || startTime > endTime) {
            throw new BadRequestError();
        }
        EmenuContext context = newContext(request);
        if (time > 0) {
            return statisticsLogic.listMenuStat(context, time, page);
        } else if (startTime > 0) {
            return statisticsLogic.listMenuStat(context, startTime, endTime);
        } else {
            time = System.currentTimeMillis();
            return statisticsLogic.listMenuStat(context, time, page);
        }
    }

    @RequestMapping(value = "/api/dish-stats", method = RequestMethod.GET)
    public
    @ResponseBody
    List<DishStat> getDishStat(
            @RequestParam(value = "time", defaultValue = "0") long time,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "start", defaultValue = "0") long startTime,
            @RequestParam(value = "end", defaultValue = "0") long endTime,
            HttpServletRequest request) {
        if (time < 0 || page < 0 || startTime < 0
                || endTime < 0 || startTime > endTime) {
            throw new BadRequestError();
        }
        EmenuContext context = newContext(request);
        if (time > 0) {
            return statisticsLogic.listDishStat(context, time, page);
        } else if (startTime > 0) {
            return statisticsLogic.listDishStat(context, startTime, endTime);
        } else {
            time = System.currentTimeMillis();
            return statisticsLogic.listDishStat(context, time, page);
        }
    }

    @RequestMapping(value = "/api/stats", method = RequestMethod.GET)
    public
    @ResponseBody
    List<GeneralStat> getGenStat(
            @RequestParam(value = "time", defaultValue = "0") long time,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "start", defaultValue = "0") long startTime,
            @RequestParam(value = "end", defaultValue = "0") long endTime,
            HttpServletRequest request) {
        if (time < 0 || page < 0 || startTime < 0
                || endTime < 0 || startTime > endTime) {
            throw new BadRequestError();
        }
        EmenuContext context = newContext(request);
        if (time > 0) {
            return statisticsLogic.listGeneralStat(context, time, page);
        } else if (startTime > 0) {
            return statisticsLogic.listGeneralStat(context, startTime, endTime);
        } else {
            time = System.currentTimeMillis();
            return statisticsLogic.listGeneralStat(context, time, page);
        }
    }
}
