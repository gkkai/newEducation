package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by kk on 2018/1/23.
 */

public class VpteSubvoteinfoBean {
    private int status;
    private String msg;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class  DataBean{
        private String title;
        private List<String> image;
        private List<String> videoAddress;
        private int isVote;
        private int userVoteLogNumber;
        private int id;
        private String desc;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

       public List<String> getImage() {
            return image;
        }

        public void setImage(List<String> image) {
            this.image = image;
        }

        public List<String> getVideoAddress() {
            return videoAddress;
        }

        public void setVideoAddress(List<String> videoAddress) {
            this.videoAddress = videoAddress;
        }

        public int getIsVote() {
            return isVote;
        }

        public void setIsVote(int isVote) {
            this.isVote = isVote;
        }

        public int getUserVoteLogNumber() {
            return userVoteLogNumber;
        }

        public void setUserVoteLogNumber(int userVoteLogNumber) {
            this.userVoteLogNumber = userVoteLogNumber;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
