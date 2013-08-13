/**
 * @(#)IPrintComponentDb.java, Aug 13, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.PrintComponent;

/**
 * @author xuhongfeng
 *
 */
public interface IPrintComponentDb {

    public List<PrintComponent> listAll();
    public void add(PrintComponent data);
    public void update(PrintComponent data);
    public void delete(int id) throws SQLiteException;
    public PrintComponent get(int id);
}
