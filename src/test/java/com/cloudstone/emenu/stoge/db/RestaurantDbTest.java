package com.cloudstone.emenu.stoge.db;

import static org.junit.Assert.*;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Restaurant;
import com.cloudstone.emenu.storage.db.RestaurantDb;
import com.cloudstone.emenu.storage.db.util.SqliteDataSource;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

/**
 * Created by nicholaszhao on 3/30/14.
 */
public class RestaurantDbTest {

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    private RestaurantDb restaurantDb;

    @Before
    public void createDb() throws Exception {
        SqliteDataSource dataSource = new SqliteDataSource();
        File dbFile = tempFolder.newFile();
        dataSource.setDbFile(dbFile);
        restaurantDb = new RestaurantDb();
        restaurantDb.setDataSource(dataSource);
    }

    @Test
    public void basicOperationTest() {
        final EmenuContext context = new EmenuContext();
        final String restaurantName = "restaurant";
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantName);
        restaurant = restaurantDb.add(context, restaurant);
        restaurant = restaurantDb.get(context, restaurant.getId());
        assertEquals(restaurantName, restaurant.getName());

        Restaurant restaurant2 = new Restaurant();
        restaurant2.setName("name2");
        restaurant2 = restaurantDb.add(context, restaurant2);

        final String updateRestaurantName = "UpdateName";
        restaurant.setName(updateRestaurantName);
        restaurantDb.update(context, restaurant);
        restaurant = restaurantDb.get(context, restaurant.getId());
        assertEquals(updateRestaurantName, restaurant.getName());
        assertEquals("name2", restaurantDb.get(context, restaurant2.getId()).getName());

        restaurantDb.delete(new EmenuContext(), restaurant.getId());
        restaurant = restaurantDb.get(new EmenuContext(), restaurant.getId());
        assertTrue(restaurant.isDeleted());
    }

}
