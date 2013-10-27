
package com.cloudstone.emenu.wrap;

import java.util.ArrayList;
import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.DishRecord;
import com.cloudstone.emenu.data.vo.RecordVO;

public class RecordWraper extends BaseWraper {

    public List<RecordVO> wrapRecord(EmenuContext context, List<DishRecord> records) {
        List<RecordVO> recordVOs = new ArrayList<RecordVO>();
        for (DishRecord cdr : records) {
            RecordVO rvo = new RecordVO();
            rvo.setCount(cdr.getCount());
            rvo.setName(menuLogic.getDish(context, cdr.getDishId()).getName());
            rvo.setUnitLabel(Const.DishUnit.getLabel(menuLogic.getDish(context, cdr.getDishId())
                    .getUnit()));
            recordVOs.add(rvo);
        }
        return recordVOs;
    }
}
