/**
 * @(#)IPrintComponentDb.java, Aug 13, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.PrintComponent;

/**
 * @author xuhongfeng
 */
public interface IPrintComponentDb {

    public List<PrintComponent> listAll(EmenuContext context);

    public void add(EmenuContext context, PrintComponent data);

    public void update(EmenuContext context, PrintComponent data);

    public void delete(EmenuContext context, int id);

    public PrintComponent get(EmenuContext context, int id);
}
