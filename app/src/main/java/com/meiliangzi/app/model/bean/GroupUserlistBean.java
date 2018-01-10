package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class GroupUserlistBean {

    private int status;

    private String msg;
    private DataBean  data;
    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
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
    public static class DataBean{
        private GroupInfo groupInfo;
        private List<UserInfo> userInfo;

        public GroupInfo getGroupInfo() {
            return groupInfo;
        }

        public void setGroupInfo(GroupInfo groupInfo) {
            this.groupInfo = groupInfo;
        }

        public List<UserInfo> getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(List<UserInfo> userInfo) {
            this.userInfo = userInfo;
        }

        public class GroupInfo{
            private String groupName;
            private String image;
            private int userNumber;
            private int id;
            private List<Integer> groupAdminId;
            private String groupUserId;

            public String getGroupName() {
                return groupName;
            }

            public void setGroupName(String groupName) {
                this.groupName = groupName;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getUserNumber() {
                return userNumber;
            }

            public void setUserNumber(int userNumber) {
                this.userNumber = userNumber;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public List<Integer> getGroupAdminId() {
                return groupAdminId;
            }

            public void setGroupAdminId(List<Integer> groupAdminId) {
                this.groupAdminId = groupAdminId;
            }

            public String getGroupUserId() {
                return groupUserId;
            }

            public void setGroupUserId(String groupUserId) {
                this.groupUserId = groupUserId;
            }
        }
        public static class UserInfo{


            private String nickName;
            private String avatar;
            private int id;

            public String getNickName() {
                return nickName;
            }

            public void setNickName(String nickName) {
                this.nickName = nickName;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
