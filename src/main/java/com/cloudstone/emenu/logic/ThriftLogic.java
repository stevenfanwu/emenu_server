/**
 * @(#)ThriftLogic.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import org.springframework.stereotype.Component;

import cn.com.cloudstone.menu.server.thrift.api.TableInfo;

import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.util.ThriftUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class ThriftLogic extends BaseLogic {

    public TableInfo getTableInfo(String tableId) {
        String tableName = tableId;
        Table table = tableService.getByName(tableName);
        return ThriftUtils.toTableInfo(table);
    }
}
