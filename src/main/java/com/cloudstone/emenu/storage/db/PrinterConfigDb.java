/**
 * @(#)PrinterDb.java, Aug 15, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.IPrinterConfigDb;
import com.cloudstone.emenu.data.PrinterConfig;

/**
 * @author xuhongfeng
 *
 */
public class PrinterConfigDb extends SQLiteDb implements IPrinterConfigDb {

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void add(PrinterConfig config) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public PrinterConfig get(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void update(PrinterConfig config) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<PrinterConfig> listByPrinter(String printer) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        // TODO Auto-generated method stub
        
    }

    
    /* ---------- SQL ---------- */
    public static final String TABLE_NAME = "printerConfig";
}