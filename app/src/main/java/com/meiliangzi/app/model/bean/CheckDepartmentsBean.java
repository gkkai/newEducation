package com.meiliangzi.app.model.bean;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class CheckDepartmentsBean {

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
        private String create_at;
        private int is_delete;
        private int company_id;
        private String manager;

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

        public String getCreate_at() {
            return create_at;
        }

        public void setCreate_at(String create_at) {
            this.create_at = create_at;
        }

        public int getIs_delete() {
            return is_delete;
        }

        public void setIs_delete(int is_delete) {
            this.is_delete = is_delete;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public String getManager() {
            return manager;
        }

        public void setManager(String manager) {
            this.manager = manager;
        }
    }


}
