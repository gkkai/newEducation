package com.meiliangzi.app.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kk on 2018/11/14.
 */

public class SendCarUserBean {
    private List<PartmentBean> data;



    public List<PartmentBean> getData() {
        return data;
    }

    public SendCarUserBean() {
        this.data = new ArrayList<PartmentBean>();
    }

    public void setData(List<PartmentBean> data) {
        this.data = data;
    }

    public static class PartmentBean {
        /**
         * id : 4
         * name : 生产技术部
         */

        private int id;
        private String name;
        private List <DepartmentUserlist> departmentuserlist;

        public PartmentBean() {
            this.departmentuserlist = new ArrayList<DepartmentUserlist>();
        }

        public List<DepartmentUserlist> getDepartmentuserlist() {
            return departmentuserlist;
        }

        public void setDepartmentuserlist(List<DepartmentUserlist> departmentuserlist) {
            this.departmentuserlist = departmentuserlist;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public static class DepartmentUserlist{
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
}
