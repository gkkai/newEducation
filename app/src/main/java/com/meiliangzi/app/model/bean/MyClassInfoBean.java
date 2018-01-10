package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class MyClassInfoBean {

    private int status;
private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Databean> getData() {
        return data;
    }

    public void setData(List<Databean> data) {
        this.data = data;
    }

    private List<Databean> data;
    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
    public class  Databean{
        private String start_at;
        private String certificate;
        private String title;
        private String scroe;
        private String termofvalidity;
        private String repair_scroe;
        private int id;

        public String getStart_at() {
            return start_at;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getScroe() {
            return scroe;
        }

        public void setScroe(String scroe) {
            this.scroe = scroe;
        }

        public String getTermofvalidity() {
            return termofvalidity;
        }

        public void setTermofvalidity(String termofvalidity) {
            this.termofvalidity = termofvalidity;
        }

        public String getRepair_scroe() {
            return repair_scroe;
        }

        public void setRepair_scroe(String repair_scroe) {
            this.repair_scroe = repair_scroe;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
