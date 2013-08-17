/**
 * @(#)PrinterUtils.java, Aug 8, 2013. 
 * 
 */
package com.cloudstone.emenu.util;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xuhongfeng
 *
 */
public class PrinterUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PrinterUtils.class);
        
    private static final String ESC = String.valueOf((char)27);
    private static final String GS = String.valueOf((char)29);
    private static final String INIT = ESC + "@";
    private static final String CUT = GS + "V" + (char)1;
    
    public static String[] listPrinters() {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        
        List<String> printers = new ArrayList<String>();
        if (printService == null) {
            LOG.info("print services = null");
        } else {
            for (PrintService p:printService) {
                LOG.info("PrintService : " + p.getName());
                printers.add(p.getName());
            }
        }
        
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        if (defaultService == null) {
            LOG.info("Defaut PrintService =  null");
        } else {
            LOG.info("Default : " + defaultService.getName());
            printers.remove(defaultService.getName());
            printers.add(0, defaultService.getName());
        }
        
        return printers.toArray(new String[0]);
    }
    
    public static void print(String printerName, String content) throws Exception {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService ps = null;
        LOG.info("printer :" + printerName);
        LOG.info("content: + " + content);
        for (PrintService p:printService) {
            if (p.getName().equals(printerName)) {
                ps = p;
                break;
            }
        }
        if (ps == null) {
            ps = PrintServiceLookup.lookupDefaultPrintService();
        }
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        out.write(content.getBytes());
        out.write(INIT.getBytes());
        out.write(CUT.getBytes());
        
        Doc doc = new SimpleDoc(out.toByteArray(), flavor,null);
        DocPrintJob job = ps.createPrintJob();
        job.print(doc, null);
    }
}