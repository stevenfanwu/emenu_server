/**
 * @(#)IMenuDb.java, 2013-7-8. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Menu;

/**
 * @author xuhongfeng
 */
public interface IMenuDb extends IDb {

    public void addMenu(EmenuContext context, Menu menu);

    public void updateMenu(EmenuContext context, Menu menu);

    public void deleteMenu(EmenuContext context, int id);

    public List<Menu> getAllMenu(EmenuContext context);

    public Menu getMenu(EmenuContext context, int id);

    public Menu getByName(EmenuContext context, String name);
}
