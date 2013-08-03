/**
 * @(#)ImageLogic.java, Aug 3, 2013. 
 * 
 */
package com.cloudstone.emenu.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.service.ImageService;

/**
 * @author xuhongfeng
 *
 */
@Component
public class ImageLogic extends BaseLogic {
    @Autowired
    private ImageService imageService;
    
    public String saveDishImage(String uriData) {
        return imageService.saveDishImage(uriData);
    }
    
    public String getDishUriData(String imageId) {
        return imageService.getDishUriData(imageId);
    }
}
