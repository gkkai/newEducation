package com.meiliangzi.app.model.bean;

/**
 * Created by Administrator on 2017/6/12.
 */

public class ImageCodeBean {

    private int status;
    private String msg;

    private ImageData data;
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

    public ImageData getData() {
        return data;
    }

    public void setData(ImageData data) {
        this.data = data;
    }

    public class ImageData{
        private int at;
        private String image;

        public int getAt() {
            return at;
        }

        public void setAt(int at) {
            this.at = at;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
