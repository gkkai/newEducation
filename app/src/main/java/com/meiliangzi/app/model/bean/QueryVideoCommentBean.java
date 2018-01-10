package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * 视频评论添加
 * Created by Administrator on 2017/8/23.
 */

public class QueryVideoCommentBean  {
    private int status;
    private String msg;
    private List<CommentDataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<CommentDataBean> getData() {
        return data;
    }

    public void setData(List<CommentDataBean> data) {
        this.data = data;
    }

    public class CommentDataBean {
        private int id;
        private String comment;
        private String comment_time;

        private String comment_person;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getComment_time() {
            return comment_time;
        }

        public void setComment_time(String comment_time) {
            this.comment_time = comment_time;
        }

        public String getComment_person() {
            return comment_person;
        }

        public void setComment_person(String comment_person) {
            this.comment_person = comment_person;
        }
    }
}
