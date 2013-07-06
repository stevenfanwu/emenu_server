/**
 * @(#)TableService.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.ITableDb;

/**
 * @author xuhongfeng
 *
 */
@Service
public class TableService extends BaseService implements ITableService {
    @Autowired
    private ITableDb tableDb;

    @Override
    public Table add(Table table) {
        try {
            return tableDb.add(table);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

}
