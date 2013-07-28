/**
 * @(#)ImageService.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.cloudstone.emenu.exception.ServerError;

/**
 * @author xuhongfeng
 *
 */
@Service
public class ImageService extends BaseService implements IImageService {
    
    @Override
    public String saveDishImage(String uriData) {
        try {
            return imageStorage.saveImage(uriData);
        } catch (IOException e) {
            throw new ServerError(e);
        }
    }
    
    @Override
    public String getDishUriData(String imageId) {
        try {
            return imageStorage.getUriData(imageId);
        } catch (IOException e) {
            throw new ServerError(e);
        }
    }
}
