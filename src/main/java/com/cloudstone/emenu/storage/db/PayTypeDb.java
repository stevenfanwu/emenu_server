/**
 * @(#)PayTypeDb.java, Aug 1, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.EmenuContext;
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
    protected void init(EmenuContext context) {
        super.init(context);
        if (getAll(context).size() == 0) {
            for (String name:DEFAULT_PAY_TYPE) {
                PayType type = new PayType();
                type.setName(name);
                add(context, type);
            }
        }
    }

    @Override
    protected PayType newObject() {
        return new PayType();
    }

    @Override
    public List<PayType> getAllPayType(EmenuContext context) {
        return getAll(context);
    }

    @Override
    public void addPayType(EmenuContext context, PayType payType) {
        add(context, payType);
    }

}
