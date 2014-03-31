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
 */
public class PrinterUtils {
    private static final Logger LOG = LoggerFactory.getLogger(PrinterUtils.class);

    private static final String ESC = String.valueOf((char) 27);
    private static final String GS = String.valueOf((char) 29);
    private static final String CR = String.valueOf((char) 13); //carriage return
    private static final String TAB = String.valueOf((char) 9); //horizontal tab
    private static final String FF = String.valueOf((char) 12); //form feed
    private static final String LINE = String.valueOf((char) 10); //line feed/new line
    private static final String $ = String.valueOf((char) 36); //used for absolute horizontal positioning
    private static final String g = String.valueOf((char) 103); //15cpi pitch
    private static final String l = String.valueOf((char) 108);
    private static final String Q = String.valueOf((char) 81);
    private static final String Segment = String.valueOf((char) 45);

    public static final String INIT = ESC + "@";
    public static final String CUT = GS + "V" + (char) 1;
    public static final String FORM_FIELD = CR + FF;
    public static final String LINE_FIELD = CR + LINE;

    private static final float CM_PER_INCH = 2.54f;

    public static String[] listPrinters() {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);

        List<String> printers = new ArrayList<String>();
        if (printService == null) {
            LOG.info("print services = null");
        } else {
            for (PrintService p : printService) {
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

    public static void print(String printerName, String content, int fontSize) throws Exception {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);
        PrintService ps = null;
        LOG.info("printer :" + printerName);
        LOG.info("content: + " + content);
        for (PrintService p : printService) {
            if (p.getName().equals(printerName)) {
                ps = p;
                break;
            }
        }
        if (ps == null) {
            ps = PrintServiceLookup.lookupDefaultPrintService();
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        out.write(INIT.getBytes());
//        out.write(select15CPI().getBytes());
//        out.write(setLeftMargin(1).getBytes());
        out.write(setRightMargin(10).getBytes());

        //Font Size
        out.write(0x1b);
        out.write(0x21);
        out.write(String.valueOf((char) (fontSize)).getBytes());
        out.write(0x1d);
        out.write(0x21);
        out.write(String.valueOf((char) (fontSize)).getBytes());

        content = content.replace("\n", LINE_FIELD);
        content = content.replace("\t", TAB);
        out.write(content.getBytes());

        out.write(FORM_FIELD.getBytes());
        out.write(CUT.getBytes());

        Doc doc = new SimpleDoc(out.toByteArray(), flavor, null);
        DocPrintJob job = ps.createPrintJob();
        job.print(doc, null);
    }
//    
//    public static String absoluteHorizontalPosition(float centimeters) {
//        //pre: centimenters >= 0 (cm)
//        //post: sets absolute horizontal print position to x centimeters from left margin
//        float inches = centimeters / CM_PER_INCH;
//        int units_low = (int) (inches * 60) % 256;
//        int units_high = (int) (inches * 60) / 256;
//        
//        return ESC + $ + String.valueOf((char)units_low) + String.valueOf((char)units_high);
//    }

    public static String setWordLine() {
        return ESC + Segment + String.valueOf((char) 1);
    }

    public static String cancelWordLine() {
        return ESC + Segment + String.valueOf((char) 0);
    }

    public static String absoluteHorizontalPosition(int hight, int low) {
        return ESC + $ + String.valueOf((char) low) + String.valueOf((char) hight);
    }

    public static String select15CPI() { //15 characters per inch (condensend not available)
        return ESC + g;
    }

    public static String setLeftMargin(int left) {
        return ESC + l + String.valueOf((char) left);
    }

    public static String setRightMargin(int right) {
        return ESC + Q + String.valueOf((char) right);
    }
}