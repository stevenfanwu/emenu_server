/**
 * @(#)IPrintTemplateDb.java, Aug 15, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.PrintTemplate;

/**
 * @author xuhongfeng
 */
public interface IPrintTemplateDb {

    public void add(EmenuContext context, PrintTemplate template);

    public void update(EmenuContext context, PrintTemplate template);

    public PrintTemplate get(EmenuContext context, int id);

    public List<PrintTemplate> listAll(EmenuContext context);

    public void delete(EmenuContext context, int id);

    public void removeComponent(EmenuContext context, int componentId);
}
