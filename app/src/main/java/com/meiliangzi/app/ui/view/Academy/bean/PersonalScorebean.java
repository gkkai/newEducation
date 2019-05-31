package com.meiliangzi.app.ui.view.Academy.bean;

import java.util.List;

/**
 * Created by kk on 2019/5/8.
 */

public class PersonalScorebean {
    private  String code;
    private  String message;
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

    public class Data{
        private  String ruleId;
        private  String createTime;
        private  String integralName;
        private  String integralScore;
        private  String userAcmyId;

        public String getRuleId() {
            return ruleId;
        }

        public void setRuleId(String ruleId) {
            this.ruleId = ruleId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getIntegralName() {
            return integralName;
        }

        public void setIntegralName(String integralName) {
            this.integralName = integralName;
        }

        public String getIntegralScore() {
            return integralScore;
        }

        public void setIntegralScore(String integralScore) {
            this.integralScore = integralScore;
        }

        public String getUserAcmyId() {
            return userAcmyId;
        }

        public void setUserAcmyId(String userAcmyId) {
            this.userAcmyId = userAcmyId;
        }
    }
}
