/**
 * @(#)DeviceLogic.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Pad;
import com.cloudstone.emenu.exception.DataConflictException;
import com.cloudstone.emenu.storage.db.IPadDb;
import com.cloudstone.emenu.util.DataUtils;

/**
 * @author xuhongfeng
 *
 */
@Component
public class DeviceLogic extends BaseLogic {
    @Autowired
    private IPadDb padDb;
    @Autowired
    private ConfigLogic configLogic;
    @Autowired
    private ThriftLogic thriftLogic;

    public Pad getPad(int id) {
        return padDb.get(id);
    }
    
    public List<Pad> listAllPad() {
        List<Pad> pads = padDb.listAll();
        DataUtils.filterDeleted(pads);
        return pads;
    }
    
    public Pad addPad(Pad pad) {
        List<Pad> pads = padDb.listAll();
        Pad sameImei = null;
        Pad sameName = null;
        int count = 0;
        for (Pad p:pads) {
            if (p.getName().equals(pad.getName())) {
                sameName = p;
                if (!p.isDeleted()) {
                    throw new DataConflictException("已存在相同名字的平板");
                }
            }
            if (p.getImei().equals(pad.getImei())) {
                sameImei = p;
                if (!p.isDeleted()) {
                    throw new DataConflictException("已存在相同IMEI的平板");
                }
            }
            if (!p.isDeleted()) {
                count++;
                if (count == configLogic.getPadNumber()) {
                    throw new DataConflictException("当前授权的平板数目最多为" + configLogic.getPadNumber() + "台");
                }
            }
        }
        Pad old = sameImei!=null ? sameImei : sameName;
        long now = System.currentTimeMillis();
        pad.setUpdateTime(now);
        if (old != null) {
            pad.setId(old.getId());
            padDb.update(pad);
        } else {
            pad.setCreatedTime(now);
            padDb.add(pad);
        }
        thriftLogic.onPadChanged();
        return getPad(pad.getId());
    }
    
    public Pad updatePad(Pad pad) {
        List<Pad> pads = padDb.listAll();
        for (Pad p:pads) {
            if (p.getName().equals(pad.getName())) {
                if (!p.isDeleted() && p.getId()!=pad.getId()) {
                    throw new DataConflictException("已存在相同名字的平板");
                }
            }
            if (p.getImei().equals(pad.getImei()) && p.getId()!=pad.getId()) {
                if (!p.isDeleted()) {
                    throw new DataConflictException("已存在相同IMEI的平板");
                }
            }
        }
        long now = System.currentTimeMillis();
        pad.setUpdateTime(now);
        padDb.update(pad);
        thriftLogic.onPadChanged();
        return getPad(pad.getId());
    }
    
    public void deletePad(int id) {
        padDb.delete(id);
        thriftLogic.onPadChanged();
    }
    
    public Pad getPad(String imei) {
        List<Pad> pads = listAllPad();
        for (Pad pad:pads) {
            if (pad.getImei().equals(imei)) {
                return pad;
            }
        }
        return null;
    }
}
