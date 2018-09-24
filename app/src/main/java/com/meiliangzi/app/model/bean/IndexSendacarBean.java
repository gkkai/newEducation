package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class IndexSendacarBean {

    private int status;
    private int JCZ;
    private int YWC;
    private int DFK;
    private String msg;
    private List<IndexSendacarData> data;


    public int getJCZ() {
        return JCZ;
    }

    public void setJCZ(int JCZ) {
        this.JCZ = JCZ;
    }

    public int getYWC() {
        return YWC;
    }

    public void setYWC(int YWC) {
        this.YWC = YWC;
    }

    public int getDFK() {
        return DFK;
    }

    public void setDFK(int DFK) {
        this.DFK = DFK;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<IndexSendacarData> getData() {
        return data;
    }

    public void setData(List<IndexSendacarData> data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static class IndexSendacarData {
        private String end;
        private String departmentName;
        private String plateNumber;
        private String endAt;
        private String start;
        private String startAt;
        private int id;
        private int driverUserId;
        private int proposerUserId;


        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getPlateNumber() {
            return plateNumber;
        }

        public void setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
        }

        public String getEndAt() {
            return endAt;
        }

        public void setEndAt(String endAt) {
            this.endAt = endAt;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getStartAt() {
            return startAt;
        }

        public void setStartAt(String startAt) {
            this.startAt = startAt;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getDriverUserId() {
            return driverUserId;
        }

        public void setDriverUserId(int driverUserId) {
            this.driverUserId = driverUserId;
        }

        public int getProposerUserid() {
            return proposerUserId;
        }

        public void setProposerUserid(int proposerUserid) {
            this.proposerUserId = proposerUserid;
        }
    }
}
