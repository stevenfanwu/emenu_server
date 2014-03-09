package com.cloudstone.emenu.storage.db.util;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

public class RestaurantIdBinder implements StatementBinder {
   private final long id;

   public RestaurantIdBinder(long id) {
      super();
      this.id = id;
   }

   @Override
   public void onBind(SQLiteStatement stmt) throws SQLiteException {
      stmt.bind(1, id);
   }
}
