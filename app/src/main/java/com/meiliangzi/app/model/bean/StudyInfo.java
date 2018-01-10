package com.meiliangzi.app.model.bean;

/**
 * Created by Administrator on 2017/8/23.
 */

public class StudyInfo extends BaseBean {

    /**
     * msg : success
     * data : {"yesCourses":0,"logDays":1,"id":3,"studyTime":"0时0分"}
     */

    private String msg;
    private DataBean data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * yesCourses : 0
         * logDays : 1
         * id : 3
         * studyTime : 0时0分
         */

        private int yesCourses;
        private int logDays;
        private int id;
        private String studyTime;

        public int getYesCourses() {
            return yesCourses;
        }

        public void setYesCourses(int yesCourses) {
            this.yesCourses = yesCourses;
        }

        public int getLogDays() {
            return logDays;
        }

        public void setLogDays(int logDays) {
            this.logDays = logDays;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getStudyTime() {
            return studyTime;
        }

        public void setStudyTime(String studyTime) {
            this.studyTime = studyTime;
        }
    }
}
