/**
 * @(#)DataUtils.java, Aug 2, 2013. 
 *
 */
package com.cloudstone.emenu.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cloudstone.emenu.data.BaseData;
import com.cloudstone.emenu.data.IdName;

/**
 * @author xuhongfeng
 */
public class DataUtils {

    public static <T extends BaseData> void filterDeleted(List<T> datas) {
        Iterator<T> it = datas.iterator();
        while (it.hasNext()) {
            if (it.next().isDeleted()) {
                it.remove();
            }
        }
    }

    public static <T extends IdName> List<String> pickNames(List<T> datas) {
        List<String> names = new ArrayList<String>();
        for (T data : datas) {
            names.add(data.getName());
        }
        return names;
    }

    public static double calMoney(double a) {
        BigDecimal d = new BigDecimal(a);
        d = d.setScale(2, BigDecimal.ROUND_HALF_UP);
        return d.doubleValue();
    }

    public static double round(double a, int digits) {
        BigDecimal d = new BigDecimal(a);
        d = d.setScale(digits, BigDecimal.ROUND_HALF_UP);
        return d.doubleValue();
    }
}
