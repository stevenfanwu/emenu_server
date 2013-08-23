
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.MenuStat;

public interface IMenuStatDb extends IDb {
    public void add(EmenuContext context, MenuStat stat);

    public MenuStat get(EmenuContext context, long day);
}
