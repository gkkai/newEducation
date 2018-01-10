package com.meiliangzi.app.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.meiliangzi.app.db.SQLHelper;
import com.meiliangzi.app.db.bean.MessageBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageDao implements MessageDaoInface {
    private SQLHelper helper = null;

    public MessageDao(Context context) {
        if(helper ==null){
            helper = new SQLHelper(context);
        }
    }

    @Override
    public boolean addCache(MessageBean item) {
        // TODO Auto-generated method stub
        boolean flag = false;
        SQLiteDatabase database = null;
        long id = -1;
        int count = 0;
		Cursor cursor = null;
        try {
            database = helper.getWritableDatabase();
			String sql = "select count(1) as count from "+ SQLHelper.TABLE_MESSAGE+" where "+ SQLHelper.ID+"=" + item.getId(); // 定义SQL
			cursor = database.rawQuery(sql, null);
			while (cursor.moveToNext())
			{
				count = cursor.getInt(cursor.getColumnIndex("count"));
			}
			Log.i("grage","count==="+count);
			if (count==0){
            ContentValues values = new ContentValues();
            values.put(SQLHelper.KEY, item.getKey());
            values.put(SQLHelper.CONTENT, item.getContent());
            values.put(SQLHelper.ID, item.getId());
            values.put(SQLHelper.TITLE, item.getTitle());
            values.put(SQLHelper.IMAGE, item.getImage());
            values.put(SQLHelper.TIME, System.currentTimeMillis()+"");
            id = database.insert(SQLHelper.TABLE_MESSAGE, null, values);
            flag = (id != -1 ? true : false);
			}
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
//			if (cursor!=null){
//				cursor.close();
//			}
        }
        return flag;
    }

    @Override
    public boolean deleteCache(String whereClause, String[] whereArgs) {
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            count = database.delete(SQLHelper.TABLE_MESSAGE, whereClause, whereArgs);
            flag = (count > 0 ? true : false);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public boolean updateCache(ContentValues values, String whereClause,
                               String[] whereArgs) {
        // TODO Auto-generated method stub
        boolean flag = false;
        SQLiteDatabase database = null;
        int count = 0;
        try {
            database = helper.getWritableDatabase();
            count = database.update(SQLHelper.TABLE_MESSAGE, values, whereClause, whereArgs);
            flag = (count > 0 ? true : false);
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return flag;
    }

    @Override
    public Map<String, String> viewCache(String selection,
                                         String[] selectionArgs) {
        // TODO Auto-generated method stub
        SQLiteDatabase database = null;
        Cursor cursor = null;
        Map<String, String> map = new HashMap<String, String>();
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(true, SQLHelper.TABLE_MESSAGE, null, selection,
                    selectionArgs, null, null, null, null);
            int cols_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                for (int i = 0; i < cols_len; i++) {
                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor
                            .getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return map;
    }

    @Override
    public List<Map<String, String>> listCache(String selection, String[] selectionArgs) {
        // TODO Auto-generated method stub
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        SQLiteDatabase database = null;
        Cursor cursor = null;
        try {
            database = helper.getReadableDatabase();
            cursor = database.query(false, SQLHelper.TABLE_MESSAGE, null, selection, selectionArgs, null, null, null, null);
            int cols_len = cursor.getColumnCount();
            while (cursor.moveToNext()) {
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < cols_len; i++) {

                    String cols_name = cursor.getColumnName(i);
                    String cols_values = cursor.getString(cursor
                            .getColumnIndex(cols_name));
                    if (cols_values == null) {
                        cols_values = "";
                    }
                    map.put(cols_name, cols_values);
                }
                list.add(map);
            }

        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (database != null) {
                database.close();
            }
        }
        return list;
    }

    public void clearFeedTable() {
        String sql = "DELETE FROM " + SQLHelper.TABLE_MESSAGE + ";";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
        revertSeq();
    }

    private void revertSeq() {
        String sql = "update sqlite_sequence set seq=0 where name='"
                + SQLHelper.TABLE_MESSAGE + "'";
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL(sql);
    }

}
