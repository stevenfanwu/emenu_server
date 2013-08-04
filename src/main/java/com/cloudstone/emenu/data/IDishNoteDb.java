/**
 * @(#)IDishNoteDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;

/**
 * @author xuhongfeng
 *
 */
public interface IDishNoteDb {
    public List<DishNote> listAll() throws SQLiteException;
    public void addDishNote(DishNote note) throws SQLiteException;
    public void updateDishNote(DishNote note) throws SQLiteException;
    public void deleteDishNote(int id) throws SQLiteException;
    public DishNote getDishNote(int id) throws SQLiteException;
    public DishNote getDishNoteByName(String name) throws SQLiteException;

}
