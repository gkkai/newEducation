package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class GroupListBean {

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

    public class DataBean{
        private int id;

        private String groupName;
        private String image;
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }


        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
