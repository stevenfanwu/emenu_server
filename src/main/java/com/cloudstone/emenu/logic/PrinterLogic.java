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

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.data.Bill;
import com.cloudstone.emenu.data.PrintComponent;
import com.cloudstone.emenu.data.PrintTemplate;
import com.cloudstone.emenu.data.PrinterConfig;
import com.cloudstone.emenu.data.User;
import com.cloudstone.emenu.data.vo.OrderDishVO;
import com.cloudstone.emenu.data.vo.OrderVO;
import com.cloudstone.emenu.exception.NotFoundException;
import com.cloudstone.emenu.storage.db.IPrintComponentDb;
import com.cloudstone.emenu.storage.db.IPrintTemplateDb;
import com.cloudstone.emenu.storage.db.IPrinterConfigDb;
import com.cloudstone.emenu.util.DataUtils;
import com.cloudstone.emenu.util.PrinterUtils;
import com.cloudstone.emenu.util.VelocityRender;
import com.cloudstone.emenu.wrap.DishWraper;

/**
 * @author xuhongfeng
 *
 */
@Component
public class PrinterLogic extends BaseLogic {
    
    private static final Logger LOG = LoggerFactory.getLogger(PrinterLogic.class);
    
    @Autowired
    private DishWraper dishWraper;
    
    private static final String DIVIDER = "\n---------------------------------------\n";
    
//    private static final String DISH_TEMPLATE = "\n" +
//            "菜品\t数量*单价\t金额\n" +
//            "#foreach($dish in $dishes)\n" +
//            "$dish.name\t$dish.number*$dish.price\t$dish.totalCost\n" +
//            "#end" +
//            "\n";
    

    private static final String DISH_TEMPLATE = "\n" +
            "菜品" + PrinterUtils.absoluteHorizontalPosition(1, 0) + "数量*单价"
                + PrinterUtils.absoluteHorizontalPosition(1, 125) + "金额\n" +
            "#foreach($group in $dishGroups)\n" +
                "【$group.category】\n" +
                "#foreach($dish in $group.dishes)\n" +
                    "$dish.name" + PrinterUtils.absoluteHorizontalPosition(1, 0) + 
                    "$dish.number*$dish.price" + PrinterUtils.absoluteHorizontalPosition(1, 125) + 
                    "$dish.totalCost\n" + "做法:" +
                    "#foreach($remark in $dish.remarks)\n" +
                        "$remark" + " " +
                    "#end" +
                "#end" +
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
    public void printBill(EmenuContext context, Bill bill, User user) throws Exception {
        String[] printers = listPrinters();
        for (String printer:printers) {
            PrinterConfig config = getPrinterConfig(context, printer);
            if (config != null && config.isWhenBill()) {
                for (int templateId:config.getBillTemplateIds()) {
                    LOG.info("print templateId :" + templateId);
                    printBill(context, bill, user, printer, templateId);
                }
            }
        }
    }
    
    public void printBill(EmenuContext context, Bill bill, User user, String printer, int templateId) throws Exception {
        PrintTemplate template = getTemplate(context, templateId);
        if (template != null) {
            String templateString = getTemplateString(context, template);
            if (template.getCutType() == Const.CutType.PER_DISH && bill.getOrder().getDishes().size()>0) {
                for (OrderDishVO dish:bill.getOrder().getDishes()) {
                    List<OrderDishVO> dishes = new LinkedList<OrderDishVO>();
                    dishes.add(dish);
                    String content = velocityRender.renderBill(bill,
                            user, dishWraper.wrapDishGroup(context, dishes), templateString);
                    PrinterUtils.print(printer, content);
                }
            } else {
                String content = velocityRender.renderBill(bill, user,
                        dishWraper.wrapDishGroup(context, bill.getOrder().getDishes()),
                        templateString);
                PrinterUtils.print(printer, content);
            }
        }
    }
    
    private String getTemplateString(EmenuContext context, PrintTemplate template) {
        StringBuilder sb = new StringBuilder();
        int headerId = template.getHeaderId();
        int footerId = template.getFooterId();
        
        if (headerId != 0) {
            PrintComponent header = getComponent(context, headerId);
            if (header != null) {
                sb.append(header.getContent());
                sb.append(DIVIDER);
            }
        }
        sb.append(DISH_TEMPLATE);
        
        if (footerId != 0) {
            PrintComponent footer = getComponent(context, footerId);
            if (footer != null) {
                sb.append(DIVIDER);
                sb.append(footer.getContent());
            }
        }
        return sb.toString();
    }
    
    public void printOrder(EmenuContext context, OrderVO order, User user) throws Exception {
        String[] printers = listPrinters();
        for (String printer:printers) {
            PrinterConfig config = getPrinterConfig(context, printer);
            if (config != null && config.isWhenOrdered()) {
                for (int templateId:config.getOrderedTemplateIds()) {
                    LOG.info("print templateId :" + templateId);
                    printOrder(context, order, user, printer, templateId);
                }
            }
        }
    }
    
    public void printOrder(EmenuContext context, OrderVO order, User user, String printer, int templateId) throws Exception {
        PrintTemplate template = getTemplate(context, templateId);
        if (template != null) {
            String templateString = getTemplateString(context, template);
            if (template.getCutType() == Const.CutType.PER_DISH && order.getDishes().size()>0) {
                for (OrderDishVO dish:order.getDishes()) {
                    List<OrderDishVO> dishes = new LinkedList<OrderDishVO>();
                    dishes.add(dish);
                    String content = velocityRender.renderOrder(order, user,
                            dishWraper.wrapDishGroup(context, dishes), templateString);
                    PrinterUtils.print(printer, content);
                }
            } else {
                String content = velocityRender.renderOrder(order, user,
                        dishWraper.wrapDishGroup(context,order.getDishes()),
                        templateString);
                PrinterUtils.print(printer, content);
            }
        }
    }
    
    public PrintTemplate getTemplate(EmenuContext context, int id) {
        return printTemplateDb.get(context, id);
    }
    
    public List<PrintComponent> listComponents(EmenuContext context) {
        List<PrintComponent> list = printComponentDb.listAll(context);
        DataUtils.filterDeleted(list);
        return list;
    }
    
    public PrintComponent addComponent(EmenuContext context, PrintComponent data) {
        printComponentDb.add(context, data);
        return printComponentDb.get(context, data.getId());
    }
    
    public PrintComponent updateComponent(EmenuContext context, PrintComponent data) {
        PrintComponent old = printComponentDb.get(context, data.getId());
        if (old==null || old.isDeleted()) {
            throw new NotFoundException("该页眉页脚不存在");
        }
        printComponentDb.update(context, data);
        return printComponentDb.get(context, data.getId());
    }
    
    public PrintComponent getComponent(EmenuContext context, int id) {
        return printComponentDb.get(context, id);
    }
    
    public void deleteComponent(EmenuContext context, int id) {
        PrintComponent old = printComponentDb.get(context, id);
        if (old==null || old.isDeleted()) {
            throw new NotFoundException("该页眉页脚不存在");
        }
        printComponentDb.delete(context, id);
        printTemplateDb.removeComponent(context, id);
    }
    
    public List<PrintTemplate> listTemplate(EmenuContext context) {
        List<PrintTemplate> list = printTemplateDb.listAll(context);
        DataUtils.filterDeleted(list);
        return list;
    }
    
    public PrintTemplate addTemplate(EmenuContext context, PrintTemplate template) {
        printTemplateDb.add(context, template);
        return printTemplateDb.get(context, template.getId());
    }
    
    public PrintTemplate updateTemplate(EmenuContext context, PrintTemplate template) {
        int headerId = template.getHeaderId();
        if (headerId != 0) {
            PrintComponent header = getComponent(context, headerId);
            if (header==null || header.isDeleted()) {
                throw new NotFoundException("该页眉不存在");
            }
        }
        int footerId = template.getFooterId();
        if (footerId != 0) {
            PrintComponent footer = getComponent(context, footerId);
            if (footer==null || footer.isDeleted()) {
                throw new NotFoundException("该页脚不存在");
            }
        }
        printTemplateDb.update(context, template);
        return printTemplateDb.get(context, template.getId());
    }
    
    public void deleteTemplate(EmenuContext context, int id) {
        printTemplateDb.delete(context, id);
        printerConfigDb.removeTemplate(context, id);
    }
    
    public PrinterConfig getPrinterConfig(EmenuContext context, String name) {
        PrinterConfig config = printerConfigDb.getConfig(context, name);
        if (config == null) {
            config = new PrinterConfig();
            config.setName(name);
            printerConfigDb.update(context, config);
        }
        return config;
    }
    
    public PrinterConfig getPrinterConfig(EmenuContext context, int id) {
        String[] printers = listPrinters();
        for (String p:printers) {
            if (p.hashCode() == id) {
                return getPrinterConfig(context, p);
            }
        }
        return null;
    }
    
    public PrinterConfig updatePrinterConfig(EmenuContext context, PrinterConfig config) {
        printerConfigDb.update(context, config);
        return printerConfigDb.getConfig(context, config.getName());
    }
    
    public List<PrinterConfig> listPrinterConfig(EmenuContext context) {
        String[] printers = listPrinters();
        List<PrinterConfig> configs = new ArrayList<PrinterConfig>();
        for (String name:printers) {
            configs.add(getPrinterConfig(context, name));
        }
        return configs;
    }
}
