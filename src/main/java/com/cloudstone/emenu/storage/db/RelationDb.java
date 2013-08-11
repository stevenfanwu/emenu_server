/**
 * @(#)RelationDb.java, Jul 19, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.ArrayList;
import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.data.BaseData;
import com.cloudstone.emenu.storage.db.RelationDb.Relation;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.CountSqlBuilder;
import com.cloudstone.emenu.storage.db.util.DbTransaction;
import com.cloudstone.emenu.storage.db.util.IdStatementBinder;
import com.cloudstone.emenu.storage.db.util.InsertSqlBuilder;
import com.cloudstone.emenu.storage.db.util.RowMapper;
import com.cloudstone.emenu.storage.db.util.SelectSqlBuilder;
import com.cloudstone.emenu.storage.db.util.StatementBinder;

/**
 * @author xuhongfeng
 *
 */
public abstract class RelationDb<T extends Relation> extends SQLiteDb {
    protected static final String ID1 = "id1";
    protected static final String ID2 = "id2";

    /* ---------- init ---------- */
    @Override
    protected void onCheckCreateTable() throws SQLiteException {
        //create table
        ColumnDefBuilder columnDefBuilder = new ColumnDefBuilder().append(ID1, DataType.INTEGER, "NOT NULL")
                .append(ID2, DataType.INTEGER, "NOT NULL");
        for (RelationDbColumn c:config.columns) {
            columnDefBuilder.append(c.name, c.type, "NOT NULL");
        }
        List<String> primaryKeys = new ArrayList<String>();
        primaryKeys.add(ID1);
        primaryKeys.add(ID2);
        for (RelationDbColumn c:config.columns) {
            if (c.isPrimaryKey) {
                primaryKeys.add(c.name);
            }
        }
        columnDefBuilder.appendPrimaryKey(primaryKeys.toArray(new String[0]));
        checkCreateTable(config.tableName, columnDefBuilder.build());
        
    }
    
    private RelationDbConfig config = onCreateConfig();
    protected abstract RelationDbConfig onCreateConfig();
    
    private RelationRowMapper<T> rowMapper = onCreateRowMapper();
    protected abstract RelationRowMapper<T> onCreateRowMapper();
    
    /* ---------- protected ---------- */
    protected void add(DbTransaction trans, InsertBinder binder) throws SQLiteException {
        int columnCount = 2 + config.columns.length;
        String sql = new InsertSqlBuilder(config.tableName, columnCount, true).build();
        executeSQL(trans, sql, binder);
    }
    
    protected void deleteById2(int id2) throws SQLiteException {
        deleteById(ID2, id2);
    }
    
    protected void deleteById1(int id1) throws SQLiteException {
        deleteById(ID1, id1);
    }
    
    private void deleteById(String idName, int id) throws SQLiteException {
        String sql = "UPDATE " + getTableName() + " SET deleted=1 WHERE " + idName + "=?";
        executeSQL(null, sql, new IdStatementBinder(id));
    }
    
    protected List<T> listById1(int id1) throws SQLiteException {
        return listById(ID1, id1);
    }
    
    protected List<T> listById2(int id2) throws SQLiteException {
        return listById(ID2, id2);
    }
    
    private List<T> listById(String idName, int id) throws SQLiteException {
        String sql = new SelectSqlBuilder(config.tableName).appendWhere(idName).build();
        return query(sql, new IdStatementBinder(id), rowMapper);
    }
    
    protected int countId1(int id1) throws SQLiteException {
        return countId(ID1, id1);
    }
    
    protected int countId2(int id2) throws SQLiteException {
        return countId(ID2, id2);
    }
    
    private int countId(String idName, int id) throws SQLiteException {
        String sql = new CountSqlBuilder(config.tableName).appendWhere(idName)
                .appendNotDeleted().build();
        return queryInt(sql, new IdStatementBinder(id));
    }
    
    /* ---------- Inner Class ---------- */
    protected static class RelationDbConfig {
        private final String tableName;
        private final RelationDbColumn[] columns;
        
        public RelationDbConfig(String tableName, RelationDbColumn[] columns) {
            super();
            this.tableName = tableName;
            this.columns = columns;
        }
        
    }
    
    protected static class RelationDbColumn {
        protected final String name;
        private final DataType type;
        private final boolean isPrimaryKey;
        
        public RelationDbColumn(String name, DataType type, boolean isPrimaryKey) {
            super();
            this.name = name;
            this.type = type;
            this.isPrimaryKey = isPrimaryKey;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    protected abstract class InsertBinder implements StatementBinder {
        private final int id1;
        private final int id2;

        public InsertBinder(int id1, int id2) {
            super();
            this.id1 = id1;
            this.id2 = id2;
        }

        @Override
        public final void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, id1);
            stmt.bind(2, id2);
            bindOthers(stmt);
        }
        
        /**
         *  stmt.bind(3, ...)
         *  ...
         * 
         * @param stmt
         * @throws SQLiteException
         */
        protected abstract void bindOthers(SQLiteStatement stmt) throws SQLiteException;
    }
    
    public static class Relation extends BaseData {
        private int id1;
        private int id2;
        
        public int getId1() {
            return id1;
        }
        public void setId1(int id1) {
            this.id1 = id1;
        }
        public int getId2() {
            return id2;
        }
        public void setId2(int id2) {
            this.id2 = id2;
        }
    }
    
    public static abstract class RelationRowMapper<T extends Relation> implements RowMapper<T> {

        @Override
        public T map(SQLiteStatement stmt) throws SQLiteException {
            T relation = newRelation();
            relation.setId1(stmt.columnInt(0));
            relation.setId2(stmt.columnInt(1));
            return relation;
        }
        
        protected abstract T newRelation();
    }
}