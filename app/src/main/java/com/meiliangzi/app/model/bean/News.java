package com.meiliangzi.app.model.bean;

/**
 * Created by Administrator on 2017/8/29.
 */

public class News extends BaseBean {

    /**
     * msg : success
     * data : {"news_title":"黄陵矿业铁运公司单日运量45654吨创新高","content":"\t\t\t\t\t\t\t\t\t\t\t  连日来，各地持续高温，电煤需求量大幅攀升，黄陵矿业铁运公司加强调度统一指挥、联系与协调力度，加大装车组织管理，实现快速装车。7月份实现运量99.5万吨，同比增长57%，8月7日，完成运量45654吨，创今年单日运输最好成绩。\r\n\r\n该公司加强与西安铁路局的联系，组织召开运输协调会，及时准确掌握煤炭库存、车流情况，超前安排，提高装车计划兑现率；合理调整内部业务组合结构，提高接发列车、调车作业、站内车辆检查等运输环节效率，减少列车站内停留时间，使接车、配送、取车、编组、发车紧密有序衔接；合理安排装车，实现满载成组、成列装车，提高日班计划、日班配车、日班装卸车兑现率，不断压缩货车占用时间，1-7月周转时间比去年同期下降0.5小时；同时根据计划运量确定机车台数，严控作业时间，降低油耗。做好机车检修工艺，提高机车质量，减少机车原因停车、机故和临修。对机车各项指标和参数进行全面比对分析，1-7月机车利用率同比提高0.2%。\r\n\r\n在做好运输协调的同时，严格落实干部走动式、管理人员包保和安全质量标准化考核等制度，强化对现场安全工作的检查和监督，针对季节特点，开展雨季\u201c三防\u201d专项整治活动，制定调度应急处置预案，及时帮助基层解决现场问题，实现作业现场的有效控制，确保运输生产形势安全平稳，为完成全年任务奠定了良好基础。（来源：中国煤炭网）","img":"http://o7tb2rscn.bkt.clouddn.com/attach/ab9721b086e411e7b1db00163e000138.jpg?imageView2/1/w/300/h/150/q/75","createuser":{"user_department":"","user_code":181017,"user_head":"http://wx.qlogo.cn/mmopen/YVicEdwDsrEnECdDVSvUd0lOCBYtNn5e7jaEj4pOynUVplsETic544UMgsxa6oqDzAFbXTPHv92Dm5EiaKiczGruotBEh3Ff4ITa/0","user_name":"张萌","id":1},"video_adress":"","browse_number":0,"id":4,"createTime":"2017-08-22"}
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
         * news_title : 黄陵矿业铁运公司单日运量45654吨创新高
         * content : 										 连日来，各地持续高温，电煤需求量大幅攀升，黄陵矿业铁运公司加强调度统一指挥、联系与协调力度，加大装车组织管理，实现快速装车。7月份实现运量99.5万吨，同比增长57%，8月7日，完成运量45654吨，创今年单日运输最好成绩。

         该公司加强与西安铁路局的联系，组织召开运输协调会，及时准确掌握煤炭库存、车流情况，超前安排，提高装车计划兑现率；合理调整内部业务组合结构，提高接发列车、调车作业、站内车辆检查等运输环节效率，减少列车站内停留时间，使接车、配送、取车、编组、发车紧密有序衔接；合理安排装车，实现满载成组、成列装车，提高日班计划、日班配车、日班装卸车兑现率，不断压缩货车占用时间，1-7月周转时间比去年同期下降0.5小时；同时根据计划运量确定机车台数，严控作业时间，降低油耗。做好机车检修工艺，提高机车质量，减少机车原因停车、机故和临修。对机车各项指标和参数进行全面比对分析，1-7月机车利用率同比提高0.2%。

         在做好运输协调的同时，严格落实干部走动式、管理人员包保和安全质量标准化考核等制度，强化对现场安全工作的检查和监督，针对季节特点，开展雨季“三防”专项整治活动，制定调度应急处置预案，及时帮助基层解决现场问题，实现作业现场的有效控制，确保运输生产形势安全平稳，为完成全年任务奠定了良好基础。（来源：中国煤炭网）
         * img : http://o7tb2rscn.bkt.clouddn.com/attach/ab9721b086e411e7b1db00163e000138.jpg?imageView2/1/w/300/h/150/q/75
         * createuser : {"user_department":"","user_code":181017,"user_head":"http://wx.qlogo.cn/mmopen/YVicEdwDsrEnECdDVSvUd0lOCBYtNn5e7jaEj4pOynUVplsETic544UMgsxa6oqDzAFbXTPHv92Dm5EiaKiczGruotBEh3Ff4ITa/0","user_name":"张萌","id":1}
         * video_adress :
         * browse_number : 0
         * id : 4
         * createTime : 2017-08-22
         */

        private String news_title;
        private String content;
        private String img;
        private CreateuserBean createuser;
        private String video_adress;
        private int browse_number;
        private int id;
        private String createTime;

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public CreateuserBean getCreateuser() {
            return createuser;
        }

        public void setCreateuser(CreateuserBean createuser) {
            this.createuser = createuser;
        }

        public String getVideo_adress() {
            return video_adress;
        }

        public void setVideo_adress(String video_adress) {
            this.video_adress = video_adress;
        }

        public int getBrowse_number() {
            return browse_number;
        }

        public void setBrowse_number(int browse_number) {
            this.browse_number = browse_number;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public static class CreateuserBean {
            /**
             * user_department :
             * user_code : 181017
             * user_head : http://wx.qlogo.cn/mmopen/YVicEdwDsrEnECdDVSvUd0lOCBYtNn5e7jaEj4pOynUVplsETic544UMgsxa6oqDzAFbXTPHv92Dm5EiaKiczGruotBEh3Ff4ITa/0
             * user_name : 张萌
             * id : 1
             */

            private String user_department;
            private int user_code;
            private String user_head;
            private String user_name;
            private int id;

            public String getUser_department() {
                return user_department;
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

            public String getUser_head() {
                return user_head;
            }

            public void setUser_head(String user_head) {
                this.user_head = user_head;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
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
