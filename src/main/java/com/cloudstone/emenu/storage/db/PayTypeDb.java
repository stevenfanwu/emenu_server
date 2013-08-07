/**
 * @(#)PayTypeDb.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.PayType;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class PayTypeDb extends IdNameDb<PayType> implements IPayTypeDb {
    private static final String TABLE_NAME = "payType";

    private static final String[] DEFAULT_PAY_TYPE = {
        "现金", "刷卡", "会员卡", "签单"
    };
    
    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
    
    @Override
    protected void init() throws SQLiteException {
        super.init();
        try {
            if (getAll().size() == 0) {
                for (String name:DEFAULT_PAY_TYPE) {
                    PayType type = new PayType();
                    type.setName(name);
                    add(type);
                }
            }
        } catch (SQLiteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected PayType newObject() {
        return new PayType();
    }

    @Override
    public List<PayType> getAllPayType() throws SQLiteException {
        return getAll();
    }

    @Override
    public void addPayType(PayType payType) throws SQLiteException {
        add(payType);
    }

    
}
