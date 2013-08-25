
package com.cloudstone.emenu.logic;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.GeneralStat;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IGenStatDb;
import com.cloudstone.emenu.util.UnitUtils;

@Component
public class StatisticsLogic extends BaseLogic {
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsLogic.class);
    
    private static final int PAGE_COUNT = 15;

    @Autowired
    private IGenStatDb genStatDb;

    @Autowired
    private OrderLogic orderLogic;

    @Autowired
    private TableLogic tableLogic;

    public List<GeneralStat> listGeneralStat(EmenuContext context, long time, int page) {
        long endTime = UnitUtils.getDayStart(time - page*PAGE_COUNT*UnitUtils.DAY);
        long startTime = endTime - PAGE_COUNT * UnitUtils.DAY;
        return listGeneralStat(context, startTime, endTime);
    }

    public List<GeneralStat> listGeneralStat(EmenuContext context, long startTime, long endTime) {
        List<GeneralStat> ret = new LinkedList<GeneralStat>();
        long p = startTime;
        if ((p-8*UnitUtils.HOUR) % UnitUtils.DAY != 0) {
            p = UnitUtils.getDayStart(p + UnitUtils.DAY);
        }
        if (p != startTime) {
            ret.add(getDailyStat(context, startTime, p));
        }
        while (p < endTime) {
            long end = p + UnitUtils.DAY;
            if (end > endTime) {
                end = endTime;
            }
            ret.add(getDailyStat(context, p, end));
            p += UnitUtils.DAY;
        }
        for (int i=0; i<ret.size(); i++) {
            GeneralStat stat = ret.get(i);
            if (stat.getId() == 0) {
                //mock id
                stat.setId(0 - i);
            }
        }
        return ret;
    }

    public GeneralStat getDailyStat(EmenuContext context, long startTime, long endTime) {
        GeneralStat stat = null;
        long today = UnitUtils.getDayStart(System.currentTimeMillis());
        if (endTime - startTime == UnitUtils.DAY && endTime<=today) {
            long day = UnitUtils.getDayByMillis((startTime+endTime)/2);
            stat = genStatDb.get(context, day);
            if (stat == null) {
                stat = computeDailyStat(context, startTime);
                long now = System.currentTimeMillis();
                stat.setCreatedTime(now);
                stat.setUpdateTime(now);
                genStatDb.add(context, stat);
            }
        } else {
            stat = computeDailyStat(context, startTime, endTime);
        }
        return stat;
    }

    private GeneralStat computeDailyStat(EmenuContext context, long time) {
        long startTime = UnitUtils.getDayStart(time);
        long endTime = startTime + UnitUtils.DAY;
        return computeDailyStat(context, startTime, endTime);
    }

    private GeneralStat computeDailyStat(EmenuContext context, long startTime, long endTime) {
        LOG.info("computeDailyStat");
        LOG.info("startTime = " + startTime);
        LOG.info("endTime = " + endTime);

        GeneralStat genStat = new GeneralStat();

        // TIME
        genStat.setDay(UnitUtils.getDayByMillis((startTime+endTime)/2));

        List<Bill> bills = orderLogic.getBills(context, startTime, endTime);
        LOG.info("bills.size = " + bills.size());
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
                invoiceAmount += bill.getInvoicePrice();
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
        int tableCount = tableLogic.tableCount(context);
        double rate = 0;
        if (0 != tableCount) {
            rate = 100.0 * bills.size() / tableCount;
        }
        genStat.setTableRate(rate);

        // AVE_PERSON_PRICE
        if (customers == 0) {
            genStat.setAvePerson(0);
        } else {
            genStat.setAvePerson(income / customers);
        }

        // AVE_ORDER_PRICE
        if (bills.size() == 0) {
            genStat.setAveOrder(0);
        } else {
            genStat.setAveOrder(income / bills.size());
        }
        return genStat;
    }

}
