
package com.cloudstone.emenu.logic;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.GeneralStat;
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

    public List<GeneralStat> listGeneralStat(EmenuContext context, long time, int page) {
        List<GeneralStat> list = new LinkedList<GeneralStat>();
        Order oldest = orderLogic.getOldestOrder(context);
        if (oldest == null) {
            return list;
        }
        long p = time - page * UnitUtils.DAY;
        long endTime = UnitUtils.getDayByMillis(oldest.getCreatedTime()) * UnitUtils.DAY;
        for (int i = 0; i < PAGE_COUNT && p >= endTime; i++, p -= UnitUtils.DAY) {
            list.add(getGeneralStat(context, p));
        }
        return list;
    }

    public GeneralStat getGeneralStat(EmenuContext context, long time) {
        if (time <= 0)
            throw new BadRequestError();

        long requestDay = UnitUtils.getDayByMillis(time);

        GeneralStat genStat = null;
        long currentDay = UnitUtils.getDayByMillis(System.currentTimeMillis());
        if (requestDay == currentDay) {
            genStat = computeStat(context, time);
            genStat.setCreatedTime(System.currentTimeMillis());
            genStat.setUpdateTime(System.currentTimeMillis());
            genStat.setId(0);
        } else {
            genStat = genStatDb.get(context, requestDay);
            if (genStat == null) {
                genStat = computeStat(context, time);
                genStat.setCreatedTime(System.currentTimeMillis());
                genStat.setUpdateTime(System.currentTimeMillis());
                genStatDb.add(context, genStat);
            }
        }

        return genStat;
    }

    public GeneralStat getGeneralStat(EmenuContext context, long startTime, long endTime) {

        long requestDay = UnitUtils.getDayByMillis(startTime);

        GeneralStat genStat = null;
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

    private GeneralStat computeStat(EmenuContext context, long time) {
        return computeStat(context, time, time);
    }

    private GeneralStat computeStat(EmenuContext context, long startTime, long endTime) {

        GeneralStat genStat = new GeneralStat();

        // TIME
        genStat.setDay(startTime / UnitUtils.DAY);

        List<Bill> bills = orderLogic.getBills(context, startTime, endTime);
        double income = 0;
        int customers = 0;
        int invoices = 0;
        double invoiceAmount = 0;
        double tips = 0;
        for (Bill bill : bills) {
            if (bill.getOrder() == null)
                throw new ServerError("bill.getOrder is null!");
            customers += bill.getOrder().getCustomerNumber();
            if (bill.isInvoice()) {
                invoices++;
                invoiceAmount += bill.getCost();
            }
            income += bill.getCost();
            tips += bill.getTip();
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
        genStat.setTips(tips);

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
