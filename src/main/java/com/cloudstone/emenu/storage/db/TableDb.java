/**
 * @(#)TableDb.java, 2013-7-5. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;
import com.cloudstone.emenu.storage.db.util.UpdateSqlBuilder;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class TableDb extends SQLiteDb implements ITableDb {
    private static final Logger LOG = LoggerFactory.getLogger(TableDb.class);
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
    private static final String TABLE_NAME = "'table'";
    
    private static enum Column {
        ID("id"), NAME("name"), TYPE("type"),
        CAPACITY("capacity"), MIN_CHARGE("minCharge"), TIP_MODE("tipMode"),
        TIP("tip"), STATUS("status"), ORDER_ID("orderId"),
        CREATED_TIME("createdTime"), UPDATE_TIME("time"), DELETED("deleted");
        
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
            .append(Column.TYPE.toString(), DataType.INTEGER, "NOT NULL")
            .append(Column.CAPACITY.toString(), DataType.INTEGER, "NOT NULL")
            .append(Column.MIN_CHARGE.toString(), DataType.REAL, "NOT NULL")
            .append(Column.TIP_MODE.toString(), DataType.INTEGER, "NOT NULL")
            .append(Column.TIP.toString(), DataType.REAL, "NOT NULL")
            .append(Column.STATUS.toString(), DataType.INTEGER, "NOT NULL")
            .append(Column.ORDER_ID.toString(), DataType.INTEGER, "NOT NULL")
            .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
            .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
            .build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 12).build();
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhere(Column.ID.toString()).build();
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.NAME.toString()).appendSetValue(Column.TYPE.toString())
        .appendSetValue(Column.CAPACITY.toString()).appendSetValue(Column.MIN_CHARGE.toString())
        .appendSetValue(Column.TIP_MODE.toString()).appendSetValue(Column.TIP.toString())
        .appendSetValue(Column.STATUS.toString()).appendSetValue(Column.ORDER_ID.toString())
        .appendSetValue(Column.CREATED_TIME).appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId()
        .build();

    @Override
    public Table add(EmenuContext context, Table table) {
        table.setId(genId(context));
        TableBinder binder = new TableBinder(table);
        executeSQL(context, SQL_INSERT, binder);
        return get(context, table.getId());
    }
    
    @Override
    public Table get(EmenuContext context, int id) {
        IdStatementBinder binder = new IdStatementBinder(id);
        Table table = queryOne(context, SQL_SELECT_BY_ID, binder, rowMapper);
        return table;
    }
    
    @Override
    public Table getByTableName(EmenuContext context, String name) {
        return getByName(context, name, rowMapper);
    }

    @Override
    protected void onCheckCreateTable(EmenuContext context) {
        checkCreateTable(context, TABLE_NAME, COL_DEF);
    }
    
    @Override
    public List<Table> getAll(EmenuContext context) {
        return query(context, SQL_SELECT, StatementBinder.NULL, rowMapper);
    }
    
    @Override
    public Table update(EmenuContext context, Table table) {
        String sql = SQL_UPDATE;
        executeSQL(context, sql, new UpdateBinder(table));
        return get(context, table.getId());
    }
    
    private class TableBinder implements StatementBinder {
        private final  Table table;

        public TableBinder(Table table) {
            super();
            this.table = table;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, table.getId());
            stmt.bind(2,  table.getName());
            stmt.bind(3, table.getType());
            stmt.bind(4,  table.getCapacity());
            stmt.bind(5, table.getMinCharge());
            stmt.bind(6, table.getTipMode());
            stmt.bind(7, table.getTip());
            stmt.bind(8, table.getStatus());
            stmt.bind(9, table.getOrderId());
            stmt.bind(10, table.getCreatedTime());
            stmt.bind(11, table.getUpdateTime());
            stmt.bind(12, table.isDeleted() ? 1 : 0);
        }
    }
    
    private RowMapper<Table> rowMapper = new RowMapper<Table>() {
        
        @Override
        public Table map(SQLiteStatement stmt) throws SQLiteException {
            Table table = new Table();
            table.setId(stmt.columnInt(0));
            table.setName(stmt.columnString(1));
            table.setType(stmt.columnInt(2));
            table.setCapacity(stmt.columnInt(3));
            table.setMinCharge(stmt.columnDouble(4));
            table.setTipMode(stmt.columnInt(5));
            table.setTip(stmt.columnDouble(6));
            table.setStatus(stmt.columnInt(7));
            table.setOrderId(stmt.columnInt(8));
            table.setCreatedTime(stmt.columnLong(9));
            table.setUpdateTime(stmt.columnLong(10));
            table.setDeleted(stmt.columnInt(11) == 1);
            return table;
        }
    };
    
    private class UpdateBinder implements StatementBinder{
        private final Table table;
        
        public UpdateBinder(Table table) {
            super();
            this.table = table;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, table.getName());
            stmt.bind(2, table.getType());
            stmt.bind(3, table.getCapacity());
            stmt.bind(4, table.getMinCharge());
            stmt.bind(5, table.getTipMode());
            stmt.bind(6, table.getTip());
            stmt.bind(7, table.getStatus());
            stmt.bind(8, table.getOrderId());
            stmt.bind(9, table.getCreatedTime());
            stmt.bind(10, table.getUpdateTime());
            stmt.bind(11, table.isDeleted() ? 1 : 0);
            stmt.bind(12, table.getId());
        }
    }

}
