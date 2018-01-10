package com.meiliangzi.app.db.dao;

import android.content.ContentValues;

import com.meiliangzi.app.db.bean.MessageBean;

import java.util.List;
import java.util.Map;

public interface MessageDaoInface {
    public boolean addCache(MessageBean item);

    public boolean deleteCache(String whereClause, String[] whereArgs);

    public boolean updateCache(ContentValues values, String whereClause,
                               String[] whereArgs);

    public Map<String, String> viewCache(String selection,
                                         String[] selectionArgs);

    public List<Map<String, String>> listCache(String selection,
                                               String[] selectionArgs);

    public void clearFeedTable();
}
