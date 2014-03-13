package com.cloudstone.emenu.storage.db;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Restaurant;
import com.cloudstone.emenu.storage.db.util.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RestaurantDb extends SQLiteDb implements IRestaurantDb {

   private static final String TABLE_NAME = "'restaurant'";

   private static enum Column {
      ID("id"),
      NAME("name"),
      CREATED_TIME("createdTime"),
      UPDATE_TIME("updatetime"),
      DELETED("deleted");

      private final String str;
      private Column(String str) {
         this.str = str;
      }

      @Override
      public String toString() {
         return str;
      }
   }

   private static final String COL_DEF = new ColumnDefBuilder()
      .append(Column.ID.toString(), DataType.INTEGER, "NOT NULL PRIMARY KEY")
      .append(Column.NAME.toString(), DataType.TEXT, "NOT NULL")
      .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
      .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
      .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
      .build();

   private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();

   private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
      .appendWhere(Column.ID.toString()).build();
   
   private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 5).build();

   @Override
   public Restaurant get(EmenuContext context, int id) {
      IdStatementBinder binder = new IdStatementBinder(id);
      Restaurant restaurant = queryOne(context, SQL_SELECT_BY_ID, binder, rowMapper);
      return restaurant;
   }

   @Override
   public Restaurant add(EmenuContext context, Restaurant restaurant) {
      restaurant.setId(genId(context));
      RestaurantBinder binder = new RestaurantBinder(restaurant);
      executeSQL(context, SQL_INSERT, binder);
      return get(context, restaurant.getId());
   }

   @Override
   public List<Restaurant> getAll(EmenuContext context) {
      return query(context, SQL_SELECT, StatementBinder.NULL, rowMapper);
   }

   @Override
   protected void onCheckCreateTable(EmenuContext context) {
      checkCreateTable(context, TABLE_NAME, COL_DEF);
   }

   @Override
   public String getTableName() {
      return TABLE_NAME;
   }

   private class RestaurantBinder implements StatementBinder {
      private final Restaurant restaurant;

      public RestaurantBinder(Restaurant restaurant) {
         super();
         this.restaurant = restaurant;
      }

      @Override
      public void onBind(SQLiteStatement stmt) throws SQLiteException {
         stmt.bind(1, restaurant.getId());
         stmt.bind(2,  restaurant.getName());
         stmt.bind(3, restaurant.getCreatedTime());
         stmt.bind(4, restaurant.getUpdateTime());
         stmt.bind(5, restaurant.isDeleted() ? 1 : 0);
      }
   }

   private RowMapper<Restaurant> rowMapper = new RowMapper<Restaurant>() {

      @Override
      public Restaurant map(SQLiteStatement stmt) throws SQLiteException {
         Restaurant restaurant = new Restaurant();
         restaurant.setId(stmt.columnInt(0));
         restaurant.setRestaurantId(restaurant.getId());
         restaurant.setName(stmt.columnString(1));
         restaurant.setCreatedTime(stmt.columnLong(2));
         restaurant.setUpdateTime(stmt.columnLong(3));
         restaurant.setDeleted(stmt.columnInt(4) == 1);
         return restaurant;
      }
   };
}
