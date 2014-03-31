/**
 * @(#)ICancelDishRecordDb.java, Aug 26, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishRecord;
import com.cloudstone.emenu.data.FreeDishRecord;

/**
 * @author xuhongfeng
 */
public interface IFreeDishRecordDb {
    public void add(EmenuContext context, FreeDishRecord record);

    public int getCount(EmenuContext context, int dishId, long startTime,
                        long endTime);
}
