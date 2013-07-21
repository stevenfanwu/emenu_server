/**
 * @(#)IdName.java, Jul 21, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class IdName extends BaseData {
    private long id;
    private String name;
    
    public IdName() {
        super();
    }
    
    public IdName(IdName other) {
        super();
        this.id = other.id;
        this.name = other.name;
    }
    
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
}
