/**
 * @(#)IPrintTemplateDb.java, Aug 15, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.PrintTemplate;

/**
 * @author xuhongfeng
 *
 */
public interface IPrintTemplateDb {

    public void add(PrintTemplate template);
    public void update(PrintTemplate template);
    public PrintTemplate get(int id);
    public List<PrintTemplate> listAll();
    public void delete(int id) ;
    public void removeComponent(int componentId);
}
