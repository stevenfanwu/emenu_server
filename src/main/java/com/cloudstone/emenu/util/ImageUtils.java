/**
 * @(#)ImageUtils.java, 2013-7-7. 
 *
 */
package com.cloudstone.emenu.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;

/**
 * @author xuhongfeng
 */
public class ImageUtils {
    private static final String FORMAT_URI_DATA = "data:image/%s;base64,%s";

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

    public static byte[] resize(byte[] origin, int width, int height, String extension) throws IOException {
        if (width == 0 && height == 0) {
            return origin;
        }
        InputStream input = new ByteArrayInputStream(origin);
        BufferedImage src = ImageIO.read(input);
        BufferedImage dest = Scalr.resize(src, width, height);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(dest, extension, output);
        output.flush();
        return output.toByteArray();
    }

    public static byte[] resize(byte[] origin, int width, String extension) throws IOException {
        if (width == 0) {
            return origin;
        }
        InputStream input = new ByteArrayInputStream(origin);
        BufferedImage src = ImageIO.read(input);
        if (src.getWidth() <= width)
            return origin;
        int height = (int) (src.getHeight() / (src.getWidth() / (double) width));
        BufferedImage dest = Scalr.resize(src, width, height);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(dest, extension, output);
        output.flush();
        return output.toByteArray();
    }

    public static String toUriData(byte[] bytes, String imageId) {
        String base64 = Base64.encodeBase64String(bytes);
        String extension = FilenameUtils.getExtension(imageId);
        return String.format(FORMAT_URI_DATA, extension, base64);
    }
}