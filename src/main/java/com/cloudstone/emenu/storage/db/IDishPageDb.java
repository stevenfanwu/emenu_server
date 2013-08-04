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
public interface IDishPageDb extends IDb {
    public void deleteByDishId(int dishId) throws SQLiteException;
    public void deleteByMenuPageId(int menuPageId) throws SQLiteException;
    public List<DishPage> getByMenuPageId(int menuPageId) throws SQLiteException;
    public List<DishPage> getByDishId(int dishId) throws SQLiteException;
    public int countByDishId(int dishId) throws SQLiteException;
    public void add(int menuPageId, int dishId, int pos) throws SQLiteException;
    public void delete(int menuPageId, int pos) throws SQLiteException;
    
    public static class DishPage extends Relation {
        private int pos;

        public int getPos() {
            return pos;
        }

        public void setPos(int pos) {
            this.pos = pos;
        }
        
        public void setMenuPageId(int id) {
            setId1(id);
        }
        
        public void setDishId(int id) {
            setId2(id);
        }
        
        public int getMenuPageId() {
            return getId1();
        }
        
        public int getDishId() {
            return getId2();
        }
    }
}
