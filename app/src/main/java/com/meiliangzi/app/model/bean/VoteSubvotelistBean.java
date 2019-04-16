package com.meiliangzi.app.model.bean;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class VoteSubvotelistBean {

    /**
     * status : 0
     * msg : success
     * data : [{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/835deaa486e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":3},{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/7a3e2be686e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":2},{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/71a76e9886e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":1}]
     */

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

    public static class DataBean {
        /**
         * image : http://o7tb2rscn.bkt.clouddn.com/attach/835deaa486e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75
         * id : 3
         */

        private int isVote;
        private int voteFrequency;
        private int voteNumber;
        private String endAt;
        private int isInfo;

        public int getIsVote() {
            return isVote;
        }

        public void setIsVote(int isVote) {
            this.isVote = isVote;
        }

        public int getVoteFrequency() {
            return voteFrequency;
        }

        public void setVoteFrequency(int voteFrequency) {
            this.voteFrequency = voteFrequency;
        }

        public int getVoteNumber() {
            return voteNumber;
        }

        public void setVoteNumber(int voteNumber) {
            this.voteNumber = voteNumber;
        }

        public String getEndAt() {
            return endAt;
        }

        public void setEndAt(String endAt) {
            this.endAt = endAt;
        }

        public int getIsInfo() {
            return isInfo;
        }

        public void setIsInfo(int isInfo) {
            this.isInfo = isInfo;
        }

        public List<VotesubList> getVotesubList() {
            return voteSubList;
        }

        public void setVotesubList(List<VotesubList> votesubList) {
            this.voteSubList = votesubList;
        }

        private List<VotesubList> voteSubList;
        public static class  VotesubList{
            private int isSubVote;
            private int userVoteLogNumber;
            private int id;
            private String image;
            private String title;
            private int type;

            public int getIsVote() {
                return isSubVote;
            }

            public void setIsVote(int isVote) {
                this.isSubVote = isVote;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }


    }
}
