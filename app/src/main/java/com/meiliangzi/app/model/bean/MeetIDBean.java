package com.meiliangzi.app.model.bean;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class MeetIDBean {



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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * status : 0
     * msg : success
     * data : [{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/835deaa486e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":3},{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/7a3e2be686e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":2},{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/71a76e9886e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":1}]
     */


    private int status;
    private String msg;
    private DataBean data;



    public static class DataBean {
        /**
         * image : http://o7tb2rscn.bkt.clouddn.com/attach/835deaa486e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75
         * id : 3
         */

        private String meeting_capacity;
        private String uid;
        private String token;
        private String meeting_id;

        public String getMeeting_capacity() {
            return meeting_capacity;
        }

        public void setMeeting_capacity(String meeting_capacity) {
            this.meeting_capacity = meeting_capacity;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMeeting_id() {
            return meeting_id;
        }

        public void setMeeting_id(String meeting_id) {
            this.meeting_id = meeting_id;
        }
    }
}
