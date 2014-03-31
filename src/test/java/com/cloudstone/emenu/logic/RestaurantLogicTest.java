package com.cloudstone.emenu.logic;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Restaurant;
import com.cloudstone.emenu.storage.db.RestaurantDb;
import com.cloudstone.emenu.storage.db.util.SqliteDataSource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nicholaszhao on 3/30/14.
 */
public class RestaurantLogicTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private RestaurantLogic restaurantLogic;

    @Before
    public void createDb() throws Exception {
        SqliteDataSource dataSource = new SqliteDataSource();
        File dbFile = tempFolder.newFile();
        dataSource.setDbFile(dbFile);
        RestaurantDb restaurantDb = new RestaurantDb();
        restaurantDb.setDataSource(dataSource);
        restaurantLogic = new RestaurantLogic();
        restaurantLogic.setRestaurantDb(restaurantDb);
    }

    @Test
    public void basicRestaurantOperations() {
        final String restaurantName = "restaurantName";
        final EmenuContext context = new EmenuContext();
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantName);
        restaurantLogic.add(context, restaurant);
        List<Restaurant> restaurants = restaurantLogic.getAll(new EmenuContext());
        assertEquals(1, restaurants.size());
        restaurant = restaurants.get(0);
        assertEquals(restaurantName, restaurant.getName());

        final String updatedRestaurantName = "UpdatedRestaurantName";
        restaurant.setName(updatedRestaurantName);
        restaurantLogic.update(context, restaurant);

        restaurant = restaurantLogic.getAll(context).get(0);
        assertEquals(updatedRestaurantName, restaurant.getName());

        restaurantLogic.delete(context, restaurant.getId());
        restaurants = restaurantLogic.getAll(new EmenuContext());
        assertEquals(0, restaurants.size());
    }

}
