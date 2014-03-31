/**
 * @(#)IDishTagDb.java, Jul 22, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishTag;

/**
 * @author xuhongfeng
 */
public interface IDishTagDb extends IDb {
    public List<DishTag> listAll(EmenuContext context);

    public void addDishTag(EmenuContext context, DishTag tag);

    public void updateDishTag(EmenuContext context, DishTag tag);

    public void deleteDishTag(EmenuContext context, int id);

    public DishTag getDishTag(EmenuContext context, int id);

    public DishTag getDishTagByName(EmenuContext context, String name);
}
