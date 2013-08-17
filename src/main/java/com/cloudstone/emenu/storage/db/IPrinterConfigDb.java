/**
 * @(#)IPrinterConfigDb.java, Aug 16, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import com.cloudstone.emenu.data.PrinterConfig;


/**
 * @author xuhongfeng
 *
 */
public interface IPrinterConfigDb {
    public void update(PrinterConfig config);
    public PrinterConfig getConfig(String name);
    public void removeTemplate(int templateId);
}