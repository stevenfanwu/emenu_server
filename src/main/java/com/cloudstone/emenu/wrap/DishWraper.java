/**
 * @(#)DishWraper.java, Aug 25, 2013. 
 * 
 */
package com.cloudstone.emenu.wrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.CancelDishRecord;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.vo.CancelDishVO;
import com.cloudstone.emenu.data.vo.DishGroup;
import com.cloudstone.emenu.data.vo.OrderDishVO;

/**
 * @author xuhongfeng
 *
 */
@Component
public class DishWraper extends BaseWraper {
    private static final Logger LOG = Logger.getLogger(DishWraper.class);

    public List<DishGroup> wrapDishGroup(EmenuContext context,
            List<OrderDishVO> dishes) {
        Map<String, DishGroup> map = new HashMap<String, DishGroup>();
        
        for (OrderDishVO dish:dishes) {
            String category = menuLogic.getCategory(context, dish.getId());
            LOG.info("dishId=" + dish.getId() + ", category=" + category);
            DishGroup group = map.get(category);
            if (group == null) {
                group = new DishGroup();
                group.setCategory(category);
                map.put(category, group);
            }
            group.getDishes().add(dish);
        }
        List<DishGroup> ret = new LinkedList<DishGroup>();
        for (DishGroup g:map.values()) {
            ret.add(g);
        }
        return ret;
    }
    
    public CancelDishVO wrapCancelDish(EmenuContext context, CancelDishRecord record) {
        Dish dish = menuLogic.getDish(context, record.getDishId());
        CancelDishVO vo = new CancelDishVO(dish, record);
        return vo;
    }
    
    public List<CancelDishVO> wrapCancelDish(EmenuContext context, List<CancelDishRecord> records) {
        List<CancelDishVO> r = new ArrayList<CancelDishVO>(records.size());
        for (CancelDishRecord record:records) {
            r.add(wrapCancelDish(context, record));
        }
        return r;
    }
}
