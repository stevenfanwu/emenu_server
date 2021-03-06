/**
 * @(#)TableLogic.java, 2013-7-6. 
 *
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.constant.Const.TableStatus;
import com.cloudstone.emenu.data.Order;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.misc.PollingManager;
import com.cloudstone.emenu.data.misc.PollingManager.PollingMessage;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.exception.NotFoundException;
import com.cloudstone.emenu.exception.PreconditionFailedException;
import com.cloudstone.emenu.storage.db.ITableDb;
import com.cloudstone.emenu.storage.db.MiscStorage;
import com.cloudstone.emenu.util.CollectionUtils;
import com.cloudstone.emenu.util.CollectionUtils.Tester;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 */
@Component
public class TableLogic extends BaseLogic {

    @Autowired
    private ITableDb tableDb;
    @Autowired
    private MiscStorage miscStorage;

    @Autowired
    private OrderLogic orderLogic;
    @Autowired
    private PollingManager pollingManager;

    public void changeTable(EmenuContext context, int fromId, int toId) {
        Table from = get(context, fromId);
        if (from == null || from.isDeleted()) {
            throw new NotFoundException("桌子不存在, tableId=" + fromId);
        }
        if (from.getStatus() == TableStatus.EMPTY) {
            throw new PreconditionFailedException("桌子未开台, tableId=" + fromId);
        }
        Table to = get(context, toId);
        if (to == null || to.isDeleted()) {
            throw new NotFoundException("桌子不存在, tableId=" + toId);
        }
        if (to.getStatus() != TableStatus.EMPTY) {
            throw new PreconditionFailedException("桌子不是空闲状态, tableId=" + toId);
        }
        Order order = null;
        if (from.getOrderId() != 0) {
            order = orderLogic.getOrder(context, from.getOrderId());
            if (order == null || order.isDeleted()) {
                throw new NotFoundException("订单不存在, orderId=" + from.getOrderId());
            }
        }

        context.beginTransaction(dataSource);
        try {
            to.setStatus(from.getStatus());
            to.setOrderId(from.getOrderId());
            from.setStatus(TableStatus.EMPTY);
            from.setOrderId(0);

            long now = System.currentTimeMillis();
            from.setUpdateTime(now);
            tableDb.update(context, from);
            to.setUpdateTime(now);
            tableDb.update(context, to);

            if (order != null) {
                order.setTableId(to.getId());
                orderLogic.updateOrder(context, order);
            }
            context.commitTransaction();
        } finally {
            context.closeTransaction(dataSource);
        }
    }

    public Table add(EmenuContext context, Table table) {
        Table oldTable = getByName(context, table.getName());
        if (oldTable != null && !oldTable.isDeleted()) {
            throw new DataConflictException("该餐桌已存在");
        }
        long now = System.currentTimeMillis();
        table.setUpdateTime(now);
        if (oldTable != null) {
            table.setId(oldTable.getId());
            tableDb.update(context, table);
        } else {
            table.setCreatedTime(now);
            tableDb.add(context, table);
        }
        return tableDb.get(context, table.getId());
    }

    public List<Table> getAll(EmenuContext context) {
        List<Table> tables = tableDb.getAll(context);
        DataUtils.filterDeleted(tables);
        return tables;
    }

    public int tableCount(EmenuContext context) {
        return tableDb.count(context);
    }

    public List<Table> getOccupied(EmenuContext context) {
        List<Table> tables = getAll(context);
        CollectionUtils.filter(tables, new Tester<Table>() {
            @Override
            public boolean test(Table table) {
                return table.getStatus() == TableStatus.OCCUPIED;
            }
        });
        return tables;
    }

    public Table occupy(EmenuContext context, int tableId, int customNum) {
        Table table = get(context, tableId);
        if (table == null || table.isDeleted()) {
            throw new NotFoundException("桌子不存在");
        }
        if (table.getStatus() != Const.TableStatus.EMPTY) {
            throw new DataConflictException("桌子已被占用");
        }
        return occupy(context, table, customNum);
    }

    public Table occupy(EmenuContext context, Table table, int customNum) {
        table.setStatus(Const.TableStatus.OCCUPIED);

        context.beginTransaction(dataSource);
        try {
            table = update(context, table);
            setCustomerNumber(context, table.getId(), customNum);
            context.commitTransaction();
        } finally {
            context.closeTransaction(dataSource);
        }
        pollingManager.putMessage(PollingMessage.TYPE_OCCUPY_TABLE, table);
        return table;
    }

    public Table clearTable(EmenuContext context, int tableId) {
        Table table = get(context, tableId);
        if (table == null) {
            throw new NotFoundException("该餐桌不存在");
        }
        return clearTable(context, table);
    }

    public Table clearTable(EmenuContext context, Table table) {
        int orderId = table.getOrderId();
        if (orderId != 0) {
            orderLogic.deleteOrder(context, orderId);
        }
        table.setOrderId(0);
        table.setStatus(Const.TableStatus.EMPTY);
        update(context, table);
        setCustomerNumber(context, table.getId(), 0);

        pollingManager.putMessage(PollingMessage.TYPE_CLEAR_TABLE, table);
        return table;
    }

    public Table update(EmenuContext context, Table table) {
        Table other = tableDb.getByTableName(context, table.getName());
        if (other != null && other.getId() != table.getId() && !other.isDeleted()) {
            throw new DataConflictException("该餐桌已存在");
        }
        table.setUpdateTime(System.currentTimeMillis());
        tableDb.update(context, table);
        return tableDb.get(context, table.getId());
    }

    public void setCustomerNumber(EmenuContext context,
                                  int tableId, int num) {
        miscStorage.setCustomerNumber(context, tableId, num);
    }

    public int getCustomerNumber(EmenuContext context, int tableId) {
        return miscStorage.getCustomerNumber(context, tableId);
    }

    public void delete(EmenuContext context, int tableId) {
        tableDb.delete(context, tableId);
    }

    public Table get(EmenuContext context, int tableId) {
        return tableDb.get(context, tableId);
    }

    public Table getByName(EmenuContext context, String name) {
        return tableDb.getByTableName(context, name);
    }
}
