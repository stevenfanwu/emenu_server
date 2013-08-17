/**
 * @(#)IMenuPageDb.java, Jul 14, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.MenuPage;

/**
 * @author xuhongfeng
 *
 */
public interface IMenuPageDb extends IDb {

    public void addMenuPage(MenuPage page) ;
    public void updateMenuPage(MenuPage page) ;
    public void deleteMenuPage(int id) ;
    public List<MenuPage> getAllMenuPage() ;
    public List<MenuPage> listMenuPages(int chapterId) ;
    public List<MenuPage> listMenuPages(int[] ids) ;
    public MenuPage getMenuPage(int id) ;
}
