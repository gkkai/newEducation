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
        private String integralNumber;
        private String integralName;
        private int score;
        private int isRepeat;
        private String conditions;
        private String singleScore;
        private String ruleDescribe;
        private int limits;
        private int dayScore;
        private  int weekScore;
        private  int cycle;
        private  int isAdd;
        private  int userTotalScore;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIntegralNumber() {
            return integralNumber;
        }

        public void setIntegralNumber(String integralNumber) {
            this.integralNumber = integralNumber;
        }

        public String getIntegralName() {
            return integralName;
        }

        public void setIntegralName(String integralName) {
            this.integralName = integralName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getIsRepeat() {
            return isRepeat;
        }

        public void setIsRepeat(int isRepeat) {
            this.isRepeat = isRepeat;
        }

        public String getConditions() {
            return conditions;
        }

        public void setConditions(String conditions) {
            this.conditions = conditions;
        }

        public String getSingleScore() {
            return singleScore;
        }

        public void setSingleScore(String singleScore) {
            this.singleScore = singleScore;
        }

        public String getRuleDescribe() {
            return ruleDescribe;
        }

        public void setRuleDescribe(String ruleDescribe) {
            this.ruleDescribe = ruleDescribe;
        }

        public int getLimits() {
            return limits;
        }

        public void setLimits(int limits) {
            this.limits = limits;
        }

        public int getDayScore() {
            return dayScore;
        }

        public void setDayScore(int dayScore) {
            this.dayScore = dayScore;
        }

        public int getWeekScore() {
            return weekScore;
        }

        public void setWeekScore(int weekScore) {
            this.weekScore = weekScore;
        }

        public int getCycle() {
            return cycle;
        }

        public void setCycle(int cycle) {
            this.cycle = cycle;
        }

        public int getIsAdd() {
            return isAdd;
        }

        public void setIsAdd(int isAdd) {
            this.isAdd = isAdd;
        }

        public int getUserTotalScore() {
            return userTotalScore;
        }

        public void setUserTotalScore(int userTotalScore) {
            this.userTotalScore = userTotalScore;
        }
    }

}
