/**
 * @(#)ImageStorage.java, Jul 27, 2013. 
 * 
 */
package com.cloudstone.emenu.storage.file;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.exception.HttpStatusError;
import com.cloudstone.emenu.util.ImageUtils;
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
        File dir = new File(root, "image");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
    
    
    public String saveImage(String uriData) throws IOException {
        Matcher m = PATTERN_URI_DATA.matcher(uriData);
        m.matches();
        String extension = m.group(1);
        String base64 = m.group(2);
        byte[] bytes = Base64.decodeBase64(base64);
        return saveImage(bytes, extension);
    }
    
    public String saveImage(byte[] bytes, String extension) throws IOException {
        String imageId = MD5Utils.md5(bytes);
        imageId = imageId + "." + extension;
        File file = new File(getImageDir(), imageId);
        FileUtils.writeByteArrayToFile(file, bytes);
        return imageId;
    }
    
    public String getUriData(String imageId) throws IOException {
        byte[] bytes = getImage(imageId);
        return ImageUtils.toUriData(bytes, imageId);
    }
    
    public byte[] getImage(String imageId) throws IOException {
        File file = new File(getImageDir(), imageId);
        if (!file.exists()) {
            throw new HttpStatusError(HttpServletResponse.SC_NOT_FOUND, "file not found: " + file.getAbsolutePath());
        }
        return FileUtils.readFileToByteArray(file);
    }
}