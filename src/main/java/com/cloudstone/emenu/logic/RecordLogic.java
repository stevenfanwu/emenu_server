/**
 * @(#)RecordLogic.java, Aug 26, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.CancelDishRecord;
import com.cloudstone.emenu.storage.db.ICancelDishRecordDb;

/**
 * @author xuhongfeng
 *
 */
@Service
public class RecordLogic extends BaseLogic {

    @Autowired
    private ICancelDishRecordDb cancelDishRecordDb;
    
    public void addCancelDishRecord(EmenuContext context, CancelDishRecord record) {
        cancelDishRecordDb.add(context, record);
    }
    
    public int getCancelDishCount(EmenuContext context, int dishId,
            long startTime, long endTime) {
        return cancelDishRecordDb.getCount(context, dishId, startTime, endTime);
    }
}
