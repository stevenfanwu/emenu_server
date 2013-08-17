/**
 * @(#)PrinterLogic.java, Aug 13, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.PrintComponent;
import com.cloudstone.emenu.data.PrintTemplate;
import com.cloudstone.emenu.data.PrinterConfig;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.OrderDishVO;
import com.cloudstone.emenu.exception.NotFoundException;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IPrintComponentDb;
import com.cloudstone.emenu.storage.db.IPrintTemplateDb;
import com.cloudstone.emenu.storage.db.IPrinterConfigDb;
import com.cloudstone.emenu.util.DataUtils;
import com.cloudstone.emenu.util.PrinterUtils;
import com.cloudstone.emenu.util.VelocityRender;

/**
 * @author xuhongfeng
 *
 */
@Component
public class PrinterLogic extends BaseLogic {
    private static final Logger LOG = LoggerFactory.getLogger(PrinterLogic.class);
    
    private static final String DIVIDER = "\n------------------------n";
    
    private static final String DISH_TEMPLATE = "\n" +
            "菜品\t数量*单价\t金额\n" +
            "#foreach($dish in $dishes)\n" +
            "$dish.name\t$dish.number*$dish.price\t$dish.totalCost\b" +
            "#end" +
            "\n";
    
    
    @Autowired
    private VelocityRender velocityRender;
    @Autowired
    private IPrintComponentDb printComponentDb;
    @Autowired
    private IPrintTemplateDb printTemplateDb;
    @Autowired
    private IPrinterConfigDb printerConfigDb;
    
    public String[] listPrinters() {
        return PrinterUtils.listPrinters();
    }
    public void printBill(Bill bill, User user) throws Exception {
        String[] printers = listPrinters();
        for (String printer:printers) {
            PrinterConfig config = getPrinterConfig(printer);
            if (config != null && config.isWhenBill()) {
                for (int templateId:config.getBillTemplateIds()) {
                    LOG.info("print templateId :" + templateId);
                    printBill(bill, user, printer, templateId);
                }
            }
        }
    }
    
    public void printBill(Bill bill, User user, String printer, int templateId) throws Exception {
        PrintTemplate template = getTemplate(templateId);
        if (template != null) {
            StringBuilder sb = new StringBuilder();
            int headerId = template.getHeaderId();
            int footerId = template.getFooterId();
            
            if (headerId != 0) {
                PrintComponent header = getComponent(headerId);
                if (header != null) {
                    sb.append(header.getContent());
                    sb.append(DIVIDER);
                }
            }
            sb.append(DISH_TEMPLATE);
            
            if (footerId != 0) {
                PrintComponent footer = getComponent(footerId);
                if (footer != null) {
                    sb.append(DIVIDER);
                    sb.append(footer.getContent());
                }
            }
            if (template.getCutType() == Const.CutType.PER_DISH && bill.getOrder().getDishes().size()>0) {
                for (OrderDishVO dish:bill.getOrder().getDishes()) {
                    List<OrderDishVO> dishes = new LinkedList<OrderDishVO>();
                    dishes.add(dish);
                    String content = velocityRender.renderBill(bill, user, dishes, sb.toString());
                    PrinterUtils.print(printer, content);
                }
            } else {
                String content = velocityRender.renderBill(bill, user, bill.getOrder().getDishes(), sb.toString());
                PrinterUtils.print(printer, content);
            }
        }
    }
    
    public PrintTemplate getTemplate(int id) {
        return printTemplateDb.get(id);
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
            printerConfigDb.removeTemplate(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    public PrinterConfig getPrinterConfig(String name) {
        PrinterConfig config = printerConfigDb.getConfig(name);
        if (config == null) {
            config = new PrinterConfig();
            config.setName(name);
            printerConfigDb.update(config);
        }
        return config;
    }
    
    public PrinterConfig updatePrinterConfig(PrinterConfig config) {
        printerConfigDb.update(config);
        return printerConfigDb.getConfig(config.getName());
    }
    
    public List<PrinterConfig> listPrinterConfig() {
        String[] printers = listPrinters();
        List<PrinterConfig> configs = new ArrayList<PrinterConfig>();
        for (String name:printers) {
            configs.add(getPrinterConfig(name));
        }
        return configs;
    }
}
