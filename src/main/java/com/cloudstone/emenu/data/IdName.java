/**
 * @(#)IdName.java, Jul 21, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class IdName extends IEntity {
    private String name;
    
    public IdName() {
        super();
    }
    
    public IdName(IdName other) {
        super();
        this.name = other.name;
        setId(other.getId());
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
