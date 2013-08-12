
package com.cloudstone.emenu.storage.db;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.DailyStat;

public interface IStatDb extends IDb {
    public void add(DailyStat stat) throws SQLiteException;

    public DailyStat get(long time) throws SQLiteException;

    public void update(DailyStat stat) throws SQLiteException;
}
