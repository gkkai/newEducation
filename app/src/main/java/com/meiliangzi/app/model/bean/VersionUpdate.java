package com.meiliangzi.app.model.bean;

/**
 * Created by Administrator on 2017/9/10.
 */

public class VersionUpdate {


    /**
     * status : 0
     * msg : success
     * data : {"versionnumber":"","android_versiondesc":"更新内容：\r\n    1.时代峻峰看电视机锋可视对讲菲利克斯。\r\n    2.豆腐块司法考试老地方乐山大佛。\r\n    3.斯蒂芬的所发生的发说大法师打发斯蒂芬第三方是对方的说法的大方的说法水电费水电费是。\r\n    4.是对方的说法的是防守打法索拉卡公交卡\t\t\t\t\t\t\t\t\t\t ","android_versionnumber":"101","android_versionname":"V1.1","android_address":"http://o7tb2rscn.bkt.clouddn.com/attach/376b0aaa962e11e79aeb00163e004505.apk"}
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
         * versionnumber :
         * android_versiondesc : 更新内容：
         1.时代峻峰看电视机锋可视对讲菲利克斯。
         2.豆腐块司法考试老地方乐山大佛。
         3.斯蒂芬的所发生的发说大法师打发斯蒂芬第三方是对方的说法的大方的说法水电费水电费是。
         4.是对方的说法的是防守打法索拉卡公交卡
         * android_versionnumber : 101
         * android_versionname : V1.1
         * android_address : http://o7tb2rscn.bkt.clouddn.com/attach/376b0aaa962e11e79aeb00163e004505.apk
         */

        private String versionnumber;
        private String android_versiondesc;
        private String android_versionnumber;
        private String android_versionname;
        private String android_address;

        public String getVersionnumber() {
            return versionnumber;
        }

        public void setVersionnumber(String versionnumber) {
            this.versionnumber = versionnumber;
        }

        public String getAndroid_versiondesc() {
            return android_versiondesc;
        }

        public void setAndroid_versiondesc(String android_versiondesc) {
            this.android_versiondesc = android_versiondesc;
        }

        public String getAndroid_versionnumber() {
            return android_versionnumber;
        }

        public void setAndroid_versionnumber(String android_versionnumber) {
            this.android_versionnumber = android_versionnumber;
        }

        public String getAndroid_versionname() {
            return android_versionname;
        }

        public void setAndroid_versionname(String android_versionname) {
            this.android_versionname = android_versionname;
        }

        public String getAndroid_address() {
            return android_address;
        }

        public void setAndroid_address(String android_address) {
            this.android_address = android_address;
        }
    }
}
