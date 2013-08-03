/**
 * @(#)ImageService.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.storage.file.ImageStorage;

/**
 * @author xuhongfeng
 *
 */
@Service
public class ImageService extends BaseService implements IImageService {
    
    @Autowired
    private ImageStorage imageStorage;
    
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
