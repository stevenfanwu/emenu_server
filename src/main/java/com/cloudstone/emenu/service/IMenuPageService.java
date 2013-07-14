/**
 * @(#)IMenuPageService.java, Jul 15, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.MenuPage;

/**
 * @author xuhongfeng
 *
 */
public interface IMenuPageService {
    public void addMenuPage(MenuPage page);
    public void updateMenuPage(MenuPage page);
    public void deleteMenuPage(long id);
    public List<MenuPage> getAllMenuPage();
    public List<MenuPage> listByChapterId(long chapterId);
    public MenuPage getMenuPage(long id);
    
}
