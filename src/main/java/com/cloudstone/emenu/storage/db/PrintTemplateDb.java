/**
 * @(#)PrintTemplateDb.java, Aug 15, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.PrintTemplate;
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
public class PrintTemplateDb extends SQLiteDb implements IPrintTemplateDb {
    
    @Override
    public void removeComponent(int componentId) {
        String sql = "UPDATE " + TABLE_NAME
                + " SET headerId=0 WHERE headerId=" + componentId;
        executeSQL(null, sql, StatementBinder.NULL);
        sql = "UPDATE " + TABLE_NAME
                + " SET footerId=0 WHERE footerId=" + componentId;
        executeSQL(null, sql, StatementBinder.NULL);
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public void add(PrintTemplate data) {
        data.setId(genId());
        executeSQL(null, SQL_INSERT, new PrintTemplateBinder(data));
    }

    @Override
    public void update(PrintTemplate template) {
        executeSQL(null, SQL_UPDATE, new UpdateBinder(template));
    }

    @Override
    public PrintTemplate get(int id) {
        return queryOne(SQL_SELECT_BY_ID, new IdStatementBinder(id), rowMapper);
    }

    @Override
    protected void onCheckCreateTable() {
        checkCreateTable(TABLE_NAME, COL_DEF);
    }
    
    @Override
    public List<PrintTemplate> listAll() {
        return query(SQL_SELECT, StatementBinder.NULL, rowMapper);
    }

    /* ---------- SQL ---------- */
    private static final String TABLE_NAME = "printTemplate";
    private static enum Column {
        ID("id"), NAME("name"), HEADER_ID("headerId"),
        FOOTER_ID("footerId"), CUT_TYPE("cutType"),
        CREATED_TIME("createdTime"), UPDATE_TIME("updateTime"),
        DELETED("deleted");
        
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
        .append(Column.HEADER_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.FOOTER_ID, DataType.INTEGER, "NOT NULL")
        .append(Column.CUT_TYPE, DataType.INTEGER, "NOT NULL")
        .append(Column.CREATED_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.UPDATE_TIME, DataType.INTEGER, "NOT NULL")
        .append(Column.DELETED, DataType.INTEGER, "NOT NULL")
        .build();
    private static final String SQL_INSERT = new InsertSqlBuilder(TABLE_NAME, 8).build();
    
    private static class PrintTemplateBinder implements StatementBinder {
        private final PrintTemplate data;

        public PrintTemplateBinder(PrintTemplate template) {
            super();
            this.data = template;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, data.getId());
            stmt.bind(2, data.getName());
            stmt.bind(3, data.getHeaderId());
            stmt.bind(4, data.getFooterId());
            stmt.bind(5, data.getCutType());
            stmt.bind(6, data.getCreatedTime());
            stmt.bind(7, data.getUpdateTime());
            stmt.bind(8, data.isDeleted() ? 1 : 0);
        }
    }
    
    private static final String SQL_UPDATE = new UpdateSqlBuilder(TABLE_NAME)
        .appendSetValue(Column.NAME)
        .appendSetValue(Column.HEADER_ID)
        .appendSetValue(Column.FOOTER_ID)
        .appendSetValue(Column.CUT_TYPE)
        .appendSetValue(Column.CREATED_TIME)
        .appendSetValue(Column.UPDATE_TIME)
        .appendSetValue(Column.DELETED)
        .appendWhereId()
        .build();
    private static class UpdateBinder implements StatementBinder {
        private final PrintTemplate data;

        public UpdateBinder(PrintTemplate data) {
            super();
            this.data = data;
        }

        @Override
        public void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, data.getName());
            stmt.bind(2, data.getHeaderId());
            stmt.bind(3, data.getFooterId());
            stmt.bind(4, data.getCutType());
            stmt.bind(5, data.getCreatedTime());
            stmt.bind(6, data.getUpdateTime());
            stmt.bind(7, data.isDeleted() ? 1 : 0);
            stmt.bind(8, data.getId());
        }
    }
    private static final String SQL_SELECT_BY_ID = new SelectSqlBuilder(TABLE_NAME)
        .appendWhereId().build();
    private static final String SQL_SELECT = new SelectSqlBuilder(TABLE_NAME).build();
    private static final RowMapper<PrintTemplate> rowMapper
        = new RowMapper<PrintTemplate>() {
        
        @Override
        public PrintTemplate map(SQLiteStatement stmt) throws SQLiteException {
            PrintTemplate data = new PrintTemplate();
            data.setId(stmt.columnInt(0));
            data.setName(stmt.columnString(1));
            data.setHeaderId(stmt.columnInt(2));
            data.setFooterId(stmt.columnInt(3));
            data.setCutType(stmt.columnInt(4));
            data.setCreatedTime(stmt.columnInt(5));
            data.setUpdateTime(stmt.columnInt(6));
            data.setDeleted(stmt.columnInt(7) == 1);
            return data;
        }
    };
}