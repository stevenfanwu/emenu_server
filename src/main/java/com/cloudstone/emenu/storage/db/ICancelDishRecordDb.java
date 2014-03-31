/**
 * @(#)ICancelDishRecordDb.java, Aug 26, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishRecord;

/**
 * @author xuhongfeng
 */
public interface ICancelDishRecordDb {
    public void add(EmenuContext context, DishRecord record);

    public int getCount(EmenuContext context, int dishId, long startTime,
                        long endTime);

    public List<DishRecord> listByOrderId(EmenuContext context, int orderId);
}
