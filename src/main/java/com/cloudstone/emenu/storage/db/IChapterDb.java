/**
 * @(#)IChapterDb.java, 2013-7-10. 
 * 
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Chapter;

/**
 * @author xuhongfeng
 *
 */
public interface IChapterDb extends IDb {

    public void addChapter(Chapter chapter) throws SQLiteException;
    public void updateChapter(Chapter chapter) throws SQLiteException;
    public void deleteChapter(int id) throws SQLiteException;
    public List<Chapter> getAllChapter() throws SQLiteException;
    public List<Chapter> listChapters(int menuId) throws SQLiteException;
    public Chapter getChapter(int id) throws SQLiteException;
}
