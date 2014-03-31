/**
 * @(#)ImageLogic.java, Aug 3, 2013. 
 *
 */
package com.cloudstone.emenu.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.storage.file.ImageStorage;

/**
 * @author xuhongfeng
 */
@Component
public class ImageLogic extends BaseLogic {
    @Autowired
    private ImageStorage imageStorage;

    public String saveDishImage(String uriData) {
        return imageStorage.saveImage(uriData);
    }

    public String getDishUriData(String imageId) {
        return imageStorage.getUriData(imageId);
    }
}
