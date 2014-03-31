package com.cloudstone.emenu.data;

/**
 * @author carelife
 */
public class Vip extends IdName {
    private int sex;
    private String idCard;
    private String phone;
    private String email;
    private String address;
    private String company;
    private double money;
    private String tag;

    public Vip() {
        super();
    }

    public Vip(Vip other) {
        super(other);
        this.sex = other.sex;
        this.idCard = other.idCard;
        this.phone = other.phone;
        this.email = other.email;
        this.address = other.address;
        this.company = other.company;
        this.money = other.money;
        this.tag = other.tag;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
