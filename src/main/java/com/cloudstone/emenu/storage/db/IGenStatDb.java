
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.GeneralStat;

public interface IGenStatDb extends IDb {
    public void add(EmenuContext context, GeneralStat stat);

    public GeneralStat get(EmenuContext context, long day);
}
