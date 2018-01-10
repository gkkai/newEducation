package com.meiliangzi.app.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class Company extends BaseBean {


    /**
     * msg : success
     * data : [{"department_info":[],"id":3,"name":"神煤科技事业部"},{"department_info":[{"id":5,"name":"综合"},{"id":4,"name":"市场营销部"},{"id":3,"name":"人力资源部"},{"id":2,"name":"生产技术部"}],"id":2,"name":"神南产业发展有限公司"}]
     */

    private String msg;
    private List<DataBean> data;

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

    public static class DataBean implements Serializable{
        /**
         * department_info : []
         * id : 3
         * name : 神煤科技事业部
         */

        private String id;
        private String name;
        private List<ParentMent> department_info;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ParentMent> getDepartment_info() {
            return department_info;
        }

        public void setDepartment_info(List<ParentMent> department_info) {
            this.department_info = department_info;
        }
    }
    public class ParentMent implements Serializable{
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
