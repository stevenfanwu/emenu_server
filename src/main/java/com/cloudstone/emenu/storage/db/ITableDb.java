/**
 * @(#)ITableDb.java, 2013-7-5. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Table;

/**
 * @author xuhongfeng
 *
 */
public interface ITableDb {
    public Table add(Table table) throws SQLiteException;
    public Table get(long id) throws SQLiteException;
    public List<Table> getAll() throws SQLiteException;
    public Table update(Table table) throws SQLiteException;
    public void delete(long tableId) throws SQLiteException;
}
