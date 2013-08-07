/**
 * @(#)ITableService.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
public interface ITableService {

    public void add(Table table);
    public Table get(int id);
    public Table getByName(String name);
    public List<Table> getAll();
    public void delete(int tableId);
    public void update(DbTransaction trans, Table table);
}
