/**
 * @(#)IDishPageDb.java, Jul 20, 2013. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.storage.db.RelationDb.Relation;

/**
 * @author xuhongfeng
 */
public interface IDishPageDb extends IDb {
    public void deleteByDishId(EmenuContext context, int dishId);

    public void deleteByMenuPageId(EmenuContext context, int menuPageId);

    public List<DishPage> getByMenuPageId(EmenuContext context, int menuPageId);

    public List<DishPage> getByDishId(EmenuContext context, int dishId);

    public int countByDishId(EmenuContext context, int dishId);

    public void add(EmenuContext context, int menuPageId, int dishId, int pos);

    public void delete(EmenuContext context, int menuPageId, int pos);

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
