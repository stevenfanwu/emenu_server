package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.DailyStat;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.logic.StatisticsLogic;

@Controller
public class StatisticsApiController extends BaseApiController {
    @Autowired
    private StatisticsLogic statisticsLogic;
    
    @RequestMapping(value="/api/stat", method=RequestMethod.GET)
    public @ResponseBody DailyStat getChapterByMenuId(@RequestParam("time") int timeStamp) {
        return statisticsLogic.getStatByTime(timeStamp);
    }
}
