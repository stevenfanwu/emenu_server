/**
 * @(#)TableLogic.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.service.ITableService;
import com.cloudstone.emenu.util.IdGenerator;

/**
 * @author xuhongfeng
 *
 */
@Component
public class TableLogic extends BaseLogic {
    @Autowired
    private ITableService tableService;
    
    public Table add(Table table) {
        table.setId(IdGenerator.generateId());
        return tableService.add(table);
    }
    
    public List<Table> getAll() {
        return tableService.getAll();
    }

    
    public Table update(Table table) {
        return tableService.update(table);
    }
    
    public void delete(long tableId) {
        tableService.delete(tableId);
    }
}
