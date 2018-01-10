package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by kk on 2017/9/21.
 */

public class PartyBranchBean {
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


    public static class DataBean {


        private List<Partybranchs_info> partybranchs_info;
        private int id;

        public List<Partybranchs_info> getPartybranchs_info() {
            return partybranchs_info;
        }

        public void setPartybranchs_info(List<Partybranchs_info> partybranchs_info) {
            this.partybranchs_info = partybranchs_info;
        }

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

        private String name;


        public static class Partybranchs_info {
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

            private int id;
            private String name;
        }

    }
    }
