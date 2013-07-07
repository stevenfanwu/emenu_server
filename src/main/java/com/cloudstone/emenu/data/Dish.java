/**
 * @(#)Dish.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class Dish extends BaseData {
    private long id;
    private String name;
    private int type;
    private double price;
    private double memberPrice;
    private int unit;
    private int spicy;
    private boolean specialPrice = false;
    private boolean nonInt = false; //是否允许小数份
    private String desc;
    private String imageData;// data:image/png;base64,...
    
    //check data
    private int[] menuIds = new int[0];

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int[] getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(int[] menuIds) {
        this.menuIds = menuIds;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
