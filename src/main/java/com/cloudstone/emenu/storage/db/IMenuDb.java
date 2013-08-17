/**
 * @(#)IMenuDb.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.Menu;

/**
 * @author xuhongfeng
 *
 */
public interface IMenuDb extends IDb {

    public void addMenu(Menu menu) ;
    public void updateMenu(Menu menu) ;
    public void deleteMenu(int id) ;
    public List<Menu> getAllMenu() ;
    public Menu getMenu(int id) ;
    public Menu getByName(String name) ;
}
