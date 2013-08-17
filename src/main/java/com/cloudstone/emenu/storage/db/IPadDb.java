/**
 * @(#)IPadDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.Pad;

/**
 * @author xuhongfeng
 *
 */
public interface IPadDb extends IDb {

    public Pad get(int id) ;
    public List<Pad> listAll() ;
    public void add(Pad pad) ;
    public void update(Pad pad) ;
}
