/**
 * @(#)ImageController.java, Jul 28, 2013. 
 *
 */
package com.cloudstone.emenu.ctrl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.storage.file.ImageStorage;
import com.cloudstone.emenu.util.ImageUtils;

/**
 * @author xuhongfeng
 */
@Controller
public class ImageController extends BaseController {
    protected static final String CACHE_CONTROL_VALUE = "max-age=604800";

    @Autowired
    private ImageStorage imageStorage;

    @RequestMapping(value = "/images/{imageId:.+}", method = RequestMethod.GET)
    public void getImage(@PathVariable(value = "imageId") String imageId,
                         @RequestParam(value = "width", defaultValue = "0") int width,
                         @RequestParam(value = "height", defaultValue = "0") int height,
                         @RequestParam(value = "strict", defaultValue = "true") boolean strict,
                         HttpServletResponse response) throws IOException {
        byte[] bytes = imageStorage.getImage(imageId);
        bytes = ImageUtils.resize(bytes, width, height, FilenameUtils.getExtension(imageId));
        response.setContentType(ImageUtils.getContentType(imageId));
        response.setContentLength(bytes.length);
        response.setHeader("Cache-Control", CACHE_CONTROL_VALUE);
        response.getOutputStream().write(bytes);
    }

    @RequestMapping(value = "/images/data/{imageId:.+}", method = RequestMethod.GET)
    public
    @ResponseBody
    String getUriData(@PathVariable(value = "imageId") String imageId,
                      @RequestParam(value = "width", defaultValue = "0") int width,
                      @RequestParam(value = "height", defaultValue = "0") int height,
                      @RequestParam(value = "strict", defaultValue = "true") boolean strict) throws IOException {
        byte[] bytes = imageStorage.getImage(imageId);
        bytes = ImageUtils.resize(bytes, width, height, FilenameUtils.getExtension(imageId));
        return ImageUtils.toUriData(bytes, imageId);
    }
}
