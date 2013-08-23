
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.UserStat;

public interface IUserStatDb extends IDb {
    public void add(EmenuContext context, UserStat stat);

    public UserStat get(EmenuContext context, long day);
}
