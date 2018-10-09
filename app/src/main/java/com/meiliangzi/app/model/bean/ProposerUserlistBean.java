package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class ProposerUserlistBean {


    private int status;
    private String       msg;
    private List<BeanData>       data;

    public List<BeanData> getData() {
        return data;
    }

    public void setData(List<BeanData> data) {
        this.data = data;
    }

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

    public class BeanData{
        private String proposerPhone;
        private int id;
        private String proposerName;

        public String getProposerPhone() {
            return proposerPhone;
        }

        public void setProposerPhone(String proposerPhone) {
            this.proposerPhone = proposerPhone;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProposerName() {
            return proposerName;
        }

        public void setProposerName(String proposerName) {
            this.proposerName = proposerName;
        }
    }
}
