/**
 * @(#)RecordLogic.java, Aug 26, 2013. 
 *
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishRecord;
import com.cloudstone.emenu.data.FreeDishRecord;
import com.cloudstone.emenu.storage.db.IAddDishRecordDb;
import com.cloudstone.emenu.storage.db.ICancelDishRecordDb;
import com.cloudstone.emenu.storage.db.IFreeDishRecordDb;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 */
@Service
public class RecordLogic extends BaseLogic {

    @Autowired
    private ICancelDishRecordDb cancelDishRecordDb;

    @Autowired
    private IAddDishRecordDb addDishRecordDb;

    @Autowired
    private IFreeDishRecordDb freeDishRecordDb;

    public void addAddDishRecord(EmenuContext context, DishRecord record) {
        long now = System.currentTimeMillis();
        record.setCreatedTime(now);
        record.setUpdateTime(now);
        addDishRecordDb.add(context, record);
    }

    public void addCancelDishRecord(EmenuContext context, DishRecord record) {
        long now = System.currentTimeMillis();
        record.setCreatedTime(now);
        record.setUpdateTime(now);
        cancelDishRecordDb.add(context, record);
    }

    public int getCancelDishCount(EmenuContext context, int dishId,
                                  long startTime, long endTime) {
        return cancelDishRecordDb.getCount(context, dishId, startTime, endTime);
    }

    public void addFreeDishRecord(EmenuContext context, FreeDishRecord record) {
        freeDishRecordDb.add(context, record);
    }

    public int getFreeDishCount(EmenuContext context, int dishId,
                                long startTime, long endTime) {
        return freeDishRecordDb.getCount(context, dishId, startTime, endTime);
    }

    public List<DishRecord> listCancelDishRecords(EmenuContext context, int orderId) {
        List<DishRecord> result = cancelDishRecordDb.listByOrderId(context, orderId);
        DataUtils.filterDeleted(result);
        return result;
    }

    public List<DishRecord> listAddDishRecords(EmenuContext context, int orderId) {
        List<DishRecord> result = addDishRecordDb.listByOrderId(context, orderId);
        DataUtils.filterDeleted(result);
        return result;
    }
}
