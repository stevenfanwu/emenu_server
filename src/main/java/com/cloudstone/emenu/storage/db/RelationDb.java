/**
 * @(#)RelationDb.java, Jul 19, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cloudstone.emenu.storage.db.RelationDb.Relation;
import com.cloudstone.emenu.storage.db.util.ColumnDefBuilder;
import com.cloudstone.emenu.storage.db.util.CountSqlBuilder;
import com.cloudstone.emenu.storage.db.util.DeleteSqlBuilder;
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
        columnDefBuilder.appendPrimaryKey(ID1, ID2);
        checkCreateTable(config.tableName, columnDefBuilder.build());
        
        //create index
        checkCreateIndex("index_" + config.tableName, config.tableName, ID2, ID1);
    }
    
    private RelationDbConfig config = onCreateConfig();
    protected abstract RelationDbConfig onCreateConfig();
    
    private RelationRowMapper<T> rowMapper = onCreateRowMapper();
    protected abstract RelationRowMapper<T> onCreateRowMapper();
    
    /* ---------- protected ---------- */
    protected void add(InsertBinder binder) throws SQLiteException {
        int columnCount = 2 + config.columns.length;
        String sql = new InsertSqlBuilder(config.tableName, columnCount, true).build();
        executeSQL(sql, binder);
    }
    
    protected void deleteById2(long id2) throws SQLiteException {
        deleteById(ID2, id2);
    }
    
    protected void deleteById1(long id1) throws SQLiteException {
        deleteById(ID1, id1);
    }
    
    private void deleteById(String idName, long id) throws SQLiteException {
        String sql = new DeleteSqlBuilder(config.tableName).appendWhere(idName).build();
        executeSQL(sql, new IdStatementBinder(id));
    }
    
    protected List<T> listById1(long id1) throws SQLiteException {
        return listById(ID1, id1);
    }
    
    protected List<T> listById2(long id2) throws SQLiteException {
        return listById(ID2, id2);
    }
    
    private List<T> listById(String idName, long id) throws SQLiteException {
        String sql = new SelectSqlBuilder(config.tableName).appendWhere(idName).build();
        return query(sql, new IdStatementBinder(id), rowMapper);
    }
    
    protected int countId1(long id1) throws SQLiteException {
        return countId(ID1, id1);
    }
    
    protected int countId2(long id2) throws SQLiteException {
        return countId(ID2, id2);
    }
    
    private int countId(String idName, long id) throws SQLiteException {
        String sql = new CountSqlBuilder(config.tableName).appendWhere(idName).build();
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
        private final String name;
        private final DataType type;
        
        public RelationDbColumn(String name, DataType type) {
            super();
            this.name = name;
            this.type = type;
        }
    }
    
    protected abstract class InsertBinder implements StatementBinder {
        private final long id1;
        private final long id2;

        public InsertBinder(long id1, long id2) {
            super();
            this.id1 = id1;
            this.id2 = id2;
        }

        @Override
        public final void onBind(SQLiteStatement stmt) throws SQLiteException {
            stmt.bind(1, id1);
            stmt.bind(2, id2);
            for (int i=0; i<config.columns.length; i++) {
                onBind(stmt, i+3, i);
            }
        }
        
        /**
         * stmt.bind(indexStmt, getValue(indexValue);
         * @param stmt
         * @param indexStmt
         * @param indexValue
         * @throws SQLiteException 
         */
        protected abstract void onBind(SQLiteStatement stmt, int indexStmt, int indexValue) throws SQLiteException;
    }
    
    public static class Relation {
        private long id1;
        private long id2;
        
        public long getId1() {
            return id1;
        }
        public void setId1(long id1) {
            this.id1 = id1;
        }
        public long getId2() {
            return id2;
        }
        public void setId2(long id2) {
            this.id2 = id2;
        }
    }
    
    public static abstract class RelationRowMapper<T extends Relation> implements RowMapper<T> {

        @Override
        public T map(SQLiteStatement stmt) throws SQLiteException {
            T relation = newRelation();
            relation.setId1(stmt.columnLong(0));
            relation.setId2(stmt.columnLong(1));
            return relation;
        }
        
        protected abstract T newRelation();
    }
}