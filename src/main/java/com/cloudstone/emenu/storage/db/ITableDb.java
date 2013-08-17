/**
 * @(#)ITableDb.java, 2013-7-5. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface ITableDb extends IDb {
    public Table add(Table table) ;
    public Table get(int id) ;
    public Table getByTableName(String tableName) ;
    public List<Table> getAll() ;
    public Table update(DbTransaction trans, Table table) ;
}
