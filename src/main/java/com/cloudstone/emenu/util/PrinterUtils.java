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
        for (PrintService p:printService) {
            if (p.getName().equals(printerName)) {
                ps = p;
                break;
            }
        }
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        String GS = String.valueOf((char)29);
        String ESC = String.valueOf((char)27);
        String COMMAND = "";
        COMMAND = ESC + "@";
        COMMAND += GS + "V" + (char)1;
        
        out.write(content.getBytes());
        out.write("\n\n\n".getBytes());
        out.write(COMMAND.getBytes());
        Doc doc = new SimpleDoc(out.toByteArray(),flavor,null);
        DocPrintJob job = ps.createPrintJob();
        job.print(doc, null);
    }
}