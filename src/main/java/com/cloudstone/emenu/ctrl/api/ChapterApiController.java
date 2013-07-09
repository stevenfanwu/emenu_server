/**
 * @(#)ChapterApiController.java, 2013-7-10. 
 * 
 */
package com.cloudstone.emenu.ctrl.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.util.JsonUtils;

/**
 * @author xuhongfeng
 *
 */
@Controller
public class ChapterApiController extends BaseApiController {

    @RequestMapping(value="/api/chapters", method=RequestMethod.POST)
    public @ResponseBody Chapter addChapter(@RequestBody String body, HttpServletResponse resp) {
        Chapter chapter = JsonUtils.fromJson(body, Chapter.class);
        chapter = chapterLogic.addChapter(chapter);
        sendSuccess(resp, HttpServletResponse.SC_CREATED);
        return chapter;
    }
    
    @RequestMapping(value="/api/chapters/{id:[\\d]+}", method=RequestMethod.DELETE)
    public void delete(@PathVariable(value="id") long id,
            HttpServletResponse response) {
        chapterLogic.deleteChapter(id);
    }

    @RequestMapping(value="/api/chapters", method=RequestMethod.GET)
    public @ResponseBody List<Chapter> get() {
        return chapterLogic.getAllChapter();
    }
    
    @RequestMapping(value="/api/chapters/{id:[\\d]+}", method=RequestMethod.PUT)
    public @ResponseBody Chapter update(@PathVariable(value="id") long id,
            @RequestBody String body, HttpServletResponse response) {
        Chapter chapter = JsonUtils.fromJson(body, Chapter.class);
        if (chapter.getId() != id) {
            sendError(response, HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        return chapterLogic.updateChapter(chapter);
    }
}
