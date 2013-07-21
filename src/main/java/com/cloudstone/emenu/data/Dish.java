/**
 * @(#)Dish.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.data;

import com.cloudstone.emenu.constant.Const;

/**
 * @author xuhongfeng
 *
 */
public class Dish extends IdName {
    private int type;
    private double price;
    private double memberPrice;
    private int unit;
    private int spicy;
    private boolean specialPrice = false;
    private boolean nonInt = false; //是否允许小数份
    private String desc;
    private String imageData;// data:image/png;base64,...
    private int status = Const.DishStatus.STATUS_INIT;
    
    /**
     * id < 0 means NullDish
     * @param pos
     * @return
     */
    public static Dish getNullDish(int pos) {
        Dish dish = new Dish();
        dish.setId(0-pos);
        return dish;
    }
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(double memberPrice) {
        this.memberPrice = memberPrice;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getSpicy() {
        return spicy;
    }

    public void setSpicy(int spicy) {
        this.spicy = spicy;
    }

    public boolean isSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(boolean specialPrice) {
        this.specialPrice = specialPrice;
    }

    public boolean isNonInt() {
        return nonInt;
    }

    public void setNonInt(boolean nonInt) {
        this.nonInt = nonInt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
