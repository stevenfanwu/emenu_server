/**
 * @(#)PrinterDb.java, Aug 15, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.PrinterConfig;
import com.cloudstone.emenu.util.JsonUtils;


/**
 * @author xuhongfeng
 */
@Repository
public class PrinterConfigDb extends JsonDb implements IPrinterConfigDb {
    private static final String TABLE_NAME = "printerConfig";

    public PrinterConfigDb() {
        super(TABLE_NAME);
    }

    @Override
    public void update(EmenuContext context, PrinterConfig config) {
        set(context, config.getName(), config);
    }

    @Override
    public PrinterConfig getConfig(EmenuContext context, String name) {
        return get(context, name, PrinterConfig.class);
    }

    @Override
    public void removeTemplate(EmenuContext context, int templateId) {
        for (PrinterConfig config : listAll(context)) {
            boolean needUpdate = false;
            if (ArrayUtils.contains(config.getOrderedTemplateIds(), templateId)) {
                int idx = ArrayUtils.indexOf(config.getOrderedTemplateIds(), templateId);
                int[] a = ArrayUtils.remove(config.getOrderedTemplateIds(), idx);
                config.setOrderedTemplateIds(a);
                needUpdate = true;
            }
            if (ArrayUtils.contains(config.getBillTemplateIds(), templateId)) {
                int idx = ArrayUtils.indexOf(config.getBillTemplateIds(), templateId);
                int[] a = ArrayUtils.remove(config.getBillTemplateIds(), idx);
                config.setBillTemplateIds(a);
                needUpdate = true;
            }
            if (ArrayUtils.contains(config.getCancelTemplateIds(), templateId)) {
                int idx = ArrayUtils.indexOf(config.getCancelTemplateIds(), templateId);
                int[] a = ArrayUtils.remove(config.getCancelTemplateIds(), idx);
                config.setCancelTemplateIds(a);
                needUpdate = true;
            }
            if (ArrayUtils.contains(config.getAddTemplateIds(), templateId)) {
                int idx = ArrayUtils.indexOf(config.getAddTemplateIds(), templateId);
                int[] a = ArrayUtils.remove(config.getAddTemplateIds(), idx);
                config.setAddTemplateIds(a);
                needUpdate = true;
            }
            if (needUpdate) {
                update(context, config);
            }
        }
    }

    private List<PrinterConfig> listAll(EmenuContext context) {
        List<String> jsonList = getAll(context);
        List<PrinterConfig> r = new LinkedList<PrinterConfig>();
        for (String json : jsonList) {
            r.add(JsonUtils.fromJson(json, PrinterConfig.class));
        }
        return r;
    }
}