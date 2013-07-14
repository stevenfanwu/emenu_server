/**
 * @(#)ChapterLogic.java, 2013-7-10. 
 * 
 */
package com.cloudstone.emenu.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.data.MenuPage;
import com.cloudstone.emenu.util.IdGenerator;

/**
 * @author xuhongfeng
 *
 */
@Component
public class ChapterLogic extends BaseLogic {
    @Autowired
    private MenuPageLogic menuPageLogic;

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
    
    public void deleteChapter(final long id) {
        chapterService.deleteChapter(id);
        runTask(new Runnable() {
            @Override
            public void run() {
                List<MenuPage> pages = menuPageLogic.listMenuPage(id);
                for (MenuPage page:pages) {
                    menuPageLogic.deleteMenuPage(page.getId());
                }
            }
        });
    }
    
    public List<Chapter> listByMenuId(long menuId) {
        return chapterService.listByMenuId(menuId);
    }
    
    public void deleteChaptersByMenuId(long menuId) {
        List<Chapter> chapters = listByMenuId(menuId);
        for(Chapter chapter:chapters) {
            deleteChapter(chapter.getId());
        }
    }

}
