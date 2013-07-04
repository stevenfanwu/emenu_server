/**
 * @(#)Const.java, Jun 1, 2013. 
 * 
 */
package com.cloudstone.emenu.constant;

/**
 * @author xuhongfeng
 *
 */
public class Const {
    public static final String PARAM_WEB_HOME_DIR = "emenu.home";
    
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
        
        public static final int SQUARE = 0;//方桌
        public static final int ROUNT = 1;//圆桌
    }
    
    public static class TipMode {//服务费收取模式
        public static final int NONE = 0;//无服务费
        public static final int FIXED = 1;//固定值
        public static final int PERCENTAGE = 2;//按比例
    }
}
