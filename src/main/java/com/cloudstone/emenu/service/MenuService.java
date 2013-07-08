/**
 * @(#)MenuService.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Menu;
import com.cloudstone.emenu.exception.ServerError;

/**
 * @author xuhongfeng
 *
 */
@Service
public class MenuService extends BaseService implements IMenuService {

    @Override
    public void addMenu(Menu menu) {
        try {
            menuDb.addMenu(menu);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void updateMenu(Menu menu) {
        try {
            menuDb.updateMenu(menu);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
        
    }

    @Override
    public void deleteMenu(long id) {
        try {
            menuDb.deleteMenu(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Menu> getAllMenu() {
        try {
            return menuDb.getAllMenu();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public Menu getMenu(long id) {
        try {
            return menuDb.getMenu(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

}
