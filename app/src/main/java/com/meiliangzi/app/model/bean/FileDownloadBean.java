package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class FileDownloadBean {

    private int status;
private String msg;
    private List<DataBean> data;
    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
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

    public class DataBean{
        private String file_type;
        private String enclosure_name;
        private String enclosure_address;
        private String id;

        public String getFile_type() {
            return file_type;
        }

        public void setFile_type(String file_type) {
            this.file_type = file_type;
        }

        public String getEnclosure_name() {
            return enclosure_name;
        }

        public void setEnclosure_name(String enclosure_name) {
            this.enclosure_name = enclosure_name;
        }

        public String getEnclosure_address() {
            return enclosure_address;
        }

        public void setEnclosure_address(String enclosure_address) {
            this.enclosure_address = enclosure_address;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
