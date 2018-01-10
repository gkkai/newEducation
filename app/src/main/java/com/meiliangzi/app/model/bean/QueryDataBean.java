package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class QueryDataBean {

  private int status;
    private String msg;
    private DetaBean data;

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

    public DetaBean getData() {
        return data;
    }

    public void setData(DetaBean data) {
        this.data = data;
    }

    public class DetaBean {
        private int id;
        private String scroe;
        private String certificate;
        private String start_at;
        private String name;
        private String repair_scroe;
        private String title;
        private String id_card;
        private String certificate_status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getScroe() {
            return scroe;
        }

        public void setScroe(String scroe) {
            this.scroe = scroe;
        }

        public String getCertificate() {
            return certificate;
        }

        public void setCertificate(String certificate) {
            this.certificate = certificate;
        }

        public String getStart_at() {
            return start_at;
        }

        public void setStart_at(String start_at) {
            this.start_at = start_at;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRepair_scroe() {
            return repair_scroe;
        }

        public void setRepair_scroe(String repair_scroe) {
            this.repair_scroe = repair_scroe;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getCertificate_status() {
            return certificate_status;
        }

        public void setCertificate_status(String certificate_status) {
            this.certificate_status = certificate_status;
        }
    }
}
