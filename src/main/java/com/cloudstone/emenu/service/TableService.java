/**
 * @(#)TableService.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.ITableDb;
import com.cloudstone.emenu.storage.db.util.DbTransaction;

/**
 * @author xuhongfeng
 *
 */
@Service
public class TableService extends BaseService implements ITableService {
    @Autowired
    private ITableDb tableDb;

    @Override
    public void add(Table table) {
        try {
            tableDb.add(table);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Table> getAll() {
        try {
            return tableDb.getAll();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    
    @Override
    public void update(Table table, DbTransaction trans) {
        try {
            tableDb.update(table, trans);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public void delete(int tableId) {
        try {
            tableDb.delete(tableId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public Table getByName(String name) {
        try {
            return tableDb.getByTableName(name);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public Table get(int id) {
        try {
            return tableDb.get(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
}
