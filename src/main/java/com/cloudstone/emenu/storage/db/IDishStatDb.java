
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.DishStat;

public interface IDishStatDb extends IDb {
    public void add(EmenuContext context, DishStat stat);

    public DishStat get(EmenuContext context, String dishName, long day);
}
