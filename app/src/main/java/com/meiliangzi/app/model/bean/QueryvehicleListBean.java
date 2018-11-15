package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by kk on 2018/11/15.
 */

public class QueryvehicleListBean {
    private  int status;
     private String       msg;
    private List<QueryvehicleListBeanData> data;

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

    public List<QueryvehicleListBeanData> getData() {
        return data;
    }

    public void setData(List<QueryvehicleListBeanData> data) {
        this.data = data;
    }

    public static class QueryvehicleListBeanData {
        private String  plateNumber;
        private String          name;
        private  int        id;

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
