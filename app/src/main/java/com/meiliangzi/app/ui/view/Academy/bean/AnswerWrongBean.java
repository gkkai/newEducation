package com.meiliangzi.app.ui.view.Academy.bean;

/**
 * Created by kk on 2019/5/14.
 */

public class AnswerWrongBean {
    private String code;
    private String message;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public  class Data{
        private  int  currentAnswerWrongNumber;
        private  int  currentAnswerRightNumber;
        private  int  lastWrongNumber;

        public int getCurrentAnswerWrongNumber() {
            return currentAnswerWrongNumber;
        }

        public void setCurrentAnswerWrongNumber(int currentAnswerWrongNumber) {
            this.currentAnswerWrongNumber = currentAnswerWrongNumber;
        }

        public int getCurrentAnswerRightNumber() {
            return currentAnswerRightNumber;
        }

        public void setCurrentAnswerRightNumber(int currentAnswerRightNumber) {
            this.currentAnswerRightNumber = currentAnswerRightNumber;
        }

        public int getLastWrongNumber() {
            return lastWrongNumber;
        }

        public void setLastWrongNumber(int lastWrongNumber) {
            this.lastWrongNumber = lastWrongNumber;
        }
    }


}
