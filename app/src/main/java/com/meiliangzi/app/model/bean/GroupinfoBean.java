package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class GroupinfoBean {

    private int status;

    private String msg;
    public BeanData data;
    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public BeanData getData() {
        return data;
    }

    public void setData(BeanData data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public class  BeanData{
        private String groupName;
        private String groupImage;

        private int groupId;
        private int groupUserId;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public String getGroupImage() {
            return groupImage;
        }

        public void setGroupImage(String groupImage) {
            this.groupImage = groupImage;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public int getGroupUserId() {
            return groupUserId;
        }

        public void setGroupUserId(int groupUserId) {
            this.groupUserId = groupUserId;
        }
    }

}
