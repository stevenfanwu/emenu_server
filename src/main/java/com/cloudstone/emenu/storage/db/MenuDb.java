/**
 * @(#)MenuDb.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Menu;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class MenuDb extends IdNameDb<Menu> implements IMenuDb {
    private static final String TABLE_NAME = "menu";
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void addMenu(Menu menu) throws SQLiteException {
        add(menu);
    }

    @Override
    public void updateMenu(Menu menu) throws SQLiteException {
        update(menu);
    }

    @Override
    public void deleteMenu(int id) throws SQLiteException {
        delete(id);
    }

    @Override
    public List<Menu> getAllMenu() throws SQLiteException {
        return getAll();
    }

    @Override
    public Menu getMenu(int id) throws SQLiteException {
        return get(id);
    }
    
    @Override
    protected Menu newObject() {
        return new Menu();
    }
}
