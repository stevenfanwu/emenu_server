/**
 * @(#)TableLogic.java, 2013-7-6. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.constant.Const.TableStatus;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.exception.BadRequestError;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.service.TableService;
import com.cloudstone.emenu.storage.db.util.DbTransaction;
import com.cloudstone.emenu.util.CollectionUtils;
import com.cloudstone.emenu.util.CollectionUtils.Tester;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class TableLogic extends BaseLogic {
    @Autowired
    private TableService tableService;
    
    public void changeTable(int fromId, int toId) {
        Table from = get(fromId);
        if (from == null || from.getStatus() == TableStatus.EMPTY) {
            throw new BadRequestError();
        }
        Table to = get(toId);
        if (to == null || to.getStatus() != TableStatus.EMPTY) {
            throw new BadRequestError();
        }

        DbTransaction trans = openTrans();
        trans.begin();
        try {
            to.setStatus(from.getStatus());
            to.setOrderId(from.getOrderId());
            from.setStatus(TableStatus.EMPTY);
            from.setOrderId(0);
    
            long now = System.currentTimeMillis();
            from.setUpdateTime(now);
            tableService.update(trans, from);
            to.setUpdateTime(now);
            tableService.update(trans, to);
            trans.commit();
        } finally {
            trans.close();
        }
    }
    
    public Table add(Table table) {
        Table oldTable = getByName(table.getName());
        if (oldTable != null && !oldTable.isDeleted()) {
            throw new DataConflictException("该餐桌已存在");
        }
        long now = System.currentTimeMillis();
        table.setUpdateTime(now);
        if (oldTable != null) {
            table.setId(oldTable.getId());
            tableService.update(null, table);
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
    
    public List<Table> getOccupied() {
        List<Table> tables = getAll();
        CollectionUtils.filter(tables, new Tester<Table>() {
            @Override
            public boolean test(Table table) {
                return table.getStatus() == TableStatus.OCCUPIED;
            }
        });
        return tables;
    }

    
    public Table update(DbTransaction trans, Table table) {
        Table other = tableService.getByName(table.getName());
        if (other!=null && other.getId()!=table.getId() && !other.isDeleted()) {
            throw new DataConflictException("该餐桌已存在");
        }
        table.setUpdateTime(System.currentTimeMillis());
        tableService.update(trans, table);
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
