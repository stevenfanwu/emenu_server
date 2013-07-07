/**
 * @(#)DishLogic.java, 2013-7-7. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.util.IdGenerator;

/**
 * @author xuhongfeng
 *
 */
@Component
public class DishLogic extends BaseLogic {
    
    public Dish add(Dish dish) {
        dish.setId(IdGenerator.generateId());
        return dishService.add(dish);
    }
    
    public Dish update(Dish dish) {
        return dishService.update(dish);
    }
    
    public List<Dish> getAll() {
        return dishService.getAll();
    }
    
    public void delete(long id) {
        dishService.delete(id);
    }

    public Dish get(long id) {
        return dishService.get(id);
    }
}
