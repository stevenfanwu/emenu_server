/**
 * @(#)DishTagDb.java, Jul 22, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishTag;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class DishTagDb extends IdNameDb<DishTag> implements IDishTagDb {
    private static final String TABLE_NAME = "dishTag";
    
    private static final String[] DEFAULT_DISH_TAGS = {
        "热菜", "凉菜", "饮料"
    };
    
    @Override
    protected void init(EmenuContext context) {
        super.init(context);
        if (listAll(context).size() == 0) {
            for (String name: DEFAULT_DISH_TAGS) {
                DishTag tag = new DishTag();
                tag.setName(name);
                add(context, tag);
            }
        }
    }
    
    @Override
    public DishTag getDishTagByName(EmenuContext context, String name) {
        return getByName(context, name);
    }
    
    @Override
    public DishTag getDishTag(EmenuContext context, int id) {
        return get(context, id);
    }

    @Override
    public List<DishTag> listAll(EmenuContext context) {
        return getAll(context);
    }

    @Override
    public void addDishTag(EmenuContext context, DishTag tag) {
        add(context, tag);
    }

    @Override
    public void updateDishTag(EmenuContext context, DishTag tag) {
        update(context, tag);
    }

    @Override
    public void deleteDishTag(EmenuContext context, int id) {
        delete(context, id);
    }
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
    @Override
    protected DishTag newObject() {
        return new DishTag();
    }
}
