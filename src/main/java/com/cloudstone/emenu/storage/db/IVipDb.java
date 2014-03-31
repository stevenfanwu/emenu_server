package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Vip;

/**
 * @author carelife
 */
public interface IVipDb extends IDb {
    public Vip add(EmenuContext context, Vip vip);

    public Vip update(EmenuContext context, Vip vip);

    public Vip get(EmenuContext context, int vipId);

    public Vip getByName(EmenuContext context, String name);

    public List<Vip> getAll(EmenuContext context);

    public double recharge(EmenuContext context, int id, double money);

    public void delete(EmenuContext context, int vipId);
}
