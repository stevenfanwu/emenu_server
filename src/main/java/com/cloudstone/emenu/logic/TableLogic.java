/**
 * @(#)TableLogic.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Table;

/**
 * @author xuhongfeng
 *
 */
@Component
public class TableLogic extends BaseLogic {
    
    public Table add(Table table) {
        tableService.add(table);
        return tableService.get(table.getId());
    }
    
    public List<Table> getAll() {
        return tableService.getAll();
    }

    
    public Table update(Table table) {
        tableService.update(table);
        return tableService.get(table.getId());
    }
    
    public void delete(int tableId) {
        tableService.delete(tableId);
    }
    
    public Table get(int tableId) {
        return tableService.get(tableId);
    }
    
    public Table getByName(String name) {
        return tableService.getByName(name);
    }
}
