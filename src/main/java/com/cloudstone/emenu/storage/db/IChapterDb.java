/**
 * @(#)IChapterDb.java, 2013-7-10. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.data.Chapter;

/**
 * @author xuhongfeng
 *
 */
public interface IChapterDb extends IDb {

    public void addChapter(Chapter chapter) ;
    public void updateChapter(Chapter chapter) ;
    public void deleteChapter(int id) ;
    public List<Chapter> getAllChapter() ;
    public List<Chapter> listChapters(int menuId) ;
    public List<Chapter> listChapters(int[] ids) ;
    public Chapter getChapter(int id) ;
    public Chapter getChapterByName(String name) ;
}
