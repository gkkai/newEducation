package com.meiliangzi.app.ui.view.Academy.bean;

/**
 * Created by zhangyipeng on 16/6/30.
 */
public class BaseInfo {

    /**
     * code : 0
     * success : true
     * msg :
     */

    private int code;
    private String success;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return data;
    }

    public void setMsg(String msg) {
        this.data = msg;
    }
}
