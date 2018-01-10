package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/27 0027.
 */

public class IndexNewsListsBean {

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
        /**
         * news_title : 陕西日报：勇当陕西工业增长的“排头兵”
         * news_type : true
         * id : 5
         */
        private String img;
        private String create_time;
        private int browse_number;
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        private String news_title;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getBrowse_number() {
            return browse_number;
        }

        public void setBrowse_number(int browse_number) {
            this.browse_number = browse_number;
        }

        private int id;

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        /*public boolean isNews_type() {
            return news_type;
        }

        public void setNews_type(boolean news_type) {
            this.news_type = news_type;
        }*/

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
