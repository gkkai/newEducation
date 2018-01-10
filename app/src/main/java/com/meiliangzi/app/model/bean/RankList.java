package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class RankList extends BaseBean {

    /**
     * msg : success
     * data : [{"userName":"张萌","avgAchievement":60,"id":1,"userCode":181017,"coursesCounts":100},{"userName":"姚银河","avgAchievement":60,"id":2,"userCode":5813,"coursesCounts":100},{"userName":"李刚","avgAchievement":60,"id":6,"userCode":5812,"coursesCounts":100},{"userName":"流星泪","avgAchievement":20,"id":5,"userCode":0,"coursesCounts":100},{"userName":"陈星霖","avgAchievement":13,"id":4,"userCode":108188,"coursesCounts":50},{"userName":"xiaobo","avgAchievement":0,"id":3,"userCode":0,"coursesCounts":0},{"userName":"heh","avgAchievement":0,"id":7,"userCode":0,"coursesCounts":0}]
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
         * userName : 张萌
         * avgAchievement : 60
         * id : 1
         * userCode : 181017
         * coursesCounts : 100
         */

        private String userName;
        private int avgAchievement;
        private int id;
        private int userCode;
        private int coursesCounts;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getAvgAchievement() {
            return avgAchievement;
        }

        public void setAvgAchievement(int avgAchievement) {
            this.avgAchievement = avgAchievement;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserCode() {
            return userCode;
        }

        public void setUserCode(int userCode) {
            this.userCode = userCode;
        }

        public int getCoursesCounts() {
            return coursesCounts;
        }

        public void setCoursesCounts(int coursesCounts) {
            this.coursesCounts = coursesCounts;
        }
    }
}
