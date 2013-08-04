/**
 * @(#)IPadDb.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Pad;

/**
 * @author xuhongfeng
 *
 */
public interface IPadDb extends IDb {

    public Pad get(int id) throws SQLiteException;
    public List<Pad> listAll() throws SQLiteException;
    public void add(Pad pad) throws SQLiteException;
    public void update(Pad pad) throws SQLiteException;
}
