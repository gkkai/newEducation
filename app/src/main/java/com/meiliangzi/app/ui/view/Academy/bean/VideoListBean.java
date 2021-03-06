package com.meiliangzi.app.ui.view.Academy.bean;

import java.util.List;

/**
 * Created by kk on 2019/5/3.
 */

public class VideoListBean {
    private String code;
    private String message;
    private List<Data> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public class Data{
        private String coverImage;
        private String id;
        private String departmentName;
        private String content;
        private int displayMode;
        private int isRelease;
        private String title;
        private String createTime;
        private String updateTime;
        private int isTop;
        private String authorName;
        private String dictName;
        private String columnName;
        private String videoTime;
        private String videoPath;

        public String getVideoTime() {

            return videoTime;
        }
        public String TimeString() {

            return getTimeString(Integer.valueOf(videoTime));
        }
        /**
         * 把时间转换为：时分秒格式。
         *
         * @param second ：秒，传入单位为秒
         * @return
         */
        /**
         * 把时间转换为：时分秒格式。
         *
         * @param time
         * @return
         */
        public  String getTimeString(int time) {
            int miao = time % 60;
            int fen = time / 60;
            int hour = 0;
            if (fen >= 60) {
                hour = fen / 60;
                fen = fen % 60;
            }
            String timeString = "";
            String miaoString = "";
            String fenString = "";
            String hourString = "";
            if (miao < 10) {
                miaoString = "0" + miao;
            } else {
                miaoString = miao + "";
            }
            if (fen < 10) {
                fenString = "0" + fen;
            } else {
                fenString = fen + "";
            }
            if (hour < 10) {
                hourString = "0" + hour;
            } else {
                hourString = hour + "";
            }
            if (hour != 0) {
                timeString = hourString + ":" + fenString + ":" + miaoString;
            } else {
                timeString = fenString + ":" + miaoString;
            }
            return timeString;
        }

        public void setVideoTime(String videoTime) {
            this.videoTime = videoTime;
        }

        public String getVideoPath() {
            return videoPath;
        }

        public void setVideoPath(String videoPath) {
            this.videoPath = videoPath;
        }

        public String getCoverImage() {
            return coverImage;
        }

        public void setCoverImage(String coverImage) {
            this.coverImage = coverImage;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getDisplayMode() {
            return displayMode;
        }

        public void setDisplayMode(int displayMode) {
            this.displayMode = displayMode;
        }

        public int getIsRelease() {
            return isRelease;
        }

        public void setIsRelease(int isRelease) {
            this.isRelease = isRelease;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getIsTop() {
            return isTop;
        }

        public void setIsTop(int isTop) {
            this.isTop = isTop;
        }

        public String getAuthorName() {
            return authorName;
        }

        public void setAuthorName(String authorName) {
            this.authorName = authorName;
        }

        public String getDictName() {
            return dictName;
        }

        public void setDictName(String dictName) {
            this.dictName = dictName;
        }

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }
    }


}
