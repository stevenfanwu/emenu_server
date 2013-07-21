/**
 * @(#)IDishPageDb.java, Jul 20, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.storage.db.RelationDb.Relation;

/**
 * @author xuhongfeng
 *
 */
public interface IDishPageDb {
    public void deleteByDishId(long dishId) throws SQLiteException;
    public void deleteByMenuPageId(long menuPageId) throws SQLiteException;
    public List<DishPage> getByMenuPageId(long menuPageId) throws SQLiteException;
    public int countByDishId(long dishId) throws SQLiteException;
    public void add(long menuPageId, long dishId, int pos) throws SQLiteException;
    public void delete(long menuPageId, int pos) throws SQLiteException;
    
    public static class DishPage extends Relation {
        private int pos;

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }
        
        public void setMenuPageId(long id) {
            setId1(id);
        }
        
        public void setDishId(long id) {
            setId2(id);
        }
        
        public long getMenuPageId() {
            return getId1();
        }
        
        public long getDishId() {
            return getId2();
        }
    }
}
