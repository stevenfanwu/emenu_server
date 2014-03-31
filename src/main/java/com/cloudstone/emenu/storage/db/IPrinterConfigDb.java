/**
 * @(#)IPrinterConfigDb.java, Aug 16, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.PrinterConfig;


/**
 * @author xuhongfeng
 */
public interface IPrinterConfigDb {
    public void update(EmenuContext context, PrinterConfig config);

    public PrinterConfig getConfig(EmenuContext context, String name);

    public void removeTemplate(EmenuContext context, int templateId);
}