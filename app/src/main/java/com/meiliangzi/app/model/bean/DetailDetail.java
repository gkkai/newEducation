package com.meiliangzi.app.model.bean;

/**
 * Created by Administrator on 2017/8/31.
 */

public class DetailDetail extends BaseBean {

    /**
     * msg : success
     * data : {"answer_tiem":50,"is_study":2,"is_collect":2,"content_type":2,"is_praise":1,"id":37,"name":"我是视频","img":"http://o7tb2rscn.bkt.clouddn.com/attach/eec7b6868b9c11e79aeb00163e004505.jpg?imageView2/1/w/300/h/150/q/75","createTime":"2017-08-28","content":"http://200019945.vod.myqcloud.com/200019945_dc36a11cfcc211e6bdf13da8014deaf6.f20.mp4","praise":0,"is_answer":true}
     */
    private String msg;
    private DataBean data;

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
         * answer_tiem : 50
         * is_study : 2
         * is_collect : 2
         * content_type : 2
         * is_praise : 1
         * id : 37
         * name : 我是视频
         * img : http://o7tb2rscn.bkt.clouddn.com/attach/eec7b6868b9c11e79aeb00163e004505.jpg?imageView2/1/w/300/h/150/q/75
         * createTime : 2017-08-28
         * content : http://200019945.vod.myqcloud.com/200019945_dc36a11cfcc211e6bdf13da8014deaf6.f20.mp4
         * praise : 0
         * is_answer : true
         */

        private int answer_tiem;
        private int is_study;
        private int is_collect;
        private int content_type;
        private int is_praise;
        private int id;
        private String name;
        private String img;
        private String createTime;
        private String content;
        private int praise;
        private boolean is_answer;

        public int getAnswer_tiem() {
            return answer_tiem;
        }

        public void setAnswer_tiem(int answer_tiem) {
            this.answer_tiem = answer_tiem;
        }

        public int getIs_study() {
            return is_study;
        }

        public void setIs_study(int is_study) {
            this.is_study = is_study;
        }

        public int getIs_collect() {
            return is_collect;
        }

        public void setIs_collect(int is_collect) {
            this.is_collect = is_collect;
        }

        public int getContent_type() {
            return content_type;
        }

        public void setContent_type(int content_type) {
            this.content_type = content_type;
        }

        public int getIs_praise() {
            return is_praise;
        }

        public void setIs_praise(int is_praise) {
            this.is_praise = is_praise;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPraise() {
            return praise;
        }

        public void setPraise(int praise) {
            this.praise = praise;
        }

        public boolean isIs_answer() {
            return is_answer;
        }

        public void setIs_answer(boolean is_answer) {
            this.is_answer = is_answer;
        }
    }
}
