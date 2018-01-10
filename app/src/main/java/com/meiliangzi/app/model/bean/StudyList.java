package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */

public class StudyList extends BaseBean {

    /**
     * msg : success
     * data : [{"is_study":true,"id":14,"study_achievement":60,"userStudy":{"contentType":1,"name":"神南矿业：\u201c六个约定\u201d奏响追赶超越最强音","img":"http://o7tb2rscn.bkt.clouddn.com/attach/7235271486e411e7b1db00163e000138.jpg?imageView2/1/w/300/h/150/q/75","create_time":"2017-08-22","type":true,"id":1}},{"is_study":true,"id":13,"study_achievement":60,"userStudy":{"contentType":1,"name":"神南矿业：\u201c六个约定\u201d奏响追赶超越最强音","img":"http://o7tb2rscn.bkt.clouddn.com/attach/7235271486e411e7b1db00163e000138.jpg?imageView2/1/w/300/h/150/q/75","create_time":"2017-08-22","type":true,"id":1}}]
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
         * is_study : true
         * id : 14
         * study_achievement : 60
         * userStudy : {"contentType":1,"name":"神南矿业：\u201c六个约定\u201d奏响追赶超越最强音","img":"http://o7tb2rscn.bkt.clouddn.com/attach/7235271486e411e7b1db00163e000138.jpg?imageView2/1/w/300/h/150/q/75","create_time":"2017-08-22","type":true,"id":1}
         */

        private boolean is_study;
        private boolean study_status;
        private String id;
        private int study_achievement;
        private UserStudyBean userStudy;

        public void setStudy_status(boolean study_status) {
            this.study_status = study_status;
        }

        public boolean isStudy_status() {
            return study_status;
        }

        public boolean isIs_study() {
            return is_study;
        }

        public void setIs_study(boolean is_study) {
            this.is_study = is_study;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStudy_achievement() {
            return study_achievement;
        }

        public void setStudy_achievement(int study_achievement) {
            this.study_achievement = study_achievement;
        }

        public UserStudyBean getUserStudy() {
            return userStudy;
        }

        public void setUserStudy(UserStudyBean userStudy) {
            this.userStudy = userStudy;
        }

        public static class UserStudyBean {
            /**
             * contentType : 1
             * name : 神南矿业：“六个约定”奏响追赶超越最强音
             * img : http://o7tb2rscn.bkt.clouddn.com/attach/7235271486e411e7b1db00163e000138.jpg?imageView2/1/w/300/h/150/q/75
             * create_time : 2017-08-22
             * type : true
             * id : 1
             */

            private int contentType;
            private String name;
            private String img;
            private String create_time;
            private boolean type;
            private String id;

            public int getContentType() {
                return contentType;
            }

            public void setContentType(int contentType) {
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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
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
}
