package com.meiliangzi.app.ui.view.Academy.bean;

import java.util.List;

/**
 * Created by kk on 2019/5/8.
 */

public class RuleListBean {
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

    public static class Data{
        private String id;
        private String updateTime;
        private String modifier;
        private String integralName;
        private String isDelete;
        private String founder;
        private String  cycle;
        private String limits;
        private String isRelease;
        private String score;
        private String ruleDescribe;
        private String conditions;
        private String isRepeat;
        private String createTime;
        private String dayIntegral;
        private String dayScore;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getModifier() {
            return modifier;
        }

        public void setModifier(String modifier) {
            this.modifier = modifier;
        }

        public String getIntegralName() {
            return integralName;
        }

        public void setIntegralName(String integralName) {
            this.integralName = integralName;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public String getFounder() {
            return founder;
        }

        public void setFounder(String founder) {
            this.founder = founder;
        }

        public String getCycle() {
            return cycle;
        }

        public void setCycle(String cycle) {
            this.cycle = cycle;
        }

        public String getLimits() {
            return limits;
        }

        public void setLimits(String limits) {
            this.limits = limits;
        }

        public String getIsRelease() {
            return isRelease;
        }

        public void setIsRelease(String isRelease) {
            this.isRelease = isRelease;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getRuleDescribe() {
            return ruleDescribe;
        }

        public void setRuleDescribe(String ruleDescribe) {
            this.ruleDescribe = ruleDescribe;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public String getIsRepeat() {
            return isRepeat;
        }

        public void setIsRepeat(String isRepeat) {
            this.isRepeat = isRepeat;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDayIntegral() {
            return dayIntegral;
        }

        public void setDayIntegral(String dayIntegral) {
            this.dayIntegral = dayIntegral;
        }

        public String getDayScore() {
            return dayScore;
        }

        public void setDayScore(String dayScore) {
            this.dayScore = dayScore;
        }
    }

}
