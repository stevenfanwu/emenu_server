/**
 * @(#)ITableDb.java, 2013-7-5. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface ITableDb extends IDb {
    public Table add(Table table) throws SQLiteException;
    public Table get(int id) throws SQLiteException;
    public Table getByTableName(String tableName) throws SQLiteException;
    public List<Table> getAll() throws SQLiteException;
    public Table update(Table table, DbTransaction trans) throws SQLiteException;
    public void delete(int tableId) throws SQLiteException;
}
