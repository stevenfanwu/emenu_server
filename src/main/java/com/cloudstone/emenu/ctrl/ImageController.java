/**
 * @(#)ImageController.java, Jul 28, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.storage.file.ImageStorage;
import com.cloudstone.emenu.util.ImageUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class ImageController extends BaseController {
    protected static final String CACHE_CONTROL_VALUE = "max-age=604800";
    
    @Autowired
    private ImageStorage imageStorage;

    @RequestMapping(value="/images/{imageId:.+}", method=RequestMethod.GET)
    public void getImage(@PathVariable(value="imageId") String imageId,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        //TODO width height
        byte[] bytes = imageStorage.getImage(imageId);
        response.setContentType(ImageUtils.getContentType(imageId));
        response.setContentLength(bytes.length);
        response.setHeader("Cache-Control", CACHE_CONTROL_VALUE);
        response.getOutputStream().write(bytes);
    }
    
    @RequestMapping(value="/images/data/{imageId:.+}", method=RequestMethod.GET)
    public @ResponseBody String getUriData(@PathVariable(value="imageId") String imageId) throws IOException {
        //TODO width height
        return imageStorage.getUriData(imageId);
    }
}
