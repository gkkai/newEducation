package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class MessageListBean {

    private int status;

    private String msg;
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

   public class DataBean{
       private CategoryDataBean category;
       public class CategoryDataBean{
           private int type;
           private String id;

           private String title;
           private String img;
           private String content;
           private String create_at;
           private String unread;

           public int getType() {
               return type;
           }

           public void setType(int type) {
               this.type = type;
           }

           public String getId() {
               return id;
           }

           public void setId(String id) {
               this.id = id;
           }

           public String getTitle() {
               return title;
           }

           public void setTitle(String title) {
               this.title = title;
           }

           public String getImg() {
               return img;
           }

           public void setImg(String img) {
               this.img = img;
           }

           public String getContent() {
               return content;
           }

           public void setContent(String content) {
               this.content = content;
           }

           public String getCreate_at() {
               return create_at;
           }

           public void setCreate_at(String create_at) {
               this.create_at = create_at;
           }

           public String getUnread() {
               return unread;
           }

           public void setUnread(String unread) {
               this.unread = unread;
           }
       }
       private NoticeDataBean notice;
       public class NoticeDataBean{
           private int type;
           private String id;

           private String title;
           private String img;
           private String content;
           private String create_at;
           private String unread;

           public int getType() {
               return type;
           }

           public void setType(int type) {
               this.type = type;
           }

           public String getId() {
               return id;
           }

           public void setId(String id) {
               this.id = id;
           }

           public String getTitle() {
               return title;
           }

           public void setTitle(String title) {
               this.title = title;
           }

           public String getImg() {
               return img;
           }

           public void setImg(String img) {
               this.img = img;
           }

           public String getContent() {
               return content;
           }

           public void setContent(String content) {
               this.content = content;
           }

           public String getCreate_at() {
               return create_at;
           }

           public void setCreate_at(String create_at) {
               this.create_at = create_at;
           }

           public String getUnread() {
               return unread;
           }

           public void setUnread(String unread) {
               this.unread = unread;
           }
       }
       private FriendsDataBean friends;
       public class FriendsDataBean{
           private int type;
           private int id;

           private String create_at;
           private String groupName;
           private String userHead;
           private String nickName;

           public int getType() {
               return type;
           }

           public void setType(int type) {
               this.type = type;
           }

           public int getId() {
               return id;
           }

           public void setId(int id) {
               this.id = id;
           }

           public String getCreate_at() {
               return create_at;
           }

           public void setCreate_at(String create_at) {
               this.create_at = create_at;
           }

           public String getGroupName() {
               return groupName;
           }

           public void setGroupName(String groupName) {
               this.groupName = groupName;
           }

           public String getUserHead() {
               return userHead;
           }

           public void setUserHead(String userHead) {
               this.userHead = userHead;
           }

           public String getNickName() {
               return nickName;
           }

           public void setNickName(String nickName) {
               this.nickName = nickName;
           }
       }

       public CategoryDataBean getCategory() {
           return category;
       }

       public void setCategory(CategoryDataBean category) {
           this.category = category;
       }

       public NoticeDataBean getNotice() {
           return notice;
       }

       public void setNotice(NoticeDataBean notice) {
           this.notice = notice;
       }

       public FriendsDataBean getFriends() {
           return friends;
       }

       public void setFriends(FriendsDataBean friends) {
           this.friends = friends;
       }
   }
}
