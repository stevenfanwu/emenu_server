/**
 * @(#)IDishDb.java, 2013-7-7. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.IdName;

/**
 * @author xuhongfeng
 */
public interface IDishDb extends IDb {
    public void add(EmenuContext context, Dish dish);

    public void update(EmenuContext context, Dish dish);

    public Dish get(EmenuContext context, int dishId);

    public Dish getByName(EmenuContext context, String name);

    public List<Dish> getAll(EmenuContext context);

    public void delete(EmenuContext context, int dishId);

    public List<IdName> getDishSuggestion(EmenuContext context);
}
