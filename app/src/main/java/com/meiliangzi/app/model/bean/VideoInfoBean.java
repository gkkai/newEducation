package com.meiliangzi.app.model.bean;

import com.meiliangzi.app.ui.adapter.ListCityAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/6/12.
 */

public class VideoInfoBean {

    private int status;
private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String video_address;
        private String video_time;
        private String image;
        private String video_teacher;
        private String video_name;
        private String video_use_person;
        private int id;
        private int learning_num;
        private List<ChapterDataInfo> video_chapter;

        public String getVideo_address() {
            return video_address;
        }

        public void setVideo_address(String video_address) {
            this.video_address = video_address;
        }

        public String getVideo_time() {
            return video_time;
        }

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getVideo_teacher() {
            return video_teacher;
        }

        public void setVideo_teacher(String video_teacher) {
            this.video_teacher = video_teacher;
        }

        public String getVideo_name() {
            return video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public String getVideo_use_person() {
            return video_use_person;
        }

        public void setVideo_use_person(String video_use_person) {
            this.video_use_person = video_use_person;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLearning_num() {
            return learning_num;
        }

        public void setLearning_num(int learning_num) {
            this.learning_num = learning_num;
        }

        public List<ChapterDataInfo> getVideo_chapter() {
            return video_chapter;
        }

        public void setVideo_chapter(List<ChapterDataInfo> video_chapter) {
            this.video_chapter = video_chapter;
        }

        public class ChapterDataInfo {
            private String chapter_pic;
            private String chapter_name;
            private int id;
            private String chapter_address;

            public String getChapter_pic() {
                return chapter_pic;
            }

            public void setChapter_pic(String chapter_pic) {
                this.chapter_pic = chapter_pic;
            }

            public String getChapter_name() {
                return chapter_name;
            }

            public void setChapter_name(String chapter_name) {
                this.chapter_name = chapter_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getChapter_address() {
                return chapter_address;
            }

            public void setChapter_address(String chapter_address) {
                this.chapter_address = chapter_address;
            }
        }
    }
}
