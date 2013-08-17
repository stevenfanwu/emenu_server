/**
 * @(#)DishTagDb.java, Jul 22, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

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
    protected void init() {
        super.init();
        if (listAll().size() == 0) {
            for (String name: DEFAULT_DISH_TAGS) {
                DishTag tag = new DishTag();
                tag.setName(name);
                add(tag);
            }
        }
    }
    
    @Override
    public DishTag getDishTagByName(String name) {
        return getByName(name);
    }
    
    @Override
    public DishTag getDishTag(int id) {
        return get(id);
    }

    @Override
    public List<DishTag> listAll() {
        return getAll();
    }

    @Override
    public void addDishTag(DishTag tag) {
        add(tag);
    }

    @Override
    public void updateDishTag(DishTag tag) {
        update(tag);
    }

    @Override
    public void deleteDishTag(int id) {
        delete(id);
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
