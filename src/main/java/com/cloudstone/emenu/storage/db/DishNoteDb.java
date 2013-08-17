/**
 * @(#)DishNoteDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.data.DishNote;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class DishNoteDb extends IdNameDb<DishNote> implements IDishNoteDb {
    private static final String TABLE_NAME = "dishNote";
    
    private static final String[] DEFAULT_DISH_NOTES = {
        "不放辣", "不放蒜"
    };
    
    @Override
    protected void init() {
        super.init();
        if (listAll().size() == 0) {
            for (String name: DEFAULT_DISH_NOTES) {
                DishNote note = new DishNote();
                note.setName(name);
                add(note);
            }
        }
    }
    
    @Override
    public DishNote getDishNoteByName(String name) {
        return getByName(name);
    }
    
    @Override
    public DishNote getDishNote(int id) {
        return get(id);
    }

    @Override
    public List<DishNote> listAll() {
        return getAll();
    }

    @Override
    public void addDishNote(DishNote note) {
        add(note);
    }

    @Override
    public void updateDishNote(DishNote note) {
        update(note);
    }

    @Override
    public void deleteDishNote(int id) {
        delete(id);
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
