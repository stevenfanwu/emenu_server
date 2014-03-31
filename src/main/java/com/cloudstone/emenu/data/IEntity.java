/**
 * @(#)IEntity.java, Jul 27, 2013. 
 *
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 */
public class IEntity extends BaseData {
    private int id;

    public IEntity() {
    }

    public IEntity(IEntity other) {
        super(other);
        id = other.id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
