package com.meiliangzi.app.receiver;

/**
 * Created by Administrator on 2017/9/6 0006.
 */

public class JpushBean {

    /**
     * ios : {"sound":"default","extras":{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/0aeab826930a11e79aeb00163e004505.png","id":47,"key":"essay","title":"wenzhanwenz"},"badge":1,"alert":"李玉林您有一个新课程未学习。"}
     * alert : 李玉林您有一个新课程未学习。
     */

    private IosBean android;
    private String alert;

    public void setAndroid(IosBean android) {
        this.android = android;
    }

    public IosBean getAndroid() {
        return android;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public static class IosBean {
        /**
         * sound : default
         * extras : {"image":"http://o7tb2rscn.bkt.clouddn.com/attach/0aeab826930a11e79aeb00163e004505.png","id":47,"key":"essay","title":"wenzhanwenz"}
         * badge : 1
         * alert : 李玉林您有一个新课程未学习。
         */

        private String sound;
        private ExtrasBean extras;
        private int badge;
        private String alert;

        public String getSound() {
            return sound;
        }

        public void setSound(String sound) {
            this.sound = sound;
        }

        public ExtrasBean getExtras() {
            return extras;
        }

        public void setExtras(ExtrasBean extras) {
            this.extras = extras;
        }

        public int getBadge() {
            return badge;
        }

        public void setBadge(int badge) {
            this.badge = badge;
        }

        public String getAlert() {
            return alert;
        }

        public void setAlert(String alert) {
            this.alert = alert;
        }

        public static class ExtrasBean {
            /**
             * image : http://o7tb2rscn.bkt.clouddn.com/attach/0aeab826930a11e79aeb00163e004505.png
             * id : 47
             * key : essay
             * title : wenzhanwenz
             */

            private String image;
            private int id;
            private String key;
            private String title;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
