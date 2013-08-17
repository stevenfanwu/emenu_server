/**
 * @(#)IPrintComponentDb.java, Aug 13, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.PrintComponent;

/**
 * @author xuhongfeng
 *
 */
public interface IPrintComponentDb {

    public List<PrintComponent> listAll();
    public void add(PrintComponent data);
    public void update(PrintComponent data);
    public void delete(int id) ;
    public PrintComponent get(int id);
}
