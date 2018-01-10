package com.meiliangzi.app.model.bean;

/**
 * Created by Administrator on 2017/8/23.
 */

public class HomePageBean extends BaseBean {

    /**
     * msg : success
     * data : [{"praise":0,"create_time":"2017/08/22","contentType":1,"name":"神南矿业：\u201c六个约定\u201d奏响追赶超越最强音","img":"http://o7tb2rscn.bkt.clouddn.com/attach/7235271486e411e7b1db00163e000138.jpg?imageView2/1/w/300/h/150/q/75","is_praise":1,"type":true,"id":1}]
     */

    private String msg;
    private  DataBean data;

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

        private String cloudsstock;
        private String turnover;
        private String cooperation;

        public String getCloudsstock() {
            return cloudsstock;
        }

        public void setCloudsstock(String cloudsstock) {
            this.cloudsstock = cloudsstock;
        }

        public String getTurnover() {
            return turnover;
        }

        public void setTurnover(String turnover) {
            this.turnover = turnover;
        }

        public String getCooperation() {
            return cooperation;
        }

        public void setCooperation(String cooperation) {
            this.cooperation = cooperation;
        }
    }
}
