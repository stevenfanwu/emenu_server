/**
 * @(#)IDeviceService.java, Aug 4, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import com.cloudstone.emenu.data.Pad;

/**
 * @author xuhongfeng
 *
 */
public interface IDeviceService {
    public Pad getPad(int id);
    public List<Pad> listAllPad();
    public void addPad(Pad pad);
    public void updatePad(Pad pad);
    public void deletePad(int id);

}
