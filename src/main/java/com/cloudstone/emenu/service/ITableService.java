/**
 * @(#)ITableService.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.Table;

/**
 * @author xuhongfeng
 *
 */
public interface ITableService {

    public Table add(Table table);
    public List<Table> getAll();
    public void delete(long tableId);
    public Table update(Table table);
}
