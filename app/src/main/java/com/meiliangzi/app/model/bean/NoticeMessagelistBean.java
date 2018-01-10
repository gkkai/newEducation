package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class NoticeMessagelistBean {

    private int status;

    private String msg;
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
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
       private int type;

       private String img;
       private int id;

       private String title;
       private int unread;

       private String create_at;
       private String content;

       public int getType() {
           return type;
       }

       public void setType(int type) {
           this.type = type;
       }

       public String getImg() {
           return img;
       }

       public void setImg(String img) {
           this.img = img;
       }

       public int getId() {
           return id;
       }

       public void setId(int id) {
           this.id = id;
       }

       public String getTitle() {
           return title;
       }

       public void setTitle(String title) {
           this.title = title;
       }

       public int getUnread() {
           return unread;
       }

       public void setUnread(int unread) {
           this.unread = unread;
       }

       public String getCreate_at() {
           return create_at;
       }

       public void setCreate_at(String create_at) {
           this.create_at = create_at;
       }

       public String getContent() {
           return content;
       }

       public void setContent(String content) {
           this.content = content;
       }
   }
}
