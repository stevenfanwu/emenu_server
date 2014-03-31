/**
 * @(#)PrinterConfig.java, Aug 14, 2013. 
 *
 */
package com.cloudstone.emenu.data;

import com.cloudstone.emenu.constant.EmptyConst;

/**
 * @author xuhongfeng
 */
public class PrinterConfig {
    private int id = -1;
    private String name;
    private boolean whenOrdered;
    private int[] orderedTemplateIds = EmptyConst.EMPTY_INT_ARRAY;
    private boolean whenBill;
    private int[] billTemplateIds = EmptyConst.EMPTY_INT_ARRAY;
    private boolean whenCancel;
    private int[] cancelTemplateIds = EmptyConst.EMPTY_INT_ARRAY;
    private boolean whenAdd = true;
    private int[] addTemplateIds = EmptyConst.EMPTY_INT_ARRAY;

    public boolean isWhenAdd() {
        return whenAdd;
    }

    public void setWhenAdd(boolean whenAdd) {
        this.whenAdd = whenAdd;
    }

    public int[] getAddTemplateIds() {
        return addTemplateIds;
    }

    public void setAddTemplateIds(int[] addTemplateIds) {
        this.addTemplateIds = addTemplateIds;
    }

    public boolean isWhenCancel() {
        return whenCancel;
    }

    public void setWhenCancel(boolean whenCancel) {
        this.whenCancel = whenCancel;
    }

    public int[] getCancelTemplateIds() {
        return cancelTemplateIds;
    }

    public void setCancelTemplateIds(int[] cancelTemplateIds) {
        this.cancelTemplateIds = cancelTemplateIds;
    }

    public boolean isWhenOrdered() {
        return whenOrdered;
    }

    public void setWhenOrdered(boolean whenOrdered) {
        this.whenOrdered = whenOrdered;
    }

    public int[] getOrderedTemplateIds() {
        return orderedTemplateIds;
    }

    public void setOrderedTemplateIds(int[] orderedTemplateIds) {
        this.orderedTemplateIds = orderedTemplateIds;
    }

    public boolean isWhenBill() {
        return whenBill;
    }

    public void setWhenBill(boolean whenBill) {
        this.whenBill = whenBill;
    }

    public int[] getBillTemplateIds() {
        return billTemplateIds;
    }

    public void setBillTemplateIds(int[] billTemplateIds) {
        this.billTemplateIds = billTemplateIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.id = name.hashCode();
    }

    public int getId() {
        if (id == -1) {
            id = name.hashCode();
        }
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}