
package com.cloudstone.emenu.logic;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.DailyStat;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.storage.db.IStatDb;

@Component
public class StatisticsLogic extends BaseLogic {

    @Autowired
    private IStatDb statDb;
    
    public DailyStat getStatByTime(long time) {
        if (time <= 0)
            throw new BadRequestError();
        //long offset = Calendar.getInstance().getTimeZone().getRawOffset();

        long requestDay = (long) (time / 1000 / 60 / 60 / 24);

        DailyStat dailyStat = null;
        long currentDay = System.currentTimeMillis();
        if (requestDay == currentDay) {
            dailyStat = computeDailyStat(time);
            DailyStat oldStat = statDb.get(requestDay);
            if (oldStat != null) {
                statDb.update(dailyStat);
            } else {
                statDb.add(dailyStat);
            }
        } else {
            dailyStat = statDb.get(requestDay);
            if (dailyStat == null) {
                dailyStat = computeDailyStat(time);
                statDb.add(dailyStat);
            }
        }

        return dailyStat;
    }

    private DailyStat computeDailyStat(long time) {
        
        return null;
    }
    
}
