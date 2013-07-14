/**
 * @(#)MenuPageService.java, Jul 15, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.exception.ServerError;

/**
 * @author xuhongfeng
 *
 */
@Service
public class MenuPageService extends BaseService implements IMenuPageService {

    @Override
    public void addMenuPage(MenuPage page) {
        try {
            menuPageDb.addMenuPage(page);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void updateMenuPage(MenuPage page) {
        try {
            menuPageDb.updateMenuPage(page);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void deleteMenuPage(long id) {
        try {
            menuPageDb.deleteMenuPage(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<MenuPage> getAllMenuPage() {
        try {
            return menuPageDb.getAllMenuPage();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<MenuPage> listByChapterId(long chapterId) {
        try {
            return menuPageDb.listMenuPages(chapterId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public MenuPage getMenuPage(long id) {
        try {
            return menuPageDb.getMenuPage(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

}
