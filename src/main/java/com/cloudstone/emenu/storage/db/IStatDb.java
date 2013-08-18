
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DailyStat;

public interface IStatDb extends IDb {
    public void add(EmenuContext context, DailyStat stat);

    public DailyStat get(EmenuContext context, long day);

    public void update(EmenuContext context, DailyStat stat);
}
