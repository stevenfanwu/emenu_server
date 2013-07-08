/**
 * @(#)IMenuDb.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Menu;

/**
 * @author xuhongfeng
 *
 */
public interface IMenuDb {

    public void addMenu(Menu menu) throws SQLiteException;
    public void updateMenu(Menu menu) throws SQLiteException;
    public void deleteMenu(long id) throws SQLiteException;
    public List<Menu> getAllMenu() throws SQLiteException;
    public Menu getMenu(long id) throws SQLiteException;
}
