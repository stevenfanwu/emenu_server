/**
 * @(#)DishService.java, 2013-7-7. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.exception.ServerError;

/**
 * @author xuhongfeng
 *
 */
@Service
public class DishService extends BaseService implements IDishService {

    @Override
    public Dish add(Dish dish) {
        try {
            dishDb.add(dish);
            return dishDb.get(dish.getId());
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Dish> getAll() {
        try {
            return dishDb.getAll();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void delete(long id) {
        try {
            dishDb.delete(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public Dish update(Dish dish) {
        try {
            dishDb.update(dish);
            return dishDb.get(dish.getId());
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public Dish get(long id) {
        try {
            return dishDb.get(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

}
