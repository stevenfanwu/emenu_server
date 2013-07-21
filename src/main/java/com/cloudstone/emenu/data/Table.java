/**
 * @(#)Table.java, 2013-7-4. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class Table extends IdName {
    private int type;//类型
    private int shape;//桌型
    private int capacity;//最多人数
    private double minCharge;//最低消费
    private int tipMode;//服务费收取模式
    private double tip;
    
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getShape() {
        return shape;
    }
    public void setShape(int shape) {
        this.shape = shape;
    }
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
    public double getMinCharge() {
        return minCharge;
    }
    public void setMinCharge(double minCharge) {
        this.minCharge = minCharge;
    }
    public int getTipMode() {
        return tipMode;
    }
    public void setTipMode(int tipMode) {
        this.tipMode = tipMode;
    }
    public double getTip() {
        return tip;
    }
    public void setTip(double tip) {
        this.tip = tip;
    }
}
