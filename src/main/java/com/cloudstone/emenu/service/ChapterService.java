/**
 * @(#)ChapterService.java, 2013-7-10. 
 * 
 */
package com.cloudstone.emenu.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.almworks.sqlite4java.SQLiteException;
import com.cloudstone.emenu.data.Chapter;
import com.cloudstone.emenu.exception.ServerError;

/**
 * @author xuhongfeng
 *
 */
@Service
public class ChapterService extends BaseService implements IChapterService {

    @Override
    public void addChapter(Chapter chapter) {
        try {
            chapterDb.addChapter(chapter);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public void updateChapter(Chapter chapter) {
        try {
            chapterDb.updateChapter(chapter);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
        
    }

    @Override
    public void deleteChapter(long id) {
        try {
            chapterDb.deleteChapter(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Chapter> getAllChapter() {
        try {
            return chapterDb.getAllChapter();
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public Chapter getChapter(long id) {
        try {
            return chapterDb.getChapter(id);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }

    @Override
    public List<Chapter> listByMenuId(long menuId) {
        try {
            return chapterDb.listChapters(menuId);
        } catch (SQLiteException e) {
            throw new ServerError(e);
        }
    }
}
