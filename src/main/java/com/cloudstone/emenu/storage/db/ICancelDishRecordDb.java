/**
 * @(#)ICancelDishRecordDb.java, Aug 26, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.CancelDishRecord;

/**
 * @author xuhongfeng
 *
 */
public interface ICancelDishRecordDb {
    public void add(EmenuContext context, CancelDishRecord record);
    public int getCount(EmenuContext context, int dishId, long startTime,
            long endTime);
    public List<CancelDishRecord> listByOrderId(EmenuContext context, int orderId);
}
