package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by kk on 2018/11/14.
 */

public class departmentuserlistBean {

   private String msg;
    private int       status;
    private List<DepartmentUserlist> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DepartmentUserlist> getData() {
        return data;
    }

    public void setData(List<DepartmentUserlist> data) {
        this.data = data;
    }

    public class DepartmentUserlist{
            private String userPhone;
            private String       nickName;
            private int id;

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
        }

}
