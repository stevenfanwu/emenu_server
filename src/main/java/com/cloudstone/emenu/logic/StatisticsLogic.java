
package com.cloudstone.emenu.logic;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.DailyStat;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.storage.db.IStatDb;
import com.cloudstone.emenu.util.UnitUtils;

@Component
public class StatisticsLogic extends BaseLogic {

    @Autowired
    private IStatDb statDb;
    
    @Autowired
    private OrderLogic orderLogic;
    
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
                dailyStat.setUpdateTime(System.currentTimeMillis());
                statDb.update(dailyStat);
            } else {
                dailyStat.setCreatedTime(System.currentTimeMillis());
                dailyStat.setUpdateTime(System.currentTimeMillis());
                statDb.add(dailyStat);
            }
        } else {
            dailyStat = statDb.get(requestDay);
            if (dailyStat == null) {
                dailyStat = computeDailyStat(time);
                dailyStat.setCreatedTime(System.currentTimeMillis());
                dailyStat.setUpdateTime(System.currentTimeMillis());
                statDb.add(dailyStat);
            }
        }

        return dailyStat;
    }

    private DailyStat computeDailyStat(long time) {
        
        DailyStat dailyStat = new DailyStat();
        //TIME
        dailyStat.setTime(System.currentTimeMillis() / UnitUtils.DAY);
        //INCOME
        List<Bill> bills = orderLogic.listBills();
        int income = 0;
        for(Bill bill : bills) {
            income += bill.getCost();
        }
        dailyStat.setIncome(income);
        return null;
    }
    
}
