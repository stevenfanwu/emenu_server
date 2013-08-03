/**
 * @(#)DataUtils.java, Aug 2, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import java.util.Iterator;
import java.util.List;

import com.cloudstone.emenu.data.BaseData;

/**
 * @author xuhongfeng
 *
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
}
