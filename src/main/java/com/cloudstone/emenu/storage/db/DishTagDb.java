/**
 * @(#)DishTagDb.java, Jul 22, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.DishTag;
import com.cloudstone.emenu.util.IdGenerator;

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
    
    public static void initData() {
        DishTagDb db = new DishTagDb();
        for (String name: DEFAULT_DISH_TAGS) {
            DishTag tag = new DishTag();
            tag.setId(IdGenerator.generateId());
            tag.setName(name);
            try {
                db.add(tag);
            } catch (SQLiteException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    @Override
    public DishTag getDishTag(long id) throws SQLiteException {
        return get(id);
    }

    @Override
    public List<DishTag> listAll() throws SQLiteException {
        return getAll();
    }

    @Override
    public void addDishTag(DishTag tag) throws SQLiteException {
        add(tag);
    }

    @Override
    public void updateDishTag(DishTag tag) throws SQLiteException {
        update(tag);
    }

    @Override
    public void deleteDishTag(long id) throws SQLiteException {
        delete(id);
    }
    
    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }
    
    @Override
    protected DishTag newObject() {
        return new DishTag();
    }
}
