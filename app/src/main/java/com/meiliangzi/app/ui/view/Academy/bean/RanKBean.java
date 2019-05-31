package com.meiliangzi.app.ui.view.Academy.bean;

/**
 * Created by kk on 2019/5/18.
 */

public class RanKBean {
  private String  code;
    private String  message;
    private Data  data;

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

    public class Data{
        private String  userTotalScore;
        private String  rank;
        private String  dRank;

        public String getUserTotalScore() {
            return userTotalScore;
        }

        public void setUserTotalScore(String userTotalScore) {
            this.userTotalScore = userTotalScore;
        }

        public String getRank() {
            return rank;
        }

        public void setRank(String rank) {
            this.rank = rank;
        }

        public String getdRank() {
            return dRank;
        }

        public void setdRank(String dRank) {
            this.dRank = dRank;
        }
    }

}
