package com.meiliangzi.app.ui.view.Academy.bean;

/**
 * Created by kk on 2019/5/16.
 */

public class NewBaseBean {
    private String code;
    private String message;
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

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

   public class Data{
       private TokenVo tokenVo;
       private String personalOrgId;
       private int oldUserId;

       public String getPersonalOrgId() {
           return personalOrgId;
       }

       public void setPersonalOrgId(String personalOrgId) {
           this.personalOrgId = personalOrgId;
       }

       public int getOldUserId() {
           return oldUserId;
       }

       public void setOldUserId(int oldUserId) {
           this.oldUserId = oldUserId;
       }

       public String getOrgId() {
           return personalOrgId;
       }

       public void setOrgId(String orgId) {
           this.personalOrgId = orgId;
       }

       public TokenVo getTokenVo() {
           return tokenVo;
       }

       public void setTokenVo(TokenVo tokenVo) {
           this.tokenVo = tokenVo;
       }

       public class TokenVo{
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
       private String userId;

       public String getUserId() {
           return userId;
       }

       public void setUserId(String userId) {
           this.userId = userId;
       }
   }
}
