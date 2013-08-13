/**
 * @(#)PrinterLogic.java, Aug 13, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.PrintComponent;
import com.cloudstone.emenu.storage.db.IPrintComponentDb;
import com.cloudstone.emenu.util.DataUtils;
import com.cloudstone.emenu.util.PrinterUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class PrinterLogic extends BaseLogic {
    @Autowired
    private IPrintComponentDb printComponentDb;
    
    public String[] listPrinters() {
        return PrinterUtils.listPrinters();
    }
    public void print(String printerName, String content) throws Exception {
        PrinterUtils.print(printerName, content);
    }
    
    public List<PrintComponent> listComponents() {
        List<PrintComponent> list = printComponentDb.listAll();
        DataUtils.filterDeleted(list);
        return list;
    }
    
    public PrintComponent addComponent(PrintComponent data) {
        printComponentDb.add(data);
        return printComponentDb.get(data.getId());
    }
}
