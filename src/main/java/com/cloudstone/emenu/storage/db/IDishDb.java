/**
 * @(#)IDishDb.java, 2013-7-7. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.IdName;

/**
 * @author xuhongfeng
 *
 */
public interface IDishDb extends IDb {
    public void add(Dish dish) throws SQLiteException;
    public void update(Dish dish) throws SQLiteException;
    public Dish get(int dishId) throws SQLiteException;
    public List<Dish> getAll() throws SQLiteException;
    public void delete(int dishId) throws SQLiteException;
    public List<IdName> getDishSuggestion() throws SQLiteException;
}
