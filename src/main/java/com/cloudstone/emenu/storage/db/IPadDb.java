/**
 * @(#)IPadDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Pad;

/**
 * @author xuhongfeng
 *
 */
public interface IPadDb extends IDb {

    public Pad get(EmenuContext context, int id) ;
    public List<Pad> listAll(EmenuContext context) ;
    public void add(EmenuContext context, Pad pad) ;
    public void update(EmenuContext context, Pad pad) ;
}
