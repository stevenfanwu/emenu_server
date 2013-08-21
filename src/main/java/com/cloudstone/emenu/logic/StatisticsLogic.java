
package com.cloudstone.emenu.logic;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.DailyStat;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IStatDb;
import com.cloudstone.emenu.util.UnitUtils;

@Component
public class StatisticsLogic extends BaseLogic {
    private static final int PAGE_COUNT = 15;

    @Autowired
    private IStatDb statDb;

    @Autowired
    private OrderLogic orderLogic;

    @Autowired
    private TableLogic tableLogic;

    public List<DailyStat> listDailyStat(EmenuContext context, long time, int page) {
        List<DailyStat> list = new LinkedList<DailyStat>();
        Order oldest = orderLogic.getOldestOrder(context);
        if (oldest == null) {
            return list;
        }
        long p = time - page * UnitUtils.DAY;
        long endTime = UnitUtils.getDayByMillis(oldest.getCreatedTime()) * UnitUtils.DAY;
        for (int i = 0; i < PAGE_COUNT && p >= endTime; i++, p -= UnitUtils.DAY) {
            list.add(getStat(context, p));
        }
        return list;
    }

    public DailyStat getStat(EmenuContext context, long time) {
        if (time <= 0)
            throw new BadRequestError();

        long requestDay = UnitUtils.getDayByMillis(time);

        DailyStat dailyStat = null;
        long currentDay = UnitUtils.getDayByMillis(System.currentTimeMillis());
        if (requestDay == currentDay) {
            dailyStat = computeStat(context, time);
            dailyStat.setCreatedTime(System.currentTimeMillis());
            dailyStat.setUpdateTime(System.currentTimeMillis());
            dailyStat.setId(0);
        } else {
            dailyStat = statDb.get(context, requestDay);
            if (dailyStat == null) {
                dailyStat = computeStat(context, time);
                dailyStat.setCreatedTime(System.currentTimeMillis());
                dailyStat.setUpdateTime(System.currentTimeMillis());
                statDb.add(context, dailyStat);
            }
        }

        return dailyStat;
    }

    public DailyStat getStat(EmenuContext context, long startTime, long endTime) {

        long requestDay = UnitUtils.getDayByMillis(startTime);

        DailyStat dailyStat = null;
        long currentDay = UnitUtils.getDayByMillis(System.currentTimeMillis());
        if (requestDay == currentDay) {
            dailyStat = computeStat(context, startTime, endTime);
            dailyStat.setCreatedTime(System.currentTimeMillis());
            dailyStat.setUpdateTime(System.currentTimeMillis());
            dailyStat.setId(0);
        } else {
            throw new BadRequestError();
        }
        return dailyStat;
    }

    private DailyStat computeStat(EmenuContext context, long time) {
        return computeStat(context, time, time);
    }

    private DailyStat computeStat(EmenuContext context, long startTime, long endTime) {

        DailyStat dailyStat = new DailyStat();
        // TIME
        dailyStat.setDay(startTime / UnitUtils.DAY);
        // INCOME
        List<Bill> bills = orderLogic.getBills(context, startTime, endTime);
        double income = 0;
        for (Bill bill : bills) {
            income += bill.getCost();
        }
        dailyStat.setIncome(income);
        // TABLECOUNT
        dailyStat.setTableCount(bills.size());
        // CUSTOMERCOUNT
        int customers = 0;
        for (Bill bill : bills) {
            if (bill.getOrder() == null)
                throw new ServerError("bill.getOrder is null!");
            customers += bill.getOrder().getCustomerNumber();
        }
        dailyStat.setCustomerCount(customers);
        // TABLERATE
        int totalTables = tableLogic.getAll(context).size();
        double rate = 0;
        if (0 != totalTables) {
            rate = 100.0 * bills.size() / totalTables;
        }
        dailyStat.setTableRate(rate);
        return dailyStat;
    }

}
