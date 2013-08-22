
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.GenStat;

public interface IGenStatDb extends IDb {
    public void add(EmenuContext context, GenStat stat);

    public GenStat get(EmenuContext context, long day);
}
