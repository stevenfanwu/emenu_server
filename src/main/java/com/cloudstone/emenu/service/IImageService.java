/**
 * @(#)IImageService.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.service;


/**
 * @author xuhongfeng
 *
 */
public interface IImageService {
    public String saveDishImage(String uriData);
    public String getDishUriData(String imageId);
}
