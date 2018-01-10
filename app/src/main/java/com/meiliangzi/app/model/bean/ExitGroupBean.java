package com.meiliangzi.app.model.bean;

/**
 * Created by Administrator on 2017/6/12.
 */

public class ExitGroupBean {

    private int status;

    private String msg;
    private String  data;
    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
