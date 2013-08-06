/**
 * @(#)PadDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.Pad;
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
public class PadDb extends SQLiteDb implements IPadDb {
    private static final String TABLE_NAME = "pad";

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Pad get(int id) throws SQLiteException {
        return queryOne(SQL_SELECT_BY_ID, new IdStatementBinder(id), rowMapper);
    }

    @Override
    public List<Pad> listAll() throws SQLiteException {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }

    @Override
    public void add(Pad pad) throws SQLiteException {
        pad.setId(genId());
        executeSQL(SQL_INSERT, new PadBinder(pad), null);
    }

    @Override
    public void update(Pad pad) throws SQLiteException {
        executeSQL(SQL_UPDATE, new UpdateBinder(pad), null);
    }

    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }
    
    private static enum Column {
        ID("id"), NAME("name"), IMEI("imei"), DESC("desc"), BATTERY_LEVEL("batteryLevel"),
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
        .append(Column.IMEI, DataType.TEXT, "NOT NULL")
        .append(Column.DESC, DataType.TEXT, "NOT NULL")
        .append(Column.BATTERY_LEVEL, DataType.INTEGER, "NOT NULL")
        .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
        .build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 8).build();
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.NAME)
        .appendSetValue(Column.IMEI)
        .appendSetValue(Column.DESC)
        .appendSetValue(Column.BATTERY_LEVEL)
        .appendSetValue(Column.CREATED_TIME)
        .appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId()
        .build();
    
    private RowMapper<Pad> rowMapper = new RowMapper<Pad>() {
        @Override
        public Pad map(SQLiteStatement stmt) throws SQLiteException {
            Pad pad = new Pad();
            pad.setId(stmt.columnInt(0));
            pad.setName(stmt.columnString(1));
            pad.setImei(stmt.columnString(2));
            pad.setDesc(stmt.columnString(3));
            pad.setBatteryLevel(stmt.columnInt(4));
            pad.setCreatedTime(stmt.columnLong(5));
            pad.setUpdateTime(stmt.columnLong(6));
            pad.setDeleted(stmt.columnInt(7) == 1);
            return pad;
        }
    };
    
    private class UpdateBinder implements StatementBinder {
        private final Pad pad;

        public UpdateBinder(Pad pad) {
            super();
            this.pad = pad;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, pad.getName());
            stmt.bind(2, pad.getImei());
            stmt.bind(3, pad.getDesc());
            stmt.bind(4, pad.getBatteryLevel());
            stmt.bind(5, pad.getCreatedTime());
            stmt.bind(6, pad.getUpdateTime());
            stmt.bind(7, pad.isDeleted() ? 1 : 0);
            stmt.bind(8, pad.getId());
        }
    }
    
    private class PadBinder implements StatementBinder {
        private final Pad pad;

        public PadBinder(Pad pad) {
            super();
            this.pad = pad;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, pad.getId());
            stmt.bind(2, pad.getName());
            stmt.bind(3, pad.getImei());
            stmt.bind(4, pad.getDesc());
            stmt.bind(5, pad.getBatteryLevel());
            stmt.bind(6, pad.getCreatedTime());
            stmt.bind(7, pad.getUpdateTime());
            stmt.bind(8, pad.isDeleted() ? 1 : 0);
        }
    }

}