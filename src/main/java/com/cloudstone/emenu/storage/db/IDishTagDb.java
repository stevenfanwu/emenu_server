/**
 * @(#)IDishTagDb.java, Jul 22, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.DishTag;

/**
 * @author xuhongfeng
 *
 */
public interface IDishTagDb extends IDb {
    public List<DishTag> listAll() ;
    public void addDishTag(DishTag tag) ;
    public void updateDishTag(DishTag tag) ;
    public void deleteDishTag(int id) ;
    public DishTag getDishTag(int id) ;
    public DishTag getDishTagByName(String name) ;
}
