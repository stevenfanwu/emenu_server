/**
 * @(#)DishPageDb.java, Jul 19, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.storage.db.IDishPageDb.DishPage;

/**
 * 
 * id1 = menuPageId
 * id2 = dishId 
 * 
 * @author xuhongfeng
 *
 */
@Repository
public class DishPageDb extends RelationDb<DishPage> implements IDishPageDb {
    private static final String TABLE_NAME = "dishPage";
    
    //dish position in the MenuPage
    private static final RelationDbColumn COL_POS = new RelationDbColumn("pos", DataType.INTEGER);

    /* ---------- protected ---------- */
    @Override
    protected RelationDbConfig onCreateConfig() {
        RelationDbColumn[] columns = new RelationDbColumn[] {
                COL_POS
        };
        return new RelationDbConfig(TABLE_NAME, columns);
    }

    @Override
    protected RelationRowMapper<DishPage> onCreateRowMapper() {
        return rowMapper;
    }

    /* ---------- public ---------- */
    @Override
    public void add(long menuPageId, long dishId, final int pos) throws SQLiteException {
        add(new InsertBinder(menuPageId, dishId) {
            @Override
            protected void onBind(SQLiteStatement stmt, int indexStmt, int indexValue) throws SQLiteException {
                stmt.bind(indexValue, pos);
            }
        });
    }

    @Override
    public void deleteByDishId(long dishId) throws SQLiteException {
        deleteById2(dishId);
    }

    @Override
    public void deleteByMenuPageId(long menuPageId) throws SQLiteException {
        deleteById1(menuPageId);
    }

    @Override
    public List<DishPage> getByMenuPageId(long menuPageId)
            throws SQLiteException {
        return listById1(menuPageId);
    }

    @Override
    public int countByDishId(long dishId) throws SQLiteException {
        return countId2(dishId);
    }
    
    /* ---------- Inner Class ---------- */
    private static RelationRowMapper<DishPage> rowMapper = new RelationRowMapper<IDishPageDb.DishPage>() {
        @Override
        protected DishPage newRelation() {
            return new DishPage();
        }
        
        @Override
        public DishPage map(SQLiteStatement stmt) throws SQLiteException {
            DishPage page = super.map(stmt);
            page.setPos(stmt.columnInt(2));
            return page;
        };
    };
 }