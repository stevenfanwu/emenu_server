package com.cloudstone.emenu.logic;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.DailyStat;

@Component
public class StatisticsLogic extends BaseLogic {
    
    public DailyStat getStatByTime(long time) {
        
        return new DailyStat();
    }
    
    public DailyStat addStat() {
        return new DailyStat();
    }

}
