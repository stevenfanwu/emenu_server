package com.cloudstone.emenu.storage.db;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

import java.util.List;

public abstract class RestaurantOwnedDb extends SQLiteDb {

   public <T> List<T> getAll(EmenuContext context, RowMapper<T> rowMapper) {
      return query(context,
         new SelectSqlBuilder(this.getTableName()).appendWhere("restaurantId").build(),
         new RestaurantBinder(context.getRestaurantId()), rowMapper);
   }

   protected static final class RestaurantBinder implements StatementBinder {
      private final long id;

      public RestaurantBinder(long id) {
         super();
         this.id = id;
      }

      @Override
      public void onBind(SQLiteStatement stmt) throws SQLiteException {
         stmt.bind(1, id);
      }
   }

}
