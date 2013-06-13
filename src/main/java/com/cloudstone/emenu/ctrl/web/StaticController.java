/**
 * @(#)StaticController.java, May 26, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class StaticController extends BaseWebController {
    private static Logger LOG = LoggerFactory.getLogger(StaticController.class);
    
    private static final String[] IMG_EXTENSION = new String[] {
        "png", "img"
    };
    
    @RequestMapping("/static/**")
    public void get(HttpServletRequest request, HttpServletResponse response) throws IOException {
        innerGet(request.getRequestURI().toString(), response);
    }
    
    private void innerGet(String path, HttpServletResponse response) throws IOException {
        path = path.replace("/static/", "");
        if (FilenameUtils.isExtension(path, "js")) {
            path = "js/" + path;
            response.setContentType("text/javascript; charset=UTF-8");
        } else if (FilenameUtils.isExtension(path, "css")) {
            path = "css/" + path;
            response.setContentType("text/css; charset=UTF-8");
        } else if (FilenameUtils.isExtension(path, IMG_EXTENSION)) {
            path = "img/" + path;
            response.setContentType("image/" + FilenameUtils.getExtension(path));
        }
        path = "view/" + path;
        File file = new File(getWebHome(), path);
        LOG.info(file.getAbsolutePath());

        InputStream is = null;
        try {
            is = new FileInputStream(file);
            byte[] bytes = IOUtils.toByteArray(is);

            response.getOutputStream().write(bytes);
        } catch (FileNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND);
        } catch (IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
    
    @RequestMapping("/img/**")
    public void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String path = uri.toString().replace("/img", "/static");
        innerGet(path, response);
    }
}
