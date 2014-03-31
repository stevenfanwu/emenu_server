/**
 * @(#)DishNoteDb.java, Aug 4, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishNote;

/**
 * @author xuhongfeng
 */
@Repository
public class DishNoteDb extends IdNameDb<DishNote> implements IDishNoteDb {
    private static final String TABLE_NAME = "dishNote";

    private static final String[] DEFAULT_DISH_NOTES = {
            "不放辣", "不放蒜"
    };

    @Override
    protected void init(EmenuContext context) {
        super.init(context);
        if (listAll(context).size() == 0) {
            for (String name : DEFAULT_DISH_NOTES) {
                DishNote note = new DishNote();
                note.setName(name);
                add(context, note);
            }
        }
    }

    @Override
    public DishNote getDishNoteByName(EmenuContext context, String name) {
        return getByName(context, name);
    }

    @Override
    public DishNote getDishNote(EmenuContext context, int id) {
        return get(context, id);
    }

    @Override
    public List<DishNote> listAll(EmenuContext context) {
        return getAll(context);
    }

    @Override
    public void addDishNote(EmenuContext context, DishNote note) {
        add(context, note);
    }

    @Override
    public void updateDishNote(EmenuContext context, DishNote note) {
        update(context, note);
    }

    @Override
    public void deleteDishNote(EmenuContext context, int id) {
        delete(context, id);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected DishNote newObject() {
        return new DishNote();
    }

}
