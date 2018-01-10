package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by kk on 2017/10/12.
 */

public class CountyListbean {
    private int  status;
    private String  msg;
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
        private int  id;
        private String  name;

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

        public List<MapinfoBean> getMapinfo() {
            return mapinfo;
        }

        public void setMapinfo(List<MapinfoBean> mapinfo) {
            this.mapinfo = mapinfo;
        }

        private List<MapinfoBean> mapinfo;
        public static class MapinfoBean {
            private int  id;
            private String  name;
            private String  image;
            private String  longitude;
            private String  latitude;
            private String  address;
            private String  phone;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
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

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
        }

    }
}
