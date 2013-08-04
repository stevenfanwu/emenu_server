/**
 * @(#)DishPageDb.java, Jul 19, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.storage.db.IDishPageDb.DishPage;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.storage.db.util.UpdateSqlBuilder;

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
    private static final Logger LOG = LoggerFactory.getLogger(DishPageDb.class);
    private static final String TABLE_NAME = "dishPage";
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
    //dish position in the MenuPage
    private static final RelationDbColumn COL_POS =
            new RelationDbColumn("pos", DataType.INTEGER, true);
    private static final RelationDbColumn COL_CREATED_TIME =
            new RelationDbColumn("createdTime", DataType.INTEGER, false);
    private static final RelationDbColumn COL_UPDATE_TIME =
            new RelationDbColumn("updateTime", DataType.INTEGER, false);
    private static final RelationDbColumn COL_DELETED =
            new RelationDbColumn("deleted", DataType.INTEGER, false);

    /* ---------- protected ---------- */
    @Override
    protected RelationDbConfig onCreateConfig() {
        RelationDbColumn[] columns = new RelationDbColumn[] {
                COL_POS, COL_CREATED_TIME, COL_UPDATE_TIME, COL_DELETED
        };
        return new RelationDbConfig(TABLE_NAME, columns);
    }

    @Override
    protected RelationRowMapper<DishPage> onCreateRowMapper() {
        return rowMapper;
    }

    /* ---------- public ---------- */
    @Override
    public void add(int menuPageId, int dishId, final int pos) throws SQLiteException {
        add(new InsertBinder(menuPageId, dishId) {
            @Override
            protected void bindOthers(SQLiteStatement stmt)
                    throws SQLiteException {
                long now = System.currentTimeMillis();
                stmt.bind(3, pos);
                stmt.bind(4, now);
                stmt.bind(5, now);
                stmt.bind(6, 0);
            }
        });
    }

    @Override
    public void deleteByDishId(int dishId) throws SQLiteException {
        deleteById2(dishId);
    }

    @Override
    public void deleteByMenuPageId(int menuPageId) throws SQLiteException {
        deleteById1(menuPageId);
    }

    @Override
    public List<DishPage> getByMenuPageId(int menuPageId)
            throws SQLiteException {
        return listById1(menuPageId);
    }

    @Override
    public List<DishPage> getByDishId(int dishId)
            throws SQLiteException {
        return listById2(dishId);
    }

    @Override
    public int countByDishId(int dishId) throws SQLiteException {
        return countId2(dishId);
    }
    
    @Override
    public void delete(final int menuPageId, final int pos) throws SQLiteException {
        String sql = new UpdateSqlBuilder(TABLE_NAME)
            .appendSetValue(COL_DELETED)
            .appendWhere(ID1).appendWhere(COL_POS).build();
        executeSQL(sql, new StatementBinder() {
            @Override
            public void onBind(SQLiteStatement stmt) throws SQLiteException {
                stmt.bind(1, 1);
                stmt.bind(2, menuPageId);
                stmt.bind(3, pos);
                
            }
        });
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
            page.setCreatedTime(stmt.columnLong(3));
            page.setUpdateTime(stmt.columnLong(4));
            page.setDeleted(stmt.columnInt(5) == 1);
            return page;
        };
    };
 }