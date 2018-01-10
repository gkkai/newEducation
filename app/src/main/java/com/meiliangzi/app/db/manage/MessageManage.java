package com.meiliangzi.app.db.manage;

import android.database.SQLException;
import android.util.Log;

import com.meiliangzi.app.db.SQLHelper;
import com.meiliangzi.app.db.bean.MessageBean;
import com.meiliangzi.app.db.dao.MessageDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MessageManage {
    public static MessageManage channelManage;
    /**
     * 默认的用户选择频道列表
     */
    public static List<MessageBean> defaultUserChannels = new ArrayList<MessageBean>();


    static {
        defaultUserChannels = new ArrayList<MessageBean>();


    }

    private MessageDao messageDao;
    /**
     * 判断数据库中是否存在用户数据
     */
    private boolean userExist = false;

    private MessageManage(SQLHelper paramDBHelper) throws SQLException {
        if (messageDao == null)
            messageDao = new MessageDao(paramDBHelper.getContext());
        // NavigateItemDao(paramDBHelper.getDao(NavigateItem.class));
        return;
    }

    /**
     * 初始化频道管理类
     *
     * @throws SQLException
     */
    public static MessageManage getManage(SQLHelper dbHelper) throws SQLException {
        if (channelManage == null)
            channelManage = new MessageManage(dbHelper);
        return channelManage;
    }

    /**
     * 清除所有的频道
     */
    public void deleteAllChannel() {
        messageDao.clearFeedTable();
    }

    /**
     * 获取其消息列表
     *
     * @return 数据库存在用户配置 ? 数据库内的用户选择频道 : 默认用户选择频道 ;
     */
    public List<MessageBean> getUserChannel() {
        Object cacheList = messageDao.listCache(null, null);
//        Object cacheList = messageDao.listCache(SQLHelper.SELECTED + "= ?", new String[]{"1"});
        if (cacheList != null && !((List) cacheList).isEmpty()) {
            userExist = true;
            List<Map<String, String>> maplist = (List) cacheList;
            int count = maplist.size();
            List<MessageBean> list = new ArrayList<MessageBean>();
            for (int i = 0; i < count; i++) {
                MessageBean navigate = new MessageBean();
                navigate.setId(maplist.get(i).get(SQLHelper.ID));
                navigate.setContent(maplist.get(i).get(SQLHelper.CONTENT));
                navigate.setImage(maplist.get(i).get(SQLHelper.IMAGE));
                navigate.setTitle(maplist.get(i).get(SQLHelper.TITLE));
                navigate.setKey(maplist.get(i).get(SQLHelper.KEY));
                navigate.setTime(maplist.get(i).get(SQLHelper.TIME));
//				navigate.setSelected(Integer.valueOf(maplist.get(i).get(SQLHelper.SELECTED)));
                list.add(navigate);
            }
            return list;
        }
        initDefaultChannel();
        return defaultUserChannels;
    }



//    /**
//     * 保存用户频道到数据库
//     *
//     * @param userList
//     */
    public void saveUserChannel(List<MessageBean> userList) {
        for (int i = 0; i < userList.size(); i++) {
            MessageBean bean = (MessageBean) userList.get(i);
            messageDao.addCache(bean);
        }
    }


    /**
     * 保存用户频道到数据库
     *
     * @param messageBean
     */
    public boolean addChannel(MessageBean messageBean) {
      return   messageDao.addCache(messageBean);
    }


    public void deleteChannelById(String id){
        messageDao.deleteCache(SQLHelper.ID+"=?",new String[]{id});
    }


    /**
     * 初始化数据库内的频道数据
     */
    private void initDefaultChannel() {
        Log.d("deleteAll", "deleteAll");
        deleteAllChannel();
        saveUserChannel(defaultUserChannels);
    }
}
