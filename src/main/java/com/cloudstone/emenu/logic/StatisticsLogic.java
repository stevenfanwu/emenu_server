
package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.BaseStat;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.DishStat;
import com.cloudstone.emenu.data.GeneralStat;
import com.cloudstone.emenu.data.MenuStat;
import com.cloudstone.emenu.data.vo.OrderDishVO;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IDishStatDb;
import com.cloudstone.emenu.storage.db.IGenStatDb;
import com.cloudstone.emenu.storage.db.IMenuStatDb;
import com.cloudstone.emenu.util.UnitUtils;

@Component
public class StatisticsLogic extends BaseLogic {
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsLogic.class);
    
    private static final int PAGE_COUNT = 15;

    @Autowired
    private IGenStatDb genStatDb;
    @Autowired
    private IDishStatDb dishStatDb;
    @Autowired
    private IMenuStatDb menuStatDb;

    @Autowired
    private OrderLogic orderLogic;
    @Autowired
    private TableLogic tableLogic;
    @Autowired
    private RecordLogic recordLogic;
    @Autowired
    private MenuLogic menuLogic;

    public List<GeneralStat> listGeneralStat(EmenuContext context, long time, int page) {
        return new GenStatGetter(context).list(time, page);
    }

    public List<GeneralStat> listGeneralStat(EmenuContext context, long startTime, long endTime) {
        return new GenStatGetter(context).list(startTime, endTime);
    }

    public List<DishStat> listDishStat(EmenuContext context, long time, int page) {
        long endTime = UnitUtils.getDayStart(time - page*PAGE_COUNT*UnitUtils.DAY);
        long startTime = endTime - PAGE_COUNT * UnitUtils.DAY;
        return listDishStat(context, startTime, endTime);
    }

    public List<DishStat> listDishStat(EmenuContext context, long startTime, long endTime) {
        List<Dish> dishes = menuLogic.getAllDish(context);
        List<DishStat> ret = new LinkedList<DishStat>();
        for (Dish dish:dishes) {
            List<DishStat> dishStats = new DishStatGetter(context, dish)
                .list(startTime, endTime);
            DishStat stat = null;
            for (DishStat s:dishStats) {
                if (stat == null) {
                    stat = s;
                } else {
                    stat.setIncome(s.getIncome() + stat.getIncome());
                    stat.setCount(s.getCount() + stat.getCount());
                    stat.setBackCount(s.getBackCount() + stat.getBackCount());
                    stat.setDiscount(s.getDiscount() + stat.getDiscount());
                }
            }
            if (stat == null) {
                stat = new DishStat();
                stat.setDishName(dish.getName());
                stat.setPrice(dish.getPrice());
            }
            ret.add(stat);
        }
        for (int i=0; i<ret.size(); i++) {
            DishStat s = ret.get(i);
            if (s.getId() == 0) {
                s.setId(0-i);
            }
        }
        return ret;
    }

    public List<MenuStat> listMenuStat(EmenuContext context, long time, int page) {
        long endTime = UnitUtils.getDayStart(time - page * PAGE_COUNT * UnitUtils.DAY);
        long startTime = endTime - PAGE_COUNT * UnitUtils.DAY;
        return listMenuStat(context, startTime, endTime);
    }

    public List<MenuStat> listMenuStat(EmenuContext context, long startTime, long endTime) {
        List<Dish> dishes = menuLogic.getAllDish(context);
        HashMap<String, ArrayList<Dish>> cateToDishes = new HashMap<String, ArrayList<Dish>>();
        List<MenuStat> ret = new LinkedList<MenuStat>();
        for (Dish dish : dishes) {
            String name = menuLogic.getCategory(context, dish.getId());
            if (cateToDishes.containsKey(name)) {
                cateToDishes.get(name).add(dish);
            } else {
                cateToDishes.put(name, new ArrayList<Dish>());
            }
        }
        Iterator<String> iterator = cateToDishes.keySet().iterator();
        while (iterator.hasNext()) {
            String chapterName = iterator.next();
            List<Dish> dishInCate = cateToDishes.get(chapterName);
            List<MenuStat> menuStats = new MenuStatGetter(context, chapterName, dishInCate).list(
                    startTime, endTime);
            MenuStat stat = null;
            for (MenuStat s : menuStats) {
                if (stat == null) {
                    stat = s;
                } else {
                    stat.setIncome(s.getIncome() + stat.getIncome());
                    stat.setCount(s.getCount() + stat.getCount());
                    stat.setDiscount(s.getDiscount() + stat.getDiscount());
                }
                if (stat == null) {
                    stat = new MenuStat();
                    stat.setChapterName(chapterName);
                }
            }
            ret.add(stat);
        }
        for (int i = 0; i < ret.size(); i++) {
            MenuStat s = ret.get(i);
            if (s.getId() == 0) {
                s.setId(0 - i);
            }
        }
        return ret;
    }

    private abstract class StatGetter<T extends BaseStat> {
        protected final EmenuContext context;
        
        public StatGetter(EmenuContext context) {
            super();
            this.context = context;
        }

        public List<T> list(long time, int page) {
            long endTime = UnitUtils.getDayStart(time - page*PAGE_COUNT*UnitUtils.DAY);
            long startTime = endTime - PAGE_COUNT * UnitUtils.DAY;
            return list(startTime, endTime);
        }
        
        public List<T> list(long startTime, long endTime) {
        List<T> ret = new LinkedList<T>();
        long p = startTime;
        if ((p-8*UnitUtils.HOUR) % UnitUtils.DAY != 0) {
            p = UnitUtils.getDayStart(p + UnitUtils.DAY);
        }
        if (p != startTime) {
            ret.add(getOne(startTime, p));
        }
        while (p < endTime) {
            long end = p + UnitUtils.DAY;
            if (end > endTime) {
                end = endTime;
            }
            ret.add(getOne(p, end));
            p += UnitUtils.DAY;
        }
        for (int i=0; i<ret.size(); i++) {
            T stat = ret.get(i);
            if (stat.getId() == 0) {
                //mock id
                stat.setId(0 - i);
            }
        }
        return ret;
        }

        private T getOne(long startTime, long endTime) {
            T stat = null;
            long today = UnitUtils.getDayStart(System.currentTimeMillis());
            if (endTime - startTime == UnitUtils.DAY && endTime<=today) {
                long day = UnitUtils.getDayByMillis((startTime+endTime)/2);
                stat = getFromDb(day);
                if (stat == null) {
                    stat = compute(context, startTime);
                    long now = System.currentTimeMillis();
                    stat.setCreatedTime(now);
                    stat.setUpdateTime(now);
                    addToDb(stat);
                }
            } else {
                stat = compute(startTime, endTime);
            }
            return stat;
        }

        private T compute(EmenuContext context, long time) {
            long startTime = UnitUtils.getDayStart(time);
            long endTime = startTime + UnitUtils.DAY;
            return compute(startTime, endTime);
        }
        
        protected abstract T getFromDb(long day);
        protected abstract void addToDb(T stat);
        protected abstract T compute(long startTime, long endTime);
    }
    
    private class GenStatGetter extends StatGetter<GeneralStat> {

        public GenStatGetter(EmenuContext context) {
            super(context);
        }

        @Override
        protected GeneralStat getFromDb(long day) {
            return genStatDb.get(context, day);
        }

        @Override
        protected void addToDb(GeneralStat stat) {
            genStatDb.add(context, stat);
        }

        @Override
        protected GeneralStat compute(long startTime, long endTime) {
            GeneralStat genStat = new GeneralStat();
    
            // TIME
            genStat.setDay(UnitUtils.getDayByMillis((startTime+endTime)/2));
    
            List<Bill> bills = orderLogic.getBills(context, startTime, endTime);
            double income = 0;
            int customers = 0;
            int invoices = 0;
            double invoiceAmount = 0;
            double discount = 0;
            double tips = 0;
            for (Bill bill : bills) {
                if (bill.getOrder() == null)
                    throw new ServerError("bill.getOrder is null!");
                customers += bill.getOrder().getCustomerNumber();
                if (bill.isInvoice()) {
                    invoices++;
                    invoiceAmount += bill.getInvoicePrice();
                }
                double originCost = bill.getOrder().getOriginPrice() + bill.getTip();
                if (bill.getCost() > originCost) {
                    discount += (originCost-bill.getCost());
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
            // Discount
            genStat.setDiscount(discount);
    
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
    
    private class DishStatGetter extends StatGetter<DishStat> {
        private final Dish dish;

        public DishStatGetter(EmenuContext context, Dish dish) {
            super(context);
            this.dish = dish;
        }

        @Override
        protected DishStat getFromDb(long day) {
            return dishStatDb.get(context, dish.getName(), day);
        }

        @Override
        protected void addToDb(DishStat stat) {
            dishStatDb.add(context, stat);
        }

        @Override
        protected DishStat compute(long startTime, long endTime) {
            DishStat stat = new DishStat();
    
            // TIME
            stat.setDay(UnitUtils.getDayByMillis((startTime+endTime)/2));
    
            List<Bill> bills = orderLogic.getBills(context, startTime, endTime);
            
            double income = 0;
            double discount = 0;
            int count = 0;
            for (Bill bill:bills) {
                OrderVO order = bill.getOrder();
                for (OrderDishVO d:order.getDishes()) {
                    if (d.getId() == this.dish.getId()) {
                        income += d.getPrice() * d.getNumber();
                        count += d.getNumber();
                        if (bill.getDiscountDishIds() != null
                                && ArrayUtils.contains(bill.getDiscountDishIds(), d.getId())
                                && bill.getDiscount()>0) {
                            discount += (d.getPrice()*d.getNumber()*(10 - bill.getDiscount()));
                        }
                    }
                }
            }
            int backCount = recordLogic.getCancelDishCount(context,
                    dish.getId(), startTime, endTime);
            String category = menuLogic.getCategory(context, dish.getId());
            
            stat.setIncome(income);
            stat.setCount(count);
            stat.setBackCount(backCount);
            stat.setDishName(dish.getName());
            stat.setDishClass(category);
            stat.setPrice(dish.getPrice());
            stat.setDiscount(discount);
            
            return stat;
        }
    }

    private class MenuStatGetter extends StatGetter<MenuStat> {
        private final List<Dish> dishes;

        private final String chapterName;

        public MenuStatGetter(EmenuContext context, String name, List<Dish> dishes) {
            super(context);
            this.dishes = dishes;
            this.chapterName = name;
        }

        @Override
        protected MenuStat getFromDb(long day) {
            return menuStatDb.get(context, chapterName, day);
        }

        @Override
        protected void addToDb(MenuStat stat) {
            menuStatDb.add(context, stat);
        }

        @Override
        protected MenuStat compute(long startTime, long endTime) {
            MenuStat stat = new MenuStat();

            // TIME
            stat.setDay(UnitUtils.getDayByMillis((startTime + endTime) / 2));

            List<Bill> bills = orderLogic.getBills(context, startTime, endTime);

            double income = 0;
            double discount = 0;
            int count = 0;
            for (Dish dish : this.dishes) {
                for (Bill bill : bills) {
                    OrderVO order = bill.getOrder();
                    for (OrderDishVO d : order.getDishes()) {
                        if (d.getId() == dish.getId()) {
                            income += d.getPrice() * d.getNumber();
                            count += d.getNumber();
                            if (bill.getDiscountDishIds() != null
                                    && ArrayUtils.contains(bill.getDiscountDishIds(), d.getId())
                                    && bill.getDiscount() > 0) {
                                discount += (d.getPrice() * d.getNumber() * (10 - bill
                                        .getDiscount()));
                            }
                        }
                    }
                }
            }
            stat.setIncome(income);
            stat.setCount(count);
            stat.setChapterName(this.chapterName);
            stat.setDiscount(discount);

            return stat;
        }
    }

}
