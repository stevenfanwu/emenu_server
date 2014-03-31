package com.cloudstone.emenu.logic;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Restaurant;
import com.cloudstone.emenu.storage.db.IRestaurantDb;
import com.cloudstone.emenu.util.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantLogic extends BaseLogic {

    @Autowired
    private IRestaurantDb restaurantDb;

    public Restaurant add(EmenuContext context, Restaurant restaurant) {
        long now = System.currentTimeMillis();
        restaurant.setUpdateTime(now);
        restaurant.setCreatedTime(now);
        restaurantDb.add(context, restaurant);

        return restaurantDb.get(context, restaurant.getId());
    }

    public List<Restaurant> getAll(EmenuContext context) {
        List<Restaurant> restaurants = restaurantDb.getAll(context);
        DataUtils.filterDeleted(restaurants);
        return restaurants;
    }

    public void delete(EmenuContext context, int id) {
        // TODO: Delete associated dishes&users.
        restaurantDb.delete(context, id);
    }

    public Restaurant update(EmenuContext context, Restaurant restaurant) {
        return restaurantDb.update(context, restaurant);
    }

    public void setRestaurantDb(IRestaurantDb restaurantDb) {
        this.restaurantDb = restaurantDb;
    }

}
