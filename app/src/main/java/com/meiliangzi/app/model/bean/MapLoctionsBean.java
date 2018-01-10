package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27 0027.
 */

public class MapLoctionsBean {

    /**
     * status : 0
     * msg : success
     * data : [{"news_title":"陕西日报：勇当陕西工业增长的\u201c排头兵\u201d","news_type":true,"id":5},{"news_title":"黄陵矿业铁运公司单日运量45654吨创新高","news_type":true,"id":4},{"news_title":"陕西煤业化工新型能源有限公司改革纪实","news_type":true,"id":3},{"news_title":"陕煤神南张家峁矿业公司实现安全生产3000天","news_type":true,"id":2},{"news_title":"陕煤集团已落地债转股融资金额达372.5亿元","news_type":true,"id":1}]
     */


    private int errno;
    private String msg;
    private List<DataBean> data;
    private int number;

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static class DataBean {
       private String latitude;
        private String longitude;
        private String name;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
