/**
 * @(#)ITableDb.java, 2013-7-5. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Table;

/**
 * @author xuhongfeng
 *
 */
public interface ITableDb extends IDb {
    public Table add(EmenuContext context, Table table) ;
    public Table get(EmenuContext context, int id) ;
    public Table getByTableName(EmenuContext context, String tableName) ;
    public List<Table> getAll(EmenuContext context) ;
    public Table update(EmenuContext context, Table table) ;
}
