/**
 * @(#)TableLogic.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.exception.NotFoundException;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class TableLogic extends BaseLogic {
    
    public Table add(Table table) {
        Table oldTable = getByName(table.getName());
        if (oldTable != null && !oldTable.isDeleted()) {
            throw new DataConflictException("该餐桌已存在");
        }
        long now = System.currentTimeMillis();
        table.setUpdateTime(now);
        if (oldTable != null) {
            table.setId(oldTable.getId());
            table.setCreatedTime(oldTable.getCreatedTime());
            tableService.update(oldTable);
        } else {
            table.setCreatedTime(now);
            tableService.add(table);
        }
        return tableService.get(table.getId());
    }
    
    public List<Table> getAll() {
        List<Table> tables = tableService.getAll();
        DataUtils.filterDeleted(tables);
        return tables;
    }

    
    public Table update(Table table) {
        Table other = tableService.getByName(table.getName());
        if (other!=null && other.getId()!=table.getId() && !other.isDeleted()) {
            throw new DataConflictException("该餐桌已存在");
        }
        table.setUpdateTime(System.currentTimeMillis());
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
