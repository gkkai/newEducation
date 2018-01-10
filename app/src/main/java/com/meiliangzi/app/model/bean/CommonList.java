package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */

public class CommonList extends BaseBean {

    /**
     * msg : success
     * data : [{"comment_infos":[],"content":"A","comment_id":-1,"create_time":"2017-08-24","userinfo":{"user_department":"","user_code":5813,"user_head":"","user_name":"姚银河","id":2},"praise":1,"is_praise":1,"category_id":1,"id":1}]
     */

    private String msg;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * comment_infos : []
         * content : A
         * comment_id : -1
         * create_time : 2017-08-24
         * userinfo : {"user_department":"","user_code":5813,"user_head":"","user_name":"姚银河","id":2}
         * praise : 1
         * is_praise : 1
         * category_id : 1
         * id : 1
         */

        private String content;
        private int comment_id;
        private String create_time;
        private UserinfoBean userinfo;
        private int praise;
        private int is_praise;
        private int category_id;
        private int id;
        private List<?> comment_infos;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getComment_id() {
            return comment_id;
        }

        public void setComment_id(int comment_id) {
            this.comment_id = comment_id;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public int getIs_praise() {
            return is_praise;
        }

        public void setIs_praise(int is_praise) {
            this.is_praise = is_praise;
        }

        public int getCategory_id() {
            return category_id;
        }

        public void setCategory_id(int category_id) {
            this.category_id = category_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<?> getComment_infos() {
            return comment_infos;
        }

        public void setComment_infos(List<?> comment_infos) {
            this.comment_infos = comment_infos;
        }

        public static class UserinfoBean {
            /**
             * user_department :
             * user_code : 5813
             * user_head :
             * user_name : 姚银河
             * id : 2
             */

            private String user_department;
            private int user_code;
            private String avatar ;
            private String nickname;
            private int id;

            public String getUser_department() {
                return user_department;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public void setUser_department(String user_department) {
                this.user_department = user_department;

            }

            public int getUser_code() {
                return user_code;
            }

            public void setUser_code(int user_code) {
                this.user_code = user_code;
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
