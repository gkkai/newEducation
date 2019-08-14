package com.meiliangzi.app.db.bean;

/**
 * Created by kk on 2019/6/12.
 */

public class MapInfoBean {
    private  int status;

    private  String msg;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data  {
        private   int  classification_id;
        private String  countyName;
        private String image;
        private String  describe;
        private String phone;
        private int county_id;
        private int  id;
        private String name;
        private String cityName;
        private String longitude;
        private String latitude;
        private String classIfIcationName;
        private String city_id;

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public int getClassification_id() {
            return classification_id;
        }

        public void setClassification_id(int classification_id) {
            this.classification_id = classification_id;
        }

        public String getCountyName() {
            return countyName;
        }

        public void setCountyName(String countyName) {
            this.countyName = countyName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescribe() {
            return describe;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getCounty_id() {
            return county_id;
        }

        public void setCounty_id(int county_id) {
            this.county_id = county_id;
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

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
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

        public String getClassIfIcationName() {
            return classIfIcationName;
        }

        public void setClassIfIcationName(String classIfIcationName) {
            this.classIfIcationName = classIfIcationName;
        }
    }
}
