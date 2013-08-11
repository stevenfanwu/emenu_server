
package com.cloudstone.emenu.logic;

import javax.print.attribute.standard.Severity;

import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.DailyStat;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IStatDb;

@Component
public class StatisticsLogic extends BaseLogic {

    @Autowired
    private IStatDb statDb;
    
    public DailyStat getStatByTime(long time) {
        if (time <= 0)
            throw new BadRequestError();

        long requestDay = (long) (time / 1000 / 60 / 60 / 24);

        DailyStat dailyStat = null;
        long currentDay = System.currentTimeMillis();
        if (requestDay == currentDay) {
            // TODO calculate dailystat & insert
            dailyStat = new DailyStat();

            try {
                DailyStat oldStat = statDb.get(requestDay);
                if(oldStat != null) {
                    statDb.update(dailyStat);
                } else {
                    statDb.add(dailyStat);
                }
            } catch (SQLiteException e) {
                throw new ServerError(e);
            }
        } else {
            try {
                dailyStat = statDb.get(requestDay);
            } catch (SQLiteException e) {
                throw new ServerError(e);
            }
        }

        return dailyStat;
    }

    public DailyStat addStat() {
        return new DailyStat();
    }

}
