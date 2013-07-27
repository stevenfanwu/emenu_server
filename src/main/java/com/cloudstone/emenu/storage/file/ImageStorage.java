/**
 * @(#)ImageStorage.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.file;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.util.MD5Utils;

/**
 * @author xuhongfeng
 *
 */
@Repository
public class ImageStorage extends FileStorage {
    private static final Logger LOG = LoggerFactory.getLogger(ImageStorage.class);
    
    private static final Pattern PATTERN_URI_DATA = Pattern.compile("data:image/(\\w+);base64,(.*)");
    
    public File getImageDir() {
        File root = getCloudstoneDataDir();
        return new File(root, "image");
    }
    
    private File getDishImageDir() {
        File dir = new File(getImageDir(), "dish");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    public String saveDishImage(String uriData) throws IOException {
        Matcher m = PATTERN_URI_DATA.matcher(uriData);
        m.matches();
        String extension = m.group(1);
        String base64 = m.group(2);
        byte[] bytes = Base64.decodeBase64(base64);
        return saveDishImage(bytes, extension);
    }
    
    public String saveDishImage(byte[] bytes, String extension) throws IOException {
        String imageId = MD5Utils.md5(bytes);
        String fileName = imageId + "." + extension;
        File file = new File(getDishImageDir(), fileName);
        FileUtils.writeByteArrayToFile(file, bytes);
        return imageId;
    }
}