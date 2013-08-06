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
    private double price;
    private double memberPrice;
    private int unit;
    private int spicy;
    private boolean specialPrice = false;
    private boolean nonInt = false; //是否允许小数份
    private String desc;
    private String imageId;
    private int status = Const.DishStatus.STATUS_INIT;
    private String pinyinfull;
    private String pinyinsimple;
    
    private String uriData;
    
    public Dish() {
        super();
    }

    public Dish(Dish dish) {
        super(dish);
        setPrice(dish.getPrice());
        setMemberPrice(dish.getMemberPrice());
        setUnit(dish.getUnit());
        setSpicy(dish.getSpicy());
        setSpecialPrice(dish.isSpecialPrice());
        setNonInt(dish.isNonInt());
        setDesc(dish.getDesc());
        setImageId(dish.getImageId());
        setStatus(dish.getStatus());
        setUriData(dish.getUriData());
    }

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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUriData() {
        return uriData;
    }

    public void setUriData(String uriData) {
        this.uriData = uriData;
    }

    public String getPinyinFull() {
        return pinyinfull;
    }

    public void setPinyinFull(String pinyinFull) {
        this.pinyinfull = pinyinFull;
    }

    public String getPinyinSimple() {
        return pinyinsimple;
    }

    public void setPinyinSimple(String pinyinSimple) {
        this.pinyinsimple = pinyinSimple;
    }
}
