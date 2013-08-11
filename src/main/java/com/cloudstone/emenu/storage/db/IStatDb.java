
package com.cloudstone.emenu.storage.db;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.DailyStat;

public interface IStatDb extends IDb {
    public void add(DailyStat stat) throws SQLiteException;

    public DailyStat get(long day) throws SQLiteException;

}
