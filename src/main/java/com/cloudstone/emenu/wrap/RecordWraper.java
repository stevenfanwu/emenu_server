
package com.cloudstone.emenu.wrap;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.DishRecord;
import com.cloudstone.emenu.data.vo.RecordVO;
import com.cloudstone.emenu.logic.MenuLogic;
import com.cloudstone.emenu.logic.OrderLogic;

@Service
public class RecordWraper extends BaseWraper {

    @Autowired
    private MenuLogic menuLogic;

    @Autowired
    private OrderLogic orderLogic;

    public List<RecordVO> wrapRecord(EmenuContext context, List<DishRecord> records) {
        List<RecordVO> recordVOs = new ArrayList<RecordVO>();
        for (DishRecord cdr : records) {
            RecordVO rvo = new RecordVO();
            double price = menuLogic.getDish(context, cdr.getDishId()).getPrice();
            rvo.setPrice(price);
            rvo.setCount(cdr.getCount());
            rvo.setName(menuLogic.getDish(context, cdr.getDishId()).getName());
            rvo.setTotal(price * cdr.getCount());
            rvo.setUnitLabel(Const.DishUnit.getLabel(menuLogic.getDish(context, cdr.getDishId())
                    .getUnit()));
            recordVOs.add(rvo);
        }
        return recordVOs;
    }
}
