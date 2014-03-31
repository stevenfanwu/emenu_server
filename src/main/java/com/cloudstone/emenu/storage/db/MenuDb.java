/**
 * @(#)MenuDb.java, 2013-7-8. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Menu;

/**
 * @author xuhongfeng
 */
@Repository
public class MenuDb extends IdNameDb<Menu> implements IMenuDb {
    private static final String TABLE_NAME = "menu";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void addMenu(EmenuContext context, Menu menu) {
        add(context, menu);
    }

    @Override
    public void updateMenu(EmenuContext context, Menu menu) {
        update(context, menu);
    }

    @Override
    public void deleteMenu(EmenuContext context, int id) {
        delete(context, id);
    }

    @Override
    public List<Menu> getAllMenu(EmenuContext context) {
        return getAll(context);
    }

    @Override
    public Menu getMenu(EmenuContext context, int id) {
        return get(context, id);
    }

    @Override
    protected Menu newObject() {
        return new Menu();
    }
}
