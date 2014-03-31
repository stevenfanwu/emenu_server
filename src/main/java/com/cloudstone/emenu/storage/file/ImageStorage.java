/**
 * @(#)ImageStorage.java, Jul 27, 2013. 
 *
 */
package com.cloudstone.emenu.storage.file;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cloudstone.emenu.exception.HttpStatusError;
import com.cloudstone.emenu.exception.ServerError;
import com.cloudstone.emenu.util.ImageUtils;
import com.cloudstone.emenu.util.MD5Utils;

/**
 * @author xuhongfeng
 */
@Repository
public class ImageStorage extends FileStorage {
    private static final Logger LOG = LoggerFactory.getLogger(ImageStorage.class);

    public File getImageDir() {
        File root = getCloudstoneDataDir();
        File dir = new File(root, "image");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }


    public String saveImage(String uriData) {
        String prefix = uriData.substring(0, 25);
        if (!prefix.startsWith("data:image/")) {
            throw new ServerError("uriData not match");
        }
        int end = prefix.indexOf(";base64,");
        String extension = prefix.substring(11, end);
        String base64 = uriData.substring(end + 8, uriData.length());
        byte[] bytes = Base64.decodeBase64(base64);
        return saveImage(bytes, extension);
    }

    public String saveImage(byte[] bytes, String extension) {
        String imageId = MD5Utils.md5(bytes);
        imageId = imageId + "." + extension;
        File file = new File(getImageDir(), imageId);
        try {
            FileUtils.writeByteArrayToFile(file, bytes);
        } catch (IOException e) {
            throw new ServerError(e);
        }
        return imageId;
    }

    public String getUriData(String imageId) {
        byte[] bytes;
        try {
            bytes = getImage(imageId);
        } catch (IOException e) {
            throw new ServerError(e);
        }
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