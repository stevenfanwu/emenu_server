package com.cloudstone.emenu.storage.db;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.DailyStat;

public class StatDb implements IStatDb {

    @Override
    public int getMaxId() throws SQLiteException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public String getTableName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(int id) throws SQLiteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void add(DailyStat stat) throws SQLiteException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public DailyStat get(long day) throws SQLiteException {
        // TODO Auto-generated method stub
        return null;
    }

}
