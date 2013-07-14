/**
 * @(#)IMenuPageDb.java, Jul 14, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.MenuPage;

/**
 * @author xuhongfeng
 *
 */
public interface IMenuPageDb {

    public void addMenuPage(MenuPage page) throws SQLiteException;
    public void updateMenuPage(MenuPage page) throws SQLiteException;
    public void deleteMenuPage(long id) throws SQLiteException;
    public List<MenuPage> getAllMenuPage() throws SQLiteException;
    public List<MenuPage> listMenuPages(long chapterId) throws SQLiteException;
    public MenuPage getMenuPage(long id) throws SQLiteException;
}
