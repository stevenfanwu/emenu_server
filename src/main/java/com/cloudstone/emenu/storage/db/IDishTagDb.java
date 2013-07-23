/**
 * @(#)IDishTagDb.java, Jul 22, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.DishTag;

/**
 * @author xuhongfeng
 *
 */
public interface IDishTagDb {
    public List<DishTag> listAll() throws SQLiteException;
    public void addDishTag(DishTag tag) throws SQLiteException;
    public void updateDishTag(DishTag tag) throws SQLiteException;
    public void deleteDishTag(long id) throws SQLiteException;
    public DishTag getDishTag(long id) throws SQLiteException;
}
