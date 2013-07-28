/**
 * @(#)ImageUtils.java, 2013-7-7. 
 * 
 */
package com.cloudstone.emenu.util;

import org.apache.commons.io.FilenameUtils;

/**
 * @author xuhongfeng
 *
 */
public class ImageUtils {
    
    public static String getContentType(String imageId) {
        String extension = FilenameUtils.getExtension(imageId);
        if (extension.equals("jpg") || extension.equals("jpeg")) {
            return "image/jpeg";
        }
        if (extension.equals("png")) {
            return "image/png";
        }
        if (extension.equals("gif")) {
            return "image/gif";
        }
        return "image/jpeg";
    }
}