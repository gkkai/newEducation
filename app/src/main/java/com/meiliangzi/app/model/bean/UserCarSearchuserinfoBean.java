package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by kk on 2018/11/14.
 */

public class UserCarSearchuserinfoBean {
    private int status;
    private String msg;
    private List<DepartmentName> data;

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

    public List<DepartmentName> getData() {
        return data;
    }

    public void setData(List<DepartmentName> data) {
        this.data = data;
    }

    public class DepartmentName {


        private String userPhone;
        private String nickName;
        private int id;
        private String departmentName;


        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
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

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }
    }
}
