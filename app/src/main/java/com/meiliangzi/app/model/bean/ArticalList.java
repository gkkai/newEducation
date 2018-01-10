package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class ArticalList extends BaseBean {

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
        /**
         * praise : 0
         * create_time : 2017/08/22
         * contentType : 1
         * name : 神南矿业：“六个约定”奏响追赶超越最强音
         * img : http://o7tb2rscn.bkt.clouddn.com/attach/7235271486e411e7b1db00163e000138.jpg?imageView2/1/w/300/h/150/q/75
         * is_praise : 1
         * type : true
         * id : 1
         */

        private String praise;
        private String create_time;
        private String contentType;
        private String name;
        private String img;
        private String is_praise;
        private boolean type;
        private String id;

        public String getPraise() {
            return praise;
        }

        public void setPraise(String praise) {
            this.praise = praise;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getContentType() {
            return contentType;
        }

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getIs_praise() {
            return is_praise;
        }

        public void setIs_praise(String is_praise) {
            this.is_praise = is_praise;
        }

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
