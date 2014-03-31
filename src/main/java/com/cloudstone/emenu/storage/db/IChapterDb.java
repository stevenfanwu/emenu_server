/**
 * @(#)IChapterDb.java, 2013-7-10. 
 *
 */
package com.cloudstone.emenu.storage.db;

import java.util.List;

import com.cloudstone.emenu.EmenuContext;
import com.cloudstone.emenu.data.Chapter;

/**
 * @author xuhongfeng
 */
public interface IChapterDb extends IDb {

    public void addChapter(EmenuContext context, Chapter chapter);

    public void updateChapter(EmenuContext context, Chapter chapter);

    public void deleteChapter(EmenuContext context, int id);

    public List<Chapter> getAllChapter(EmenuContext context);

    public List<Chapter> listChapters(EmenuContext context, int menuId);

    public List<Chapter> listChapters(EmenuContext context, int menuId, int dishId);

    public List<Chapter> listChapters(EmenuContext context, int[] ids);

    public Chapter getChapter(EmenuContext context, int id);

    public Chapter getChapterByName(EmenuContext context, String name);

    public int[] getAllChapterIds(EmenuContext context);
}
