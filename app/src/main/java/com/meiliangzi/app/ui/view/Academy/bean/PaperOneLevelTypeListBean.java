package com.meiliangzi.app.ui.view.Academy.bean;

import java.util.List;

/**
 * Created by kk on 2019/7/11.
 */

public class PaperOneLevelTypeListBean {
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

    public class  Data{
        private String typeName;
        private String createTime;
        private String integralRuleId;
        private String paperTypeCount;
        private String id;
        private String paperCount;
        private String pid;
        private boolean ischos;

        public boolean ischos() {
            return ischos;
        }

        public void setIschos(boolean ischos) {
            this.ischos = ischos;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIntegralRuleId() {
            return integralRuleId;
        }

        public void setIntegralRuleId(String integralRuleId) {
            this.integralRuleId = integralRuleId;
        }

        public String getPaperTypeCount() {
            return paperTypeCount;
        }

        public void setPaperTypeCount(String paperTypeCount) {
            this.paperTypeCount = paperTypeCount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPaperCount() {
            return paperCount;
        }

        public void setPaperCount(String paperCount) {
            this.paperCount = paperCount;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }
    }
}
