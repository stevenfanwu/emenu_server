
package com.cloudstone.emenu.logic;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.GenStat;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IGenStatDb;
import com.cloudstone.emenu.util.UnitUtils;

@Component
public class StatisticsLogic extends BaseLogic {
    private static final int PAGE_COUNT = 15;

    @Autowired
    private IGenStatDb genStatDb;

    @Autowired
    private OrderLogic orderLogic;

    @Autowired
    private TableLogic tableLogic;

    public List<GenStat> listGenStat(EmenuContext context, long time, int page) {
        List<GenStat> list = new LinkedList<GenStat>();
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

    public GenStat getStat(EmenuContext context, long time) {
        if (time <= 0)
            throw new BadRequestError();

        long requestDay = UnitUtils.getDayByMillis(time);

        GenStat dailyStat = null;
        long currentDay = UnitUtils.getDayByMillis(System.currentTimeMillis());
        if (requestDay == currentDay) {
            dailyStat = computeStat(context, time);
            dailyStat.setCreatedTime(System.currentTimeMillis());
            dailyStat.setUpdateTime(System.currentTimeMillis());
            dailyStat.setId(0);
        } else {
            dailyStat = genStatDb.get(context, requestDay);
            if (dailyStat == null) {
                dailyStat = computeStat(context, time);
                dailyStat.setCreatedTime(System.currentTimeMillis());
                dailyStat.setUpdateTime(System.currentTimeMillis());
                genStatDb.add(context, dailyStat);
            }
        }

        return dailyStat;
    }

    public GenStat getStat(EmenuContext context, long startTime, long endTime) {

        long requestDay = UnitUtils.getDayByMillis(startTime);

        GenStat genStat = null;
        long currentDay = UnitUtils.getDayByMillis(System.currentTimeMillis());
        if (requestDay == currentDay) {
            genStat = computeStat(context, startTime, endTime);
            genStat.setCreatedTime(System.currentTimeMillis());
            genStat.setUpdateTime(System.currentTimeMillis());
            genStat.setId(0);
        } else {
            throw new BadRequestError();
        }
        return genStat;
    }

    private GenStat computeStat(EmenuContext context, long time) {
        return computeStat(context, time, time);
    }

    private GenStat computeStat(EmenuContext context, long startTime, long endTime) {

        GenStat genStat = new GenStat();

        // TIME
        genStat.setDay(startTime / UnitUtils.DAY);

        List<Bill> bills = orderLogic.getBills(context, startTime, endTime);
        double income = 0;
        int customers = 0;
        int invoices = 0;
        double invoiceAmount = 0;
        double serviceAmount = 0;
        for (Bill bill : bills) {
            if (bill.getOrder() == null)
                throw new ServerError("bill.getOrder is null!");
            customers += bill.getOrder().getCustomerNumber();
            if (bill.isInvoice()) {
                invoices++;
                invoiceAmount += bill.getCost();
            }
            income += bill.getCost();
            serviceAmount += bill.getTip();
        }

        // COUNT
        genStat.setCount(bills.size());
        // INCOME
        genStat.setIncome(income);
        // CUSTOMER_COUNT
        genStat.setCustomerCount(customers);
        // INVOICE_COUNT
        genStat.setInvoiceCount(invoices);
        // INVOICE_AMOUNT
        genStat.setInvoiceAmount(invoiceAmount);
        // SERVICE_AMOUNT
        genStat.setTips(serviceAmount);

        // TABLERATE
        int totalTables = tableLogic.getAll(context).size();
        double rate = 0;
        if (0 != totalTables) {
            rate = 100.0 * bills.size() / totalTables;
        }
        genStat.setTableRate(rate);

        // AVE_PERSON_PRICE
        genStat.setAvePersonPrice(income / customers);

        // AVE_ORDER_PRICE
        genStat.setAveOrderPrice(income / bills.size());
        return genStat;
    }

}
