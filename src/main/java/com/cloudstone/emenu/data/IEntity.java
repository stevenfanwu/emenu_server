/**
 * @(#)IEntity.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.data;

/**
 * @author xuhongfeng
 *
 */
public class IEntity extends BaseData {
    private int id;

    // Id of the restaurant the user belongs to
    private int restaurantId;
    
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

    public int getRestaurantId() {
      return restaurantId;
   }

    public void setRestaurantId(int restaurantId) {
      this.restaurantId = restaurantId;
   }
}
