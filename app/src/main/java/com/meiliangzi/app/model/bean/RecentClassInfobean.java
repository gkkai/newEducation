package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by kk on 17/10/26.
 */

public class RecentClassInfobean {
    private int status;
    private String msg;
    private Databean data;

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

    public Databean getData() {
        return data;
    }

    public void setData(Databean data) {
        this.data = data;
    }

    public class Databean {
        private int study_type;
        private ClassInfo info;
        private List<TeacherGroup> teacher_group;
        private LinkmanData linkman_data;

        public int getStudy_type() {
            return study_type;
        }

        public void setStudy_type(int study_type) {
            this.study_type = study_type;
        }

        public ClassInfo getInfo() {
            return info;
        }

        public void setInfo(ClassInfo info) {
            this.info = info;
        }

        public List<TeacherGroup> getTeacher_group() {
            return teacher_group;
        }

        public void setTeacher_group(List<TeacherGroup> teacher_group) {
            this.teacher_group = teacher_group;
        }

        public LinkmanData getLinkman_data() {
            return linkman_data;
        }

        public void setLinkman_data(LinkmanData linkman_data) {
            this.linkman_data = linkman_data;
        }

        public class ClassInfo {
            private String image;
            private String start_at;
            private int id;
            private String certificate;
            private String title;
            private String content;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getStart_at() {
                return start_at;
            }

            public void setStart_at(String start_at) {
                this.start_at = start_at;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getCertificate() {
                return certificate;
            }

            public void setCertificate(String certificate) {
                this.certificate = certificate;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public class TeacherGroup {
            private String positions;
            private String name;
            private String avatar;
            private String desc;

            public String getPositions() {
                return positions;
            }

            public void setPositions(String positions) {
                this.positions = positions;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }
        }
        public class LinkmanData {
            private String qq;
            private String name;
            private String phone;

            public String getQq() {
                return qq;
            }

            public void setQq(String qq) {
                this.qq = qq;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }


}
