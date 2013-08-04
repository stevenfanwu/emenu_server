/**
 * @(#)DeviceService.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Pad;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.db.IPadDb;

/**
 * @author xuhongfeng
 *
 */
@Service
public class DeviceService extends BaseService implements IDeviceService {
    @Autowired
    private IPadDb padDb;

    @Override
    public Pad getPad(int id) {
        try {
            return padDb.get(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Pad> listAllPad() {
        try {
            return padDb.listAll();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void addPad(Pad pad) {
        try {
            padDb.add(pad);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void updatePad(Pad pad) {
        try {
            padDb.update(pad);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void deletePad(int id) {
        try {
            padDb.delete(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
}
