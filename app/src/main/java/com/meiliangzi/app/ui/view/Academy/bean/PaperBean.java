package com.meiliangzi.app.ui.view.Academy.bean;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.model.bean.QualityVideoCommentBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kk on 2019/4/30.
 */

public class PaperBean {
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

    public static class Data implements Serializable {
        private String id;
        private String questionsBankId;
        private String type;
        private String title;
        private List<QuestionOption> questionOption;
        private String answerAnalysis;
        private String fraction;
        private String founderId;
        private String founder;
        private String modifierId;
        private String modifier;
        private String createTime;
        private String updateTime;
        private String isDelete;
        private List<UserAnswer> userAnswer;
        private String userAnswerResult;
        private String userAnswerStatus;
        private List<RightAnswer> rightAnswer;
        private String paperQuestionId;

        public String getPaperQuestionId() {
            return paperQuestionId;
        }

        public void setPaperQuestionId(String paperQuestionId) {
            this.paperQuestionId = paperQuestionId;
        }

        private String score;
        private boolean ischos;

        public boolean ischos() {
            return ischos;
        }

        public void setIschos(boolean ischos) {
            this.ischos = ischos;
        }

        public List<UserAnswer> getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(List<UserAnswer> userAnswer) {
            this.userAnswer = userAnswer;
        }

        public List<RightAnswer> getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(List<RightAnswer> rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestionsBankId() {
            return questionsBankId;
        }

        public void setQuestionsBankId(String questionsBankId) {
            this.questionsBankId = questionsBankId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }



        public String getAnswerAnalysis() {
            return answerAnalysis;
        }

        public void setAnswerAnalysis(String answerAnalysis) {
            this.answerAnalysis = answerAnalysis;
        }

        public String getFraction() {
            return fraction;
        }

        public void setFraction(String fraction) {
            this.fraction = fraction;
        }

        public String getFounderId() {
            return founderId;
        }

        public void setFounderId(String founderId) {
            this.founderId = founderId;
        }

        public String getFounder() {
            return founder;
        }

        public void setFounder(String founder) {
            this.founder = founder;
        }

        public String getModifierId() {
            return modifierId;
        }

        public void setModifierId(String modifierId) {
            this.modifierId = modifierId;
        }

        public String getModifier() {
            return modifier;
        }

        public void setModifier(String modifier) {
            this.modifier = modifier;
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


        public String getUserAnswerResult() {
            return userAnswerResult;
        }

        public void setUserAnswerResult(String userAnswerResult) {
            this.userAnswerResult = userAnswerResult;
        }

        public String getUserAnswerStatus() {
            return userAnswerStatus;
        }

        public void setUserAnswerStatus(String userAnswerStatus) {
            this.userAnswerStatus = userAnswerStatus;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }


        public List<QuestionOption> getQuestionOption() {
            return questionOption;
        }

        public void setQuestionOption(List<QuestionOption> questionOption) {
            this.questionOption = questionOption;
        }
        public static class UserAnswer{
            public String key;
            public String value;
            public boolean ischos;
            public String optinon;

            public boolean ischos() {
                return ischos;
            }

            public void setIschos(boolean ischos) {
                this.ischos = ischos;
            }

            public String getOptinon() {
                return optinon;
            }

            public void setOptinon(String optinon) {
                this.optinon = optinon;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
        public class RightAnswer {
            public String key;
            public String value;
            public boolean ischos;
            public String optinon;

            public boolean ischos() {
                return ischos;
            }

            public void setIschos(boolean ischos) {
                this.ischos = ischos;
            }

            public String getOptinon() {
                return optinon;
            }

            public void setOptinon(String optinon) {
                this.optinon = optinon;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
        public class QuestionOption {
            public String key;
            public String value;
            public boolean ischos;
            public String optinon;

            public String getOptinon() {
                return optinon;
            }

            public void setOptinon(String optinon) {
                this.optinon = optinon;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public boolean ischos() {
                return ischos;
            }

            public void setIschos(boolean ischos) {
                this.ischos = ischos;
            }
        }
    }

}



