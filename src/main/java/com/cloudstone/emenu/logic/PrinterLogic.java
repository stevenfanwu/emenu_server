/**
 * @(#)PrinterLogic.java, Aug 13, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.PrintComponent;
import com.cloudstone.emenu.data.PrintTemplate;
import com.cloudstone.emenu.exception.NotFoundException;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IPrintComponentDb;
import com.cloudstone.emenu.storage.db.IPrintTemplateDb;
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
    @Autowired
    private IPrintTemplateDb printTemplateDb;
    
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
    
    public PrintComponent updateComponent(PrintComponent data) {
        PrintComponent old = printComponentDb.get(data.getId());
        if (old==null || old.isDeleted()) {
            throw new NotFoundException("该页眉页脚不存在");
        }
        printComponentDb.update(data);
        return printComponentDb.get(data.getId());
    }
    
    public PrintComponent getComponent(int id) {
        return printComponentDb.get(id);
    }
    
    public void deleteComponent(int id) {
        PrintComponent old = printComponentDb.get(id);
        if (old==null || old.isDeleted()) {
            throw new NotFoundException("该页眉页脚不存在");
        }
        try {
            printComponentDb.delete(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
        printTemplateDb.removeComponent(id);
    }
    
    public List<PrintTemplate> listTemplate() {
        List<PrintTemplate> list = printTemplateDb.listAll();
        DataUtils.filterDeleted(list);
        return list;
    }
    
    public PrintTemplate addTemplate(PrintTemplate template) {
        printTemplateDb.add(template);
        return printTemplateDb.get(template.getId());
    }
    
    public PrintTemplate updateTemplate(PrintTemplate template) {
        int headerId = template.getHeaderId();
        if (headerId != 0) {
            PrintComponent header = getComponent(headerId);
            if (header==null || header.isDeleted()) {
                throw new NotFoundException("该页眉不存在");
            }
        }
        int footerId = template.getFooterId();
        if (footerId != 0) {
            PrintComponent footer = getComponent(footerId);
            if (footer==null || footer.isDeleted()) {
                throw new NotFoundException("该页脚不存在");
            }
        }
        printTemplateDb.update(template);
        return printTemplateDb.get(template.getId());
    }
    
    public void deleteTemplate(int id) {
        try {
            printTemplateDb.delete(id);
            //TODO update printConfig
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
}
