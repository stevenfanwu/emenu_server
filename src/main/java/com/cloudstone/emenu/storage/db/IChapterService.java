/**
 * @(#)IChapterService.java, 2013-7-10. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.Chapter;

/**
 * @author xuhongfeng
 *
 */
public interface IChapterService {
    public void addChapter(Chapter chapter);
    public void updateChapter(Chapter chapter);
    public void deleteChapter(long id);
    public List<Chapter> getAllChapter();
    public Chapter getChapter(long id);

}
