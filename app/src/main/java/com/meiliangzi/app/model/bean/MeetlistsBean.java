package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27 0027.
 */

public class MeetlistsBean {

    /**
     * status : 0
     * msg : success
     * data : [{"news_title":"陕西日报：勇当陕西工业增长的\u201c排头兵\u201d","news_type":true,"id":5},{"news_title":"黄陵矿业铁运公司单日运量45654吨创新高","news_type":true,"id":4},{"news_title":"陕西煤业化工新型能源有限公司改革纪实","news_type":true,"id":3},{"news_title":"陕煤神南张家峁矿业公司实现安全生产3000天","news_type":true,"id":2},{"news_title":"陕煤集团已落地债转股融资金额达372.5亿元","news_type":true,"id":1}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String real_start_time;
        private String meeting_type;
        private String join_password;
        private String title;



        private String meeting_app_value;
        private int reserve_length;
        private String meeting_app_id;
        private int id;
        private int reserve_number;
        private String meeting_password;

        public String getReal_start_time() {
            return real_start_time;
        }

        public void setReal_start_time(String real_start_time) {
            this.real_start_time = real_start_time;
        }

        public String getMeeting_type() {
            return meeting_type;
        }

        public void setMeeting_type(String meeting_type) {
            this.meeting_type = meeting_type;
        }

        public String getJoin_password() {
            return join_password;
        }

        public void setJoin_password(String join_password) {
            this.join_password = join_password;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMeeting_app_value() {
            return meeting_app_value;
        }

        public void setMeeting_app_value(String meeting_app_value) {
            this.meeting_app_value = meeting_app_value;
        }

        public int getReserve_length() {
            return reserve_length;
        }

        public void setReserve_length(int reserve_length) {
            this.reserve_length = reserve_length;
        }

        public String getMeeting_app_id() {
            return meeting_app_id;
        }

        public void setMeeting_app_id(String meeting_app_id) {
            this.meeting_app_id = meeting_app_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getReserve_number() {
            return reserve_number;
        }

        public void setReserve_number(int reserve_number) {
            this.reserve_number = reserve_number;
        }

        public String getMeeting_password() {
            return meeting_password;
        }

        public void setMeeting_password(String meeting_password) {
            this.meeting_password = meeting_password;
        }
    }
}
