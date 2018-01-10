package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class MessageChatIMBean {

    private int errno;

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

    public void setStatus(int status) {
        this.errno = status;
    }

    public int getStatus() {
        return errno;
    }
    public class DataBean {
        private String userHead;
        private String nickName;
        private int id;

        public String getUserHead() {
            return userHead;
        }

        public void setUserHead(String userHead) {
            this.userHead = userHead;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
