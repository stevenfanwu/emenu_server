package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.VipHistory;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

/**
 * 
 * @author carelife
 *
 */
@Repository
public class VipHistoryDb extends SQLiteDb implements IVipHistoryDb {
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
   
    private static final String TABLE_NAME = "viphistory";
    
    private static enum Column {
		ID("id"), VIPID("vipid"), RECHARGE("recharge"), LEFT("left"), OPTIME("opTime"),
		CREATED_TIME("createdTime"), UPDATE_TIME("updatetime"), DELETED("deleted"),
      RESTAURANT_ID("restaurantId");

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
            .append(Column.ID.toString(), DataType.INTEGER, "NOT NULL PRIMARY KEY")
            .append(Column.VIPID.toString(), DataType.TEXT, "NOT NULL")
            .append(Column.RECHARGE.toString(), DataType.REAL, "NOT NULL")
            .append(Column.LEFT.toString(), DataType.REAL, "NOT NULL")
            .append(Column.OPTIME.toString(), DataType.INTEGER, "NOT NULL")
            .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
            .append(Column.RESTAURANT_ID, DataType.INTEGER, "NOT NULL")
            .build();
    
    private static final String SQL_SELECT_BY_VIPID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.VIPID.toString()).build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 9).build();
    
    @Override
    protected void onCheckCreateTable(EmenuContext context) {
        checkCreateTable(context, TABLE_NAME, COL_DEF);
    }

    @Override
    public List<VipHistory> get(EmenuContext context, int vipid) {
    	VipidBinder binder = new VipidBinder(vipid);
        List<VipHistory> viphistory = query(context, SQL_SELECT_BY_VIPID, binder, rowMapper);
        return viphistory;
    }
    
    @Override
    public List<VipHistory> getAll(EmenuContext context) {
        return getAllInRestaurant(context, rowMapper);
    }

    @Override
    public void add(EmenuContext context, VipHistory viphistory) {
        viphistory.setId(genId(context));
        viphistory.setRestaurantId(context.getRestaurantId());
        VipHistoryBinder binder = new VipHistoryBinder(viphistory);
        executeSQL(context, SQL_INSERT, binder);
    }
    
    private RowMapper<VipHistory> rowMapper = new RowMapper<VipHistory>() {
        
        @Override
        public VipHistory map(SQLiteStatement stmt) throws SQLiteException {
            VipHistory vipHistory = new VipHistory();
            vipHistory.setId(stmt.columnInt(0));
            vipHistory.setVipid(stmt.columnInt(1));
            vipHistory.setRecharge(stmt.columnDouble(2));
            vipHistory.setLeft(stmt.columnDouble(3));
            vipHistory.setOpTime(stmt.columnLong(4));
            vipHistory.setCreatedTime(stmt.columnLong(5));
            vipHistory.setUpdateTime(stmt.columnLong(6));
            vipHistory.setDeleted(stmt.columnInt(7) == 1);
            return vipHistory;
        }
    };
    
    private class VipHistoryBinder implements StatementBinder{
        private final VipHistory vip;

        public VipHistoryBinder(VipHistory vip) {
            super();
            this.vip = vip;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, vip.getId());
            stmt.bind(2, vip.getVipid());
            stmt.bind(3, vip.getRecharge());
            stmt.bind(4, vip.getLeft());
            stmt.bind(5, vip.getOpTime());
            stmt.bind(6, vip.getCreatedTime());
            stmt.bind(7, vip.getUpdateTime());
            stmt.bind(8, vip.isDeleted() ? 1 : 0);
            stmt.bind(9, vip.getRestaurantId());
        }
    }
    
    private class VipidBinder implements StatementBinder {
        private final int vipid;

        public VipidBinder(int vipid) {
            super();
            this.vipid = vipid;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, vipid);
        }
    }
}
