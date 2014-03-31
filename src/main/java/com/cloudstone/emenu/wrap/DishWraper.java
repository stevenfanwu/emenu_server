/**
 * @(#)DishWraper.java, Aug 25, 2013. 
 *
 */
package com.cloudstone.emenu.wrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishRecord;
import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.vo.CancelDishVO;
import com.cloudstone.emenu.data.vo.DishGroup;
import com.cloudstone.emenu.data.vo.OrderDishVO;
import com.cloudstone.emenu.logic.MenuLogic;

/**
 * @author xuhongfeng
 */
@Component
public class DishWraper extends BaseWraper {
    private static final Logger LOG = Logger.getLogger(DishWraper.class);

    @Autowired
    private MenuLogic menuLogic;

    public List<DishGroup> wrapDishGroup(EmenuContext context,
                                         List<OrderDishVO> dishes, int[] chapterIds, boolean isBill) {
        Map<String, DishGroup> map = new HashMap<String, DishGroup>();
        List<Chapter> chapters = menuLogic.listChapters(context, chapterIds);
        Set<String> categories = new HashSet<String>();
        for (Chapter c : chapters) {
            categories.add(c.getName());
        }

        for (OrderDishVO dish : dishes) {
            String category = menuLogic.getCategory(context, dish.getId());
            if (!categories.contains(category)) {
                LOG.info("ignore category:" + category);
                continue;
            }
            LOG.info("dishId=" + dish.getId() + ", category=" + category);
            int requireLength = isBill ? 10 : 7;
            if (dish.getName().length() > requireLength) {
                String name = dish.getName();
                String first = name.substring(0, requireLength);
                String second = name.substring(requireLength, name.length());
                StringBuilder finalName = new StringBuilder();
                dish.setName(finalName.append(first).append("\n").append(second).toString());
            }
            DishGroup group = map.get(category);
            if (group == null) {
                group = new DishGroup();
                group.setCategory(category);
                map.put(category, group);
            }
            group.getDishes().add(dish);
        }
        List<DishGroup> ret = new LinkedList<DishGroup>();
        for (DishGroup g : map.values()) {
            LOG.info(String.format("Group:%s  dishes.size=%d", g.getCategory(), g.getDishes().size()));
            ret.add(g);
        }
        return ret;
    }

    public CancelDishVO wrapCancelDish(EmenuContext context, DishRecord record) {
        Dish dish = menuLogic.getDish(context, record.getDishId());
        CancelDishVO vo = new CancelDishVO(dish, record);
        return vo;
    }

    public List<CancelDishVO> wrapCancelDish(EmenuContext context, List<DishRecord> records) {
        List<CancelDishVO> r = new ArrayList<CancelDishVO>(records.size());
        for (DishRecord record : records) {
            r.add(wrapCancelDish(context, record));
        }
        return r;
    }
}
