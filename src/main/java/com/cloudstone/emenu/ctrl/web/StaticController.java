/**
 * @(#)StaticController.java, May 26, 2013. 
 * 
 */
package com.cloudstone.emenu.ctrl.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cloudstone.emenu.exception.ServerError;

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
        try {
            innerGet(request.getRequestURI().toString(), response);
        } catch (FileNotFoundException e) {
            sendError(response, HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void innerGet(String path, HttpServletResponse response) throws IOException {
        path = path.replace("/static/", "");
        if (FilenameUtils.isExtension(path, "js")) {
            path = "js/" + path;
            response.setContentType("text/javascript; charset=UTF-8");
        } else if (FilenameUtils.isExtension(path, "css")
                || FilenameUtils.isExtension(path, "less")) {
            path = "css/" + path;
            response.setContentType("text/css; charset=UTF-8");
        } else if (FilenameUtils.isExtension(path, IMG_EXTENSION)) {
            path = "img/" + path;
            response.setContentType("image/" + FilenameUtils.getExtension(path));
        }
        path = "view/" + path;
        if (path.endsWith(".handlebars.js")) {
            //handlebars
            response.setContentType("text/javascript; charset=UTF-8");
            path = path.substring(0, path.length()-3);
            File file = new File(getWebHome(), path);
            String content = FileUtils.readFileToString(file, "UTF-8");
            try {
                content = HandlebarsObj.toJavaScript(content);
                LOG.info("handlebars : " + content);
                IOUtils.write(content.getBytes("UTF-8"), response.getOutputStream());
            } catch (Exception e) {
                throw new ServerError(e);
            }
        } else {
            sendFile(response, path);
        }
    }
    
    @RequestMapping("/img/**")
    public void getImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String path = uri.toString().replace("/img", "/static");
        innerGet(path, response);
    }
    
        @SuppressWarnings("unused")
    private static class HandlebarsObj {
        private static final String FORMAT = "define(function (require, exports, module) {return %s;});";
        private static final  ObjectMapper mapper = new ObjectMapper();
        
        private String template;
        
        public String getTemplate() {
            return template;
        }

        public void setTemplate(String template) {
            this.template = template;
        }

        public static String toJavaScript(String content) throws Exception {
            HandlebarsObj obj = new HandlebarsObj();
            obj.template = content;
            return String.format(FORMAT, mapper.writeValueAsString(obj));
        }
    }
}
