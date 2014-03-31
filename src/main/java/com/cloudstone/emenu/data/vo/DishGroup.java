/**
 * @(#)DishGroup.java, Aug 25, 2013. 
 *
 */
package com.cloudstone.emenu.data.vo;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xuhongfeng
 */
public class DishGroup {
    private String category;
    private List<OrderDishVO> dishes = new LinkedList<OrderDishVO>();

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<OrderDishVO> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDishVO> dishes) {
        this.dishes = dishes;
    }
}
