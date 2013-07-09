/**
 * @(#)ChapterLogic.java, 2013-7-10. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.util.IdGenerator;

/**
 * @author xuhongfeng
 *
 */
@Component
public class ChapterLogic extends BaseLogic {

    public Chapter addChapter(Chapter chapter) {
        chapter.setId(IdGenerator.generateId());
        chapterService.addChapter(chapter);
        return chapterService.getChapter(chapter.getId());
    }
    
    public Chapter getChapter(long id) {
        return chapterService.getChapter(id);
    }
    
    public List<Chapter> getAllChapter() {
        return chapterService.getAllChapter();
    }
    
    public Chapter updateChapter(Chapter chapter) {
        chapterService.updateChapter(chapter);
        return chapterService.getChapter(chapter.getId());
    }
    
    public void deleteChapter(long id) {
        chapterService.deleteChapter(id);
    }

}
