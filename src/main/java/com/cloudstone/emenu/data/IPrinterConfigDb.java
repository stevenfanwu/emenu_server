/**
 * @(#)IPrinterConfigDb.java, Aug 15, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

import java.util.List;

/**
 * @author xuhongfeng
 *
 */
public interface IPrinterConfigDb {
    public void add(PrinterConfig config);
    public PrinterConfig get(int id);
    public void update(PrinterConfig config);
    public List<PrinterConfig> listByPrinter(String printer);
}
