package com.meiliangzi.app.ui.view.Academy.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kk on 2019/5/3.
 */

public class WeekColumnBean {
    private String code;
    private String message;
    private List<Data> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class  Data implements Serializable {
        private String id;
        private String serviceMenuId;
        private String typeName;
        private String founderOrgId;

        private String  typeSort;
        private String type;
        private String createTime;
        private String updateTime;
        private String isDelete;
        private boolean ischos;

        public boolean ischos() {
            return ischos;
        }

        public void setIschos(boolean ischos) {
            this.ischos = ischos;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getServiceMenuId() {
            return serviceMenuId;
        }

        public void setServiceMenuId(String serviceMenuId) {
            this.serviceMenuId = serviceMenuId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getFounderOrgId() {
            return founderOrgId;
        }

        public void setFounderOrgId(String founderOrgId) {
            this.founderOrgId = founderOrgId;
        }

        public String getTypeSort() {
            return typeSort;
        }

        public void setTypeSort(String typeSort) {
            this.typeSort = typeSort;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }
    }

}
