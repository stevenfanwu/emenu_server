/**
 * @(#)ThriftUtils.java, Jul 26, 2013. 
 *
 */
package com.cloudstone.emenu.util;

import cn.com.cloudstone.menu.server.thrift.api.GoodState;
import cn.com.cloudstone.menu.server.thrift.api.GoodsOrder;
import cn.com.cloudstone.menu.server.thrift.api.PageLayoutType;
import cn.com.cloudstone.menu.server.thrift.api.TableStatus;
import cn.com.cloudstone.menu.server.thrift.api.UserType;

import com.cloudstone.emenu.constant.Const;
import com.cloudstone.emenu.constant.Const.OrderDishStatus;
import com.cloudstone.emenu.data.Table;
import com.cloudstone.emenu.data.User;

/**
 * @author xuhongfeng
 */
public class ThriftUtils {
    public static UserType getUserType(User user) {
        if (user.getType() == Const.UserType.USER) {
            return UserType.Waiter;
        } else {
            return UserType.Admin;
        }
    }

    public static TableStatus getTableStatus(Table table) {
        TableStatus status = TableStatus.Empty;
        switch (table.getStatus()) {
            case Const.TableStatus.OCCUPIED:
                status = TableStatus.Occupied;
                break;
            case Const.TableStatus.ORDERED:
                status = TableStatus.Ordered;
                break;
        }
        return status;
    }

    public static PageLayoutType getPageLayoutType(com.cloudstone.emenu.data.MenuPage page) {
        PageLayoutType type = PageLayoutType.Triangle4;
        switch (page.getDishCount()) {
            case 1:
                type = PageLayoutType.Horizontal1;
                break;
            case 2:
                type = PageLayoutType.Horizontal2;
                break;
            case 3:
                type = PageLayoutType.Horizontal3;
                break;
            case 6:
                type = PageLayoutType.Grid6;
                break;
        }
        return type;
    }

    public static int getOrderDishStatus(GoodsOrder g) {
        GoodState state = g.getGoodstate();
        if (state == GoodState.Ordered) {
            return OrderDishStatus.ORDERED;
        } else if (state == GoodState.Canceled) {
            return OrderDishStatus.CANCELED;
        } else if (state == GoodState.Servered) {
            return OrderDishStatus.SERVED;
        } else if (state == GoodState.Takeout) {
            return OrderDishStatus.TAKE_OUT;
        } else {
            return OrderDishStatus.WAITING;
        }
    }
}
