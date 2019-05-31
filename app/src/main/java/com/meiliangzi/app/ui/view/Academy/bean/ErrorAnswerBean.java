package com.meiliangzi.app.ui.view.Academy.bean;

import java.util.List;

/**
 * Created by kk on 2019/5/13.
 */

public class ErrorAnswerBean {
    private List<AnswerBean> answerBean;

    public List<AnswerBean> getAnswerBean() {
        return answerBean;
    }

    public void setAnswerBean(List<AnswerBean> answerBean) {
        this.answerBean = answerBean;
    }

    public static class  AnswerBean{
        private String id;
        private List<PaperBean.Data.RightAnswer> rightAnswer;
        private List<PaperBean.Data.UserAnswer> userAnswer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<PaperBean.Data.RightAnswer> getRightAnswer() {
            return rightAnswer;
        }

        public void setRightAnswer(List<PaperBean.Data.RightAnswer> rightAnswer) {
            this.rightAnswer = rightAnswer;
        }

        public List<PaperBean.Data.UserAnswer> getUserAnswe() {
            return userAnswer;
        }

        public void setUserAnswe(List<PaperBean.Data.UserAnswer> userAnswe) {
            this.userAnswer = userAnswe;
        }

    }

}
