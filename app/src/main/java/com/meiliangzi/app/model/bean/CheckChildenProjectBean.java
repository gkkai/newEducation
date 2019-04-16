package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class CheckChildenProjectBean {

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
        private String name;
        private int userid;
        private String userName;
        private String start_at;
        private String desc;
        private String end_at;
        private int project_id;
        private int project_status;
        private int project_status_red;
        private String assisting_departmentname;
        private String standard;
        private int schedule;
        private int check_status;
        private int examine_userid;
        private String examines_userName;
        private String measures;
        private int is_change;
        private String create_at;
        private int is_publisher;
        private int is_delete;

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

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getStart_at() {
            return start_at;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getEnd_at() {
            return end_at;
        }

        public void setEnd_at(String end_at) {
            this.end_at = end_at;
        }

        public int getProject_id() {
            return project_id;
        }

        public void setProject_id(int project_id) {
            this.project_id = project_id;
        }

        public int getProject_status() {
            return project_status;
        }

        public void setProject_status(int project_status) {
            this.project_status = project_status;
        }

        public int getProject_status_red() {
            return project_status_red;
        }

        public void setProject_status_red(int project_status_red) {
            this.project_status_red = project_status_red;
        }

        public String getAssisting_departmentname() {
            return assisting_departmentname;
        }

        public void setAssisting_departmentname(String assisting_departmentname) {
            this.assisting_departmentname = assisting_departmentname;
        }

        public String getStandard() {
            return standard;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }

        public int getSchedule() {
            return schedule;
        }

        public void setSchedule(int schedule) {
            this.schedule = schedule;
        }

        public int getCheck_status() {
            return check_status;
        }

        public void setCheck_status(int check_status) {
            this.check_status = check_status;
        }

        public int getExamine_userid() {
            return examine_userid;
        }

        public void setExamine_userid(int examine_userid) {
            this.examine_userid = examine_userid;
        }

        public String getExamines_userName() {
            return examines_userName;
        }

        public void setExamines_userName(String examines_userName) {
            this.examines_userName = examines_userName;
        }

        public String getMeasures() {
            return measures;
        }

        public void setMeasures(String measures) {
            this.measures = measures;
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

        public int getIs_publisher() {
            return is_publisher;
        }

        public void setIs_publisher(int is_publisher) {
            this.is_publisher = is_publisher;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }
    }


}
