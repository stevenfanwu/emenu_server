/**
 * @(#)Const.java, Jun 1, 2013. 
 * 
 */
package com.cloudstone.emenu.constant;

import com.cloudstone.emenu.util.PrinterUtils;

/**
 * @author xuhongfeng
 *
 */
public class Const {
    public static final String PARAM_WEB_HOME_DIR = "emenu.home";
    public static final String PARAM_TOMCAT_HOME = "tomcat.home";
    public static final String PARAM_CLOUDSTONE_DATA_DIR = "cloudstone.data.dir";
    public static final String PARAM_DB_FILE = "cloudstone.db";
    
    public static class UserType {
        public static final int USER = 0;
        public static final int ADMIN = 1;
        public static final int SUPER_USER = 2;
        
        //for velocity
        public int getUSER() {
            return USER;
        }
        public int getADMIN() {
            return ADMIN;
        }
        public int getSUPER_USER() {
            return SUPER_USER;
        }
    }
    
    public static class TableType {
        public static final int ROOM = 0;//包间
        public static final int HALL = 1;//散台
        public static final int BOOTH = 2;//卡座
    }
    
    public static class TableStatus {
        public static final int EMPTY = 0;
        public static final int OCCUPIED = 1;
        public static final int ORDERED = 2;
    }
    
    public static class TipMode {//服务费收取模式
        public static final int NONE = 0;//无服务费
        public static final int FIXED = 1;//固定值
        public static final int PERCENTAGE = 2;//按比例
    }
    
    public static class DishUnit {
        public static final int FEN = 0; // 份
        public static final int JIN = 1; // 斤
        public static final int GE = 2; //个
        public static final int ZHI = 3; //只
        public static final int PAN = 4; //盘
        public static final int PING = 5; //瓶
        public static final int BEI = 6; //杯
        public static final int LI = 7;
        public static final int WEI = 8;
        
        public static String getLabel(int type) {
            switch(type) {
                case 0: return "份";
                case 1: return "斤";
                case 2: return "个";
                case 3: return "只";
                case 4: return "盘";
                case 5: return "瓶";
                case 6: return "杯";
                case 7: return "例";
                case 8: return "位";
            }
            throw new RuntimeException("Unknow type");
        }
    }
    
    public static class TypeUnit {
        public static final int NORMAL = 0; //正常
        public static final int CANCEL = 1; //退
        public static final int ADD = 2; //加
        public static final int FREE = 3; //赠
        
        public static String getLabel(int type) {
            switch(type) {
                case 0: return "";
                case 1: return "退";
                case 2: return "加";
                case 3: return "赠";
            }
            throw new RuntimeException("Unknow type");
        }
    }
    
    public static class DishStatus {
        public static final int STATUS_INIT = 0;
        public static final int STATUS_IN_MENU = 1;
    }
    
    public static class OrderDishStatus {
        public static final int ORDERED = 0;
        public static final int SERVED = 1;
        public static final int TAKE_OUT = 2;
        public static final int WAITING = 3;
        public static final int CANCELED = 4;
    }
    
    public static class OrderStatus {
        public static final int ORDERED = 0;
        public static final int PAYED = 1;
    }
    
    public static class UserAgent {
        public static final String CLOUD_HAND = "CloudHand";
    }
    
    public static class CutType {
        public static final int PER_ORDER = 0;
        public static final int PER_DISH = 1;
    }

    public static final String DIVIDER = "\n---------------------------------------\n";

    public static final String DISH_TEMPLATE = "\n" +
            "菜品" + PrinterUtils.absoluteHorizontalPosition(1, 0) + "单价    数量"
                + PrinterUtils.absoluteHorizontalPosition(1, 125) + "    金额\n" +
            "#foreach($group in $dishGroups)\n" +
                "【$group.category】\n" +
                "#foreach($dish in $group.dishes)\n" +
                    "$dish.name" + PrinterUtils.absoluteHorizontalPosition(1, 0) + 
                    "$dish.price    $dish.number$dish.unitLabel" + PrinterUtils.absoluteHorizontalPosition(1, 125) + 
                    "    $dish.totalCost\n" + 
                    "#if($dish.remarks && $dish.remarks.size() != 0)\n" +
                        "  做法: " +
                        "#foreach($remark in $dish.remarks)\n" +
                            "$remark" + " " +
                        "#end\n" + "\n" +
                    "#end\n" +
                "#end\n" +
            "#end\n" +
            "【退菜】\n" +
            "#foreach($rec in $cancelrecord)\n" +
                "$rec.name" + PrinterUtils.absoluteHorizontalPosition(1, 0) +
                "$rec.price    $rec.count$rec.unitLabel" + PrinterUtils.absoluteHorizontalPosition(1, 125) + 
                "    $rec.total\n" + 
            "#end\n" +
            "【加菜】\n" +
            "#foreach($rec in $addrecord)\n" +
                "$rec.name" + PrinterUtils.absoluteHorizontalPosition(1, 0) +
                "$rec.price    $rec.count$rec.unitLabel" + PrinterUtils.absoluteHorizontalPosition(1, 125) + 
                "    $rec.total\n" + 
            "#end\n" +
            "\n";

    public static final String DISH_TEMPLATE_ORDER = "\n" +
            "菜品" + PrinterUtils.absoluteHorizontalPosition(1, 0) + "      数量" + "\n" +
            "#foreach($group in $dishGroups)\n" +
                "【$group.category】\n" +
                "#foreach($dish in $group.dishes)\n" +
                    "$dish.name" + PrinterUtils.absoluteHorizontalPosition(1, 0) + 
                    "      $dish.number$dish.unitLabel" + "\n" + 
                    "#if($dish.remarks && $dish.remarks.size() != 0)\n" +
                        "  做法: " +
                        "#foreach($remark in $dish.remarks)\n" +
                            "$remark" + " " +
                        "#end\n" + "\n" +
                    "#end\n" +
                "#end\n" +
            "#end\n" +
            "\n";

}
