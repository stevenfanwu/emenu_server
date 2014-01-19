package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Vip;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.storage.db.util.UpdateSqlBuilder;

/**
 * 
 * @author carelife
 *
 */
@Repository
public class VipDb extends SQLiteDb implements IVipDb {
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
   
    private static final String TABLE_NAME = "vip";
    
    private static enum Column {
		ID("id"), NAME("name"), SEX("sex"), IDCARD("idCard"), PHONE("phone"), 
		EMAIL("email"), ADDRESS("address"), COMPANY("company"), MONEY("money"), TAG("tag"),
		CREATED_TIME("createdTime"), UPDATE_TIME("updatetime"), DELETED("deleted");

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
            .append(Column.NAME.toString(), DataType.TEXT, "NOT NULL")
            .append(Column.SEX.toString(), DataType.INTEGER, "NOT NULL")
            .append(Column.IDCARD.toString(), DataType.TEXT, "DEFAULT ''")
            .append(Column.PHONE.toString(), DataType.TEXT, "NOT NULL")
            .append(Column.EMAIL.toString(), DataType.TEXT, "DEFAULT ''")
            .append(Column.ADDRESS.toString(), DataType.TEXT, "DEFAULT ''")
            .append(Column.COMPANY.toString(), DataType.TEXT, "DEFAULT ''")
            .append(Column.MONEY.toString(), DataType.REAL, "NOT NULL")
            .append(Column.TAG.toString(), DataType.TEXT, "DEFAULT ''")
            .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
            .build();
    
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.ID.toString()).build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 13).build();
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.NAME)
        .appendSetValue(Column.SEX)
        .appendSetValue(Column.IDCARD)
        .appendSetValue(Column.PHONE)
        .appendSetValue(Column.EMAIL)
        .appendSetValue(Column.ADDRESS)
        .appendSetValue(Column.COMPANY)
        .appendSetValue(Column.CREATED_TIME)
        .appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId()
        .build();
    private static final String SQL_MODIFY_MONEY = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.MONEY.toString()).appendWhereId().build();
    
    @Override
    protected void onCheckCreateTable(EmenuContext context) {
        checkCreateTable(context, TABLE_NAME, COL_DEF);
    }
    
    @Override
    public Vip getByName(EmenuContext context, String name) {
        Vip vip = super.getByName(context, name, rowMapper);
        return vip;
    }
    
    @Override
    public Vip update(EmenuContext context, Vip vip) {
        String sql = SQL_UPDATE;
        executeSQL(context, sql, new UpdateBinder(vip));
        return get(context, vip.getId());
    }
    
    @Override
    public double recharge(EmenuContext context, int id, double money)
            {
        executeSQL(context, SQL_MODIFY_MONEY, new RechargeBinder(id, money));
        return get(context, id).getMoney();
    }
    
    @Override
    public Vip get(EmenuContext context, int vipId) {
        IdStatementBinder binder = new IdStatementBinder(vipId);
        Vip vip = queryOne(context, SQL_SELECT_BY_ID, binder, rowMapper);
        return vip;
    }
    
    @Override
    public List<Vip> getAll(EmenuContext context) {
        return query(context, SQL_SELECT, StatementBinder.NULL, rowMapper);
    }

    @Override
    public Vip add(EmenuContext context, Vip vip) {
        vip.setId(genId(context));
        VipBinder binder = new VipBinder(vip);
        executeSQL(context, SQL_INSERT, binder);
        return get(context, vip.getId());
    }
    
    private RowMapper<Vip> rowMapper = new RowMapper<Vip>() {
        
        @Override
        public Vip map(SQLiteStatement stmt) throws SQLiteException {
            Vip vip = new Vip();
            vip.setId(stmt.columnInt(0));
            vip.setName(stmt.columnString(1));
            vip.setSex(stmt.columnInt(2));
            vip.setIdCard(stmt.columnString(3));
            vip.setPhone(stmt.columnString(4));
            vip.setEmail(stmt.columnString(5));
            vip.setAddress(stmt.columnString(6));
            vip.setCompany(stmt.columnString(7));
            vip.setMoney(stmt.columnDouble(8));
            vip.setTag(stmt.columnString(9));
            vip.setCreatedTime(stmt.columnLong(10));
            vip.setUpdateTime(stmt.columnLong(11));
            vip.setDeleted(stmt.columnInt(12) == 1);
            return vip;
        }
    };
    
    private class VipBinder implements StatementBinder{
        private final Vip vip;

        public VipBinder(Vip vip) {
            super();
            this.vip = vip;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, vip.getId());
            stmt.bind(2, vip.getName());
            stmt.bind(3, vip.getSex());
            stmt.bind(4, vip.getIdCard());
            stmt.bind(5, vip.getPhone());
            stmt.bind(6, vip.getEmail());
            stmt.bind(7, vip.getAddress());
            stmt.bind(8, vip.getCompany());
            stmt.bind(9, vip.getMoney());
            stmt.bind(10, vip.getTag());
            stmt.bind(11, vip.getCreatedTime());
            stmt.bind(12, vip.getUpdateTime());
            stmt.bind(13, vip.isDeleted() ? 1 : 0);
        }
    }
    
    private class RechargeBinder implements StatementBinder {
        private final int id;
        private final double money;

        public RechargeBinder(int id, double money) {
            super();
            this.id = id;
            this.money = money;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, money);
            stmt.bind(2, id);
        }
    }
    
    private class UpdateBinder implements StatementBinder{
        private final Vip vip;

        public UpdateBinder(Vip vip) {
            super();
            this.vip = vip;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, vip.getName());
            stmt.bind(2, vip.getSex());
            stmt.bind(3, vip.getIdCard());
            stmt.bind(4, vip.getPhone());
            stmt.bind(5, vip.getEmail());
            stmt.bind(6, vip.getAddress());
            stmt.bind(7, vip.getCompany());
            stmt.bind(8, vip.getMoney());
            stmt.bind(9, vip.getTag());
            stmt.bind(10, vip.getCreatedTime());
            stmt.bind(11, vip.getUpdateTime());
            stmt.bind(12, vip.isDeleted() ? 1 : 0);
        	stmt.bind(13, vip.getId());
        }
    }
}
