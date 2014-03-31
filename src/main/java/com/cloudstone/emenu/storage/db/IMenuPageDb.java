/**
 * @(#)IMenuPageDb.java, Jul 14, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.MenuPage;

/**
 * @author xuhongfeng
 */
public interface IMenuPageDb extends IDb {

    public void addMenuPage(EmenuContext context, MenuPage page);

    public void updateMenuPage(EmenuContext context, MenuPage page);

    public void deleteMenuPage(EmenuContext context, int id);

    public List<MenuPage> getAllMenuPage(EmenuContext context);

    public List<MenuPage> listMenuPages(EmenuContext context, int chapterId);

    public List<MenuPage> listMenuPages(EmenuContext context, int[] ids);

    public MenuPage getMenuPage(EmenuContext context, int id);
}
