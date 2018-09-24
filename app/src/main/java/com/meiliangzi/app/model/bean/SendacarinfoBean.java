package com.meiliangzi.app.model.bean;

/**
 * Created by kk on 2018/9/18.
 */

public class SendacarinfoBean {
    private  int status;
    private String msg;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data {
        private String   returnMileage;
        private String   startAt;
        private String    driverPhone;
        private String     departmentName;
        private String  plateNumber;
        private String   endAt;
        private String   proposer;
        private String   start;
        private String           userPhone;
        private String   mileage;
        private String   user;
        private String   remarks;
        private String   end;
        private String   returnTime;
        private  int  id;
        private String   initialMileage;
        private String  driverName;
        private  String sendACarInfoId;
        private  String driverUserId;


        public String getReturnMileage() {
            return returnMileage;
        }

        public void setReturnMileage(String returnMileage) {
            this.returnMileage = returnMileage;
        }

        public String getStartAt() {
            return startAt;
        }

        public void setStartAt(String startAt) {
            this.startAt = startAt;
        }

        public String getDriverPhone() {
            return driverPhone;
        }

        public void setDriverPhone(String driverPhone) {
            this.driverPhone = driverPhone;
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

        public String getProposer() {
            return proposer;
        }

        public void setProposer(String proposer) {
            this.proposer = proposer;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getMileage() {
            return mileage;
        }

        public void setMileage(String mileage) {
            this.mileage = mileage;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getReturnTime() {
            return returnTime;
        }

        public void setReturnTime(String returnTime) {
            this.returnTime = returnTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInitialMileage() {
            return initialMileage;
        }

        public void setInitialMileage(String initialMileage) {
            this.initialMileage = initialMileage;
        }

        public String getDriverName() {
            return driverName;
        }

        public void setDriverName(String driverName) {
            this.driverName = driverName;
        }

        public String getSendACarInfoId() {
            return sendACarInfoId;
        }

        public void setSendACarInfoId(String sendACarInfoId) {
            this.sendACarInfoId = sendACarInfoId;
        }

        public String getDriverUserId() {
            return driverUserId;
        }

        public void setDriverUserId(String driverUserId) {
            this.driverUserId = driverUserId;
        }
    }
}
