package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by kk on 17/11/23.
 */

public class DepartmentuserNumberBean {
    private String msg;
    private int errno;
    public List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public class DataBean{
        private String departmentName;
        private int id;
        private int number;

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public List<DepartmentUser> getDepartmentUser() {
            return departmentUser;
        }

        public void setDepartmentUser(List<DepartmentUser> departmentUser) {
            this.departmentUser = departmentUser;
        }

        public List<DepartmentUser> departmentUser;
        public class DepartmentUser{
            private String userHead;
            private String nickName;
            private int id;
            private int isFriends;

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

            public int getIsFriends() {
                return isFriends;
            }

            public void setIsFriends(int isFriends) {
                this.isFriends = isFriends;
            }
        }
    }

}
