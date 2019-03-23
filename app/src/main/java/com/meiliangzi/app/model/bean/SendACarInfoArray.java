package com.meiliangzi.app.model.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class SendACarInfoArray {

    private String user;
    private String userPhone;
    private String start;
    private String end;
    private String startAt;
    private String endAt;
    private String driverName;
    private String driverPhone;
    private String plateNumber;

    public ArrayList<QueryuserData> getQueryuserDatalist() {
        return queryuserDatalist;
    }

    public void setQueryuserDatalist(ArrayList<QueryuserData> queryuserDatalist) {
        this.queryuserDatalist = queryuserDatalist;
    }

    private String driverUserid;

    private ArrayList<QueryuserData> queryuserDatalist=new ArrayList<QueryuserData>();
    public String getUser() {
        return user;
    }
    public static class QueryuserData {
        private String driverPhone;
        private String plateNumber;
        private String driverName;
        private int id;
        private boolean Select;

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isSelect() {
            return Select;
        }

        public void setSelect(boolean select) {
            Select = select;
        }
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStartAt() {
        return startAt;
    }

    public void setStartAt(String startAt) {
        this.startAt = startAt;
    }

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
        this.endAt = endAt;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverPhone() {
        return driverPhone;
    }

    public void setDriverPhone(String driverPhone) {
        this.driverPhone = driverPhone;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getDriverUserid() {
        return driverUserid;
    }

    public void setDriverUserid(String driverUserid) {
        this.driverUserid = driverUserid;
    }
}
