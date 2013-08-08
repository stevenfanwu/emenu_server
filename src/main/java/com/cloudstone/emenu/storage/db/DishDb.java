/**
 * @(#)DishDb.java, 2013-7-7. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.Dish;
import com.cloudstone.emenu.data.IdName;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.SqlUtils;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.storage.db.util.UpdateSqlBuilder;
import com.cloudstone.emenu.util.CnToPinyinUtils;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class DishDb extends SQLiteDb implements IDishDb {
    private static final Logger LOG = LoggerFactory.getLogger(DishDb.class);
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
    @Override
    public List<IdName> getDishSuggestion() throws SQLiteException {
        return getIdNames();
    }
    
    @Override
    public void add(Dish dish) throws SQLiteException {
        dish.setId(genId());
        DishBinder binder = new DishBinder(dish);
        executeSQL(SQL_INSERT, binder);
    }
    
    @Override
    public Dish getByName(String name) throws SQLiteException {
        return super.getByName(name, rowMapper);
    }
    
    @Override
    public Dish get(int dishId) throws SQLiteException {
        IdStatementBinder binder = new IdStatementBinder(dishId);
        Dish dish = queryOne(SQL_SELECT_BY_ID, binder, rowMapper);
        return dish;
    }
    
    @Override
    public List<Dish> getAll() throws SQLiteException {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }
    
    @Override
    public void update(Dish dish) throws SQLiteException {
        executeSQL(SQL_UPDATE, new UpdateBinder(dish));
    }
    
    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }
    
    /* ---------- inner class ---------- */
    private static final RowMapper<Dish> rowMapper = new RowMapper<Dish>() {
        
        @Override
        public Dish map(SQLiteStatement stmt) throws SQLiteException {
            Dish dish = new Dish();
            
            dish.setId(stmt.columnInt(0));
            dish.setName(stmt.columnString(1));
            dish.setPinyin(stmt.columnString(2));
            dish.setPrice(stmt.columnDouble(3));
            dish.setMemberPrice(stmt.columnDouble(4));
            dish.setUnit(stmt.columnInt(5));
            dish.setSpicy(stmt.columnInt(6));
            dish.setSpecialPrice(SqlUtils.intToBoolean(stmt.columnInt(7)));
            dish.setNonInt(SqlUtils.intToBoolean(stmt.columnInt(8)));
            dish.setDesc(stmt.columnString(9));
            dish.setImageId(stmt.columnString(10));
            dish.setStatus(stmt.columnInt(11));
            dish.setCreatedTime(stmt.columnLong(12));
            dish.setUpdateTime(stmt.columnLong(13));
            dish.setDeleted(stmt.columnInt(14) == 1);
            
            return dish;
        }
    };
    
    private static class DishBinder implements StatementBinder {
        private final Dish dish;

        public DishBinder(Dish dish) {
            super();
            this.dish = dish;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, dish.getId());
            stmt.bind(2, dish.getName());
            stmt.bind(3, dish.getPinyin());
            stmt.bind(4, dish.getPrice());
            stmt.bind(5, dish.getMemberPrice());
            stmt.bind(6, dish.getUnit());
            stmt.bind(7, dish.getSpicy());
            stmt.bind(8, SqlUtils.booleanToInt(dish.isSpecialPrice()));
            stmt.bind(9, SqlUtils.booleanToInt(dish.isNonInt()));
            stmt.bind(10, dish.getDesc());
            stmt.bind(11, dish.getImageId());
            stmt.bind(12, dish.getStatus());
            stmt.bind(13, dish.getCreatedTime());
            stmt.bind(14, dish.getUpdateTime());
            stmt.bind(15, dish.isDeleted() ? 1 : 0);
        }
    }
    
    private static class UpdateBinder implements StatementBinder {
        private final Dish dish;

        public UpdateBinder(Dish dish) {
            super();
            this.dish = dish;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, dish.getName());
            stmt.bind(2, dish.getPinyin());
            stmt.bind(3, dish.getPrice());
            stmt.bind(4, dish.getMemberPrice());
            stmt.bind(5, dish.getUnit());
            stmt.bind(6, dish.getSpicy());
            stmt.bind(7, SqlUtils.booleanToInt(dish.isSpecialPrice()));
            stmt.bind(8, SqlUtils.booleanToInt(dish.isNonInt()));
            stmt.bind(9, dish.getDesc());
            stmt.bind(10, dish.getImageId());
            stmt.bind(11, dish.getStatus());
            stmt.bind(12, dish.getCreatedTime());
            stmt.bind(13, dish.getUpdateTime());
            stmt.bind(14, dish.isDeleted() ? 1 : 0);
            stmt.bind(15, dish.getId());
        }
    }
    
    /* ---------- SQL ---------- */
    private static final String TABLE_NAME = "dish";
    
    private static enum Column {
        ID("id"), NAME("name"), PINYINFULL("pinyin"),
        PRICE("price"), MEMBER_PRICE("memberPrice"), UNIT("unit"), SPICY("spicy"),
        SPECIAL_PRICE("specialPrice"), NON_INT("nonInt"), DESC("desc"),
        IMAGE_ID("imageId"), STATUS("status"),
        CREATED_TIME("createdTime"), UPDATE_TIME("updateTime"), DELETED("deleted");
        
        private final String str;
        private Column(String str) {
            this.str = str;
        }
        
        @Override
        public String toString() {
            return str;
        }
    }

    private static final String COL_DEF = new ColumnDefBuilder()
        .append(Column.ID, DataType.INTEGER, "NOT NULL PRIMARY KEY")
        .append(Column.NAME, DataType.TEXT, "NOT NULL")
        .append(Column.PINYINFULL, DataType.TEXT, "NOT NULL")
        .append(Column.PRICE, DataType.REAL, "NOT NULL")
        .append(Column.MEMBER_PRICE, DataType.REAL, "NOT NULL")
        .append(Column.UNIT, DataType.INTEGER, "NOT NULL")
        .append(Column.SPICY, DataType.INTEGER, "NOT NULL")
        .append(Column.SPECIAL_PRICE, DataType.INTEGER, "NOT NULL")
        .append(Column.NON_INT, DataType.INTEGER, "NOT NULL")
        .append(Column.DESC, DataType.TEXT, "NOT NULL")
        .append(Column.IMAGE_ID, DataType.TEXT, "DEFAULT ''")
        .append(Column.STATUS, DataType.INTEGER, "DEFAULT ''")
        .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
        .build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 15).build();
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.NAME)
        .appendSetValue(Column.PINYINFULL)
        .appendSetValue(Column.PRICE)
        .appendSetValue(Column.MEMBER_PRICE)
        .appendSetValue(Column.UNIT)
        .appendSetValue(Column.SPICY)
        .appendSetValue(Column.SPECIAL_PRICE)
        .appendSetValue(Column.NON_INT)
        .appendSetValue(Column.DESC)
        .appendSetValue(Column.IMAGE_ID)
        .appendSetValue(Column.STATUS)
        .appendSetValue(Column.CREATED_TIME)
        .appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId()
        .build();
}
