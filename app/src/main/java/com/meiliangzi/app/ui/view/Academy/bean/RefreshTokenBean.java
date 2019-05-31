package com.meiliangzi.app.ui.view.Academy.bean;

/**
 * Created by zhangyipeng on 16/6/30.
 */
public class RefreshTokenBean {

    /**
     * code : 0
     * success : true
     * msg :
     */

    private int code;
    private String success;
    private Data data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private String token;
        private String dateTimeOut;
        private String refreshToken;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getDateTimeOut() {
            return dateTimeOut;
        }

        public void setDateTimeOut(String dateTimeOut) {
            this.dateTimeOut = dateTimeOut;
        }

        public String getRefreshToken() {
            return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
