
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.data.DailyStat;

public interface IStatDb extends IDb {
    public void add(DailyStat stat);

    public DailyStat get(long time);

    public void update(DailyStat stat);
}
