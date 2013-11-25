/**
 * @(#)IDishNoteDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishNote;

/**
 * @author xuhongfeng
 *
 */
public interface IDishNoteDb {
    public List<DishNote> listAll(EmenuContext context);
    public void addDishNote(EmenuContext context, DishNote note);
    public void updateDishNote(EmenuContext context, DishNote note);
    public void deleteDishNote(EmenuContext context, int id);
    public DishNote getDishNote(EmenuContext context, int id);
    public DishNote getDishNoteByName(EmenuContext context, String name);

}
