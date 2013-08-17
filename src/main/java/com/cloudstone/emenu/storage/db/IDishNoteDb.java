/**
 * @(#)IDishNoteDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.DishNote;

/**
 * @author xuhongfeng
 *
 */
public interface IDishNoteDb {
    public List<DishNote> listAll();
    public void addDishNote(DishNote note);
    public void updateDishNote(DishNote note);
    public void deleteDishNote(int id);
    public DishNote getDishNote(int id);
    public DishNote getDishNoteByName(String name);

}
