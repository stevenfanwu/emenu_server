/**
 * @(#)IDishDb.java, 2013-7-7. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.IdName;

/**
 * @author xuhongfeng
 *
 */
public interface IDishDb extends IDb {
    public void add(Dish dish) ;
    public void update(Dish dish) ;
    public Dish get(int dishId) ;
    public Dish getByName(String name) ;
    public List<Dish> getAll() ;
    public void delete(int dishId) ;
    public List<IdName> getDishSuggestion() ;
}
