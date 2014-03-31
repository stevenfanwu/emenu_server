package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Restaurant;

import java.util.List;

public interface IRestaurantDb extends IDb {
    public Restaurant get(EmenuContext context, int id);

    public Restaurant add(EmenuContext context, Restaurant restaurant);

    public List<Restaurant> getAll(EmenuContext context);

    public Restaurant update(EmenuContext context, Restaurant restaurant);
}
