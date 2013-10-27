
package com.cloudstone.emenu.data.vo;

public class RecordVO {

    private String name;

    private int count;
    
    private String unitLabel;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setUnitLabel(String unitLabel) {
        this.unitLabel = unitLabel;
    }

    public String getUnitLabel() {
        return unitLabel;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
