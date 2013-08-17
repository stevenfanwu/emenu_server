/**
 * @(#)PrinterDb.java, Aug 15, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.PrinterConfig;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.util.JsonUtils;


/**
 * @author xuhongfeng
 *
 */
@Repository
public class PrinterConfigDb extends JsonDb implements IPrinterConfigDb {
    private static final String TABLE_NAME = "printerConfig";

    public PrinterConfigDb() {
        super(TABLE_NAME);
    }

    @Override
    public void update(PrinterConfig config) {
        set(config.getName(), config);
    }

    @Override
    public PrinterConfig getConfig(String name) {
        try {
            return get(name, PrinterConfig.class);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void removeTemplate(int templateId) {
        for (PrinterConfig config:listAll()) {
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
            if (needUpdate) {
                update(config);
            }
        }
    }

    private List<PrinterConfig> listAll() {
        List<String> jsonList = getAll();
        List<PrinterConfig> r = new LinkedList<PrinterConfig>();
        for (String json:jsonList) {
            r.add(JsonUtils.fromJson(json, PrinterConfig.class));
        }
        return r;
    }
}