/**
 * @(#)IDishService.java, 2013-7-7. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.Dish;

/**
 * @author xuhongfeng
 *
 */
public interface IDishService {
    public Dish add(Dish dish);
    public List<Dish> getAll();
    public Dish get(long id);
    public void delete(long id);
    public Dish update(Dish dish);
}
