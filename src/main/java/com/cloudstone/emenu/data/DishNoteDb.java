/**
 * @(#)DishNoteDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.storage.db.IdNameDb;

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
    protected void init() throws SQLiteException {
        super.init();
        try {
            if (listAll().size() == 0) {
                for (String name: DEFAULT_DISH_NOTES) {
                    DishNote note = new DishNote();
                    note.setName(name);
                    add(note);
                }
            }
        } catch (SQLiteException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public DishNote getDishNoteByName(String name) throws SQLiteException {
        return getByName(name);
    }
    
    @Override
    public DishNote getDishNote(int id) throws SQLiteException {
        return get(id);
    }

    @Override
    public List<DishNote> listAll() throws SQLiteException {
        return getAll();
    }

    @Override
    public void addDishNote(DishNote note) throws SQLiteException {
        add(note);
    }

    @Override
    public void updateDishNote(DishNote note) throws SQLiteException {
        update(note);
    }

    @Override
    public void deleteDishNote(int id) throws SQLiteException {
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
