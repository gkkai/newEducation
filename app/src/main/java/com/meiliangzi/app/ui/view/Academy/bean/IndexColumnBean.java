package com.meiliangzi.app.ui.view.Academy.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by kk on 2019/5/3.
 */

public class IndexColumnBean {
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

    public class  Data implements Serializable {
        private String PId;
        private String columnName;
        private List<Children> children;

        private int  columnType;
        private String createTime;
        private String id;

        public String getPId() {
            return PId;
        }

        public void setPId(String PId) {
            this.PId = PId;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public List<Children> getChildren() {
            return children;
        }

        public void setChildren(List<Children> children) {
            this.children = children;
        }

        public int getColumnType() {
            return columnType;
        }

        public void setColumnType(int columnType) {
            this.columnType = columnType;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public class Children{
            private String PId;
            private String columnName;
            private List<Children> children;

            private int  columnType;
            private String createTime;
            private String id;
            private boolean ischos;

            public boolean ischos() {
                return ischos;
            }

            public void setIschos(boolean ischos) {
                this.ischos = ischos;
            }

            public String getPId() {
                return PId;
            }

            public void setPId(String PId) {
                this.PId = PId;
            }

            public String getColumnName() {
                return columnName;
            }

            public void setColumnName(String columnName) {
                this.columnName = columnName;
            }

            public List<Children> getChildren() {
                return children;
            }

            public void setChildren(List<Children> children) {
                this.children = children;
            }

            public int getColumnType() {
                return columnType;
            }

            public void setColumnType(int columnType) {
                this.columnType = columnType;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

    }

}
