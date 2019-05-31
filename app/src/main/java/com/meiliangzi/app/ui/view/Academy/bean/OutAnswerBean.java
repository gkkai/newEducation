package com.meiliangzi.app.ui.view.Academy.bean;

import java.util.List;

/**
 * Created by kk on 2019/5/2.
 */

public class OutAnswerBean{
    private String paperId;//试题ID
    private String finishStatus;
    private String answerTime;
    private String repeatAnswer;

    public String getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(String answerTime) {
        this.answerTime = answerTime;
    }

    public String getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(String finishStatus) {
        this.finishStatus = finishStatus;
    }

    public String getRepeatAnswer() {
        return repeatAnswer;
    }

    public void setRepeatAnswer(String repeatAnswer) {
        this.repeatAnswer = repeatAnswer;
    }

    private List<AnswerBean> AnswerBean;





    public String getPaperId() {
        return paperId;
    }

    public List<OutAnswerBean.AnswerBean> getAnswerBean() {
        return AnswerBean;
    }

    public void setAnswerBean(List<OutAnswerBean.AnswerBean> answerBean) {
        AnswerBean = answerBean;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public static class AnswerBean {



        private List<QuestionOption> questionOption;//选项集合
        private List<PaperBean.Data.RightAnswer> rightAnswer;

        private String QuestionOptionid;
        private String fraction;
        private String type;

        public List<PaperBean.Data.RightAnswer> getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(List<PaperBean.Data.RightAnswer> rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        public String getFraction() {
            return fraction;
        }

        public void setFraction(String fraction) {
            this.fraction = fraction;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getQuestionOptionid() {
            return QuestionOptionid;
        }

        public void setQuestionOptionid(String questionOptionid) {
            QuestionOptionid = questionOptionid;
        }

        public List<QuestionOption> getQuestionOption() {
            return questionOption;
        }

        public void setQuestionOption(List<QuestionOption> questionOption) {
            this.questionOption = questionOption;
        }


        public static class QuestionOption{
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

    }
}

