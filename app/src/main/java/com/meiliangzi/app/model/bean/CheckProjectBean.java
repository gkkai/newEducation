package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class CheckProjectBean {

    /**
     * status : 0
     * msg : success
     * data : [{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/835deaa486e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":3},{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/7a3e2be686e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":2},{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/71a76e9886e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":1}]
     */

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

    public class DataBean {
        private int id;
        private String proName;
        private int project_type;
        private int userid;
        private String start_at;
        private String userName;
        private String end_at;
        private int project_status;
        private int nature;
        private int is_public;
        private int create_check_result;
        private String  create_check_comm;
        private int examine_userid;
        private String examine_userName;
        private String departmentid;
        private String departmentName;
        private int check_status;
        private int is_change;
        private String create_at;
        private int schedule;
        private int is_delete;
        private String proDesc;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public int getProject_type() {
            return project_type;
        }

        public void setProject_type(int project_type) {
            this.project_type = project_type;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getStart_at() {
            return start_at;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getEnd_at() {
            return end_at;
        }

        public void setEnd_at(String end_at) {
            this.end_at = end_at;
        }

        public int getProject_status() {
            return project_status;
        }

        public void setProject_status(int project_status) {
            this.project_status = project_status;
        }

        public int getNature() {
            return nature;
        }

        public void setNature(int nature) {
            this.nature = nature;
        }

        public int getIs_public() {
            return is_public;
        }

        public void setIs_public(int is_public) {
            this.is_public = is_public;
        }

        public int getCreate_check_result() {
            return create_check_result;
        }

        public void setCreate_check_result(int create_check_result) {
            this.create_check_result = create_check_result;
        }

        public String getCreate_check_comm() {
            return create_check_comm;
        }

        public void setCreate_check_comm(String create_check_comm) {
            this.create_check_comm = create_check_comm;
        }

        public int getExamine_userid() {
            return examine_userid;
        }

        public void setExamine_userid(int examine_userid) {
            this.examine_userid = examine_userid;
        }

        public String getExamine_userName() {
            return examine_userName;
        }

        public void setExamine_userName(String examine_userName) {
            this.examine_userName = examine_userName;
        }

        public String getDepartmentid() {
            return departmentid;
        }

        public void setDepartmentid(String departmentid) {
            this.departmentid = departmentid;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public int getCheck_status() {
            return check_status;
        }

        public void setCheck_status(int check_status) {
            this.check_status = check_status;
        }

        public int getIs_change() {
            return is_change;
        }

        public void setIs_change(int is_change) {
            this.is_change = is_change;
        }

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public int getSchedule() {
            return schedule;
        }

        public void setSchedule(int schedule) {
            this.schedule = schedule;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public String getProDesc() {
            return proDesc;
        }

        public void setProDesc(String proDesc) {
            this.proDesc = proDesc;
        }
    }


}
