package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class VoteBaseBean extends BaseBean {

    /**
     * msg : success
     * data : [{"praise":0,"create_time":"2017/08/22","contentType":1,"name":"神南矿业：\u201c六个约定\u201d奏响追赶超越最强音","img":"http://o7tb2rscn.bkt.clouddn.com/attach/7235271486e411e7b1db00163e000138.jpg?imageView2/1/w/300/h/150/q/75","is_praise":1,"type":true,"id":1}]
     */
    private String msg;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }



    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int vateNumber;
        private String image;
        private int id;
        private String title;
        private int isEnd;
        private int  isInfo;

        public int getVateNumber() {
            return vateNumber;
        }

        public void setVateNumber(int vateNumber) {
            this.vateNumber = vateNumber;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
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

        public int getIsEnd() {
            return isEnd;
        }

        public void setIsEnd(int isEnd) {
            this.isEnd = isEnd;
        }

        public int getIsInfo() {
            return isInfo;
        }

        public void setIsInfo(int isInfo) {
            this.isInfo = isInfo;
        }
    }
}
