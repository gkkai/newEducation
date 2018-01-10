package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27 0027.
 */

public class StudyCenternBean {

    /**
     * status : 0
     * msg : success
     * data : [{"news_title":"陕西日报：勇当陕西工业增长的\u201c排头兵\u201d","news_type":true,"id":5},{"news_title":"黄陵矿业铁运公司单日运量45654吨创新高","news_type":true,"id":4},{"news_title":"陕西煤业化工新型能源有限公司改革纪实","news_type":true,"id":3},{"news_title":"陕煤神南张家峁矿业公司实现安全生产3000天","news_type":true,"id":2},{"news_title":"陕煤集团已落地债转股融资金额达372.5亿元","news_type":true,"id":1}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String image;
        private String video_name;
        private int id;
        private String video_address;
        private String video_teacher;
        private String video_time;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getVideo_name() {
            return video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVideo_address() {
            return video_address;
        }

        public void setVideo_address(String video_address) {
            this.video_address = video_address;
        }

        public String getVideo_teacher() {
            return video_teacher;
        }

        public void setVideo_teacher(String video_teacher) {
            this.video_teacher = video_teacher;
        }

        public String getVideo_time() {
            return video_time;
        }

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }
    }
}
