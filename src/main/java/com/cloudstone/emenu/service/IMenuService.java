/**
 * @(#)IMenuService.java, 2013-7-8. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.Menu;

/**
 * @author xuhongfeng
 *
 */
public interface IMenuService {
    public void addMenu(Menu menu);
    public void updateMenu(Menu menu);
    public void deleteMenu(long id);
    public List<Menu> getAllMenu();
    public Menu getMenu(long id);
}