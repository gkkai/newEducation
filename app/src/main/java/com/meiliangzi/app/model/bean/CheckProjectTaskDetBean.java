package com.meiliangzi.app.model.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class CheckProjectTaskDetBean {

    /**
     * status : 0
     * msg : success
     * data : [{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/835deaa486e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":3},{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/7a3e2be686e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":2},{"image":"http://o7tb2rscn.bkt.clouddn.com/attach/71a76e9886e311e7b1db00163e000138.jpg?imageView2/1/w/400/h/200/q/75","id":1}]
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

    public  class DataBean{
        private SubPO subPO;
        private List<TaskCheckLogs> taskCheckLogs;
        private List<ChangePos> changePos;

        public SubPO getProjectPo() {
            return subPO;
        }

        public void setProjectPo(SubPO subPO) {
            this.subPO = subPO;
        }

        public List<TaskCheckLogs> getProjectChecks() {
            return taskCheckLogs;
        }

        public void setProjectChecks(List<TaskCheckLogs> taskCheckLogs) {
            this.taskCheckLogs = taskCheckLogs;
        }

        public List<ChangePos> getChangePos() {
            return changePos;
        }

        public void setChangePos(List<ChangePos> changePos) {
            this.changePos = changePos;
        }



        public class SubPO{
            private int id;
            private String name;
            private int userid;
            private String userName;
            private String start_at;
            private String desc;
            private String end_at;
            private int project_id;
            private int project_status;
            private int project_status_red;
            private String assisting_departmentname;
            private String standard;
            private int schedule;
            private int check_status;
            private int examine_userid;
            private String examines_userName;
            private String measures;
            private int is_change;
            private String create_at;
            private int is_publisher;
            private int is_delete;

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

            public int getUserid() {
                return userid;
            }

            public void setUserid(int userid) {
                this.userid = userid;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getStart_at() {
                return start_at;
            }

            public void setStart_at(String start_at) {
                this.start_at = start_at;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnd_at() {
                return end_at;
            }

            public void setEnd_at(String end_at) {
                this.end_at = end_at;
            }

            public int getProject_id() {
                return project_id;
            }

            public void setProject_id(int project_id) {
                this.project_id = project_id;
            }

            public int getProject_status() {
                return project_status;
            }

            public void setProject_status(int project_status) {
                this.project_status = project_status;
            }

            public int getProject_status_red() {
                return project_status_red;
            }

            public void setProject_status_red(int project_status_red) {
                this.project_status_red = project_status_red;
            }

            public String getAssisting_departmentname() {
                return assisting_departmentname;
            }

            public void setAssisting_departmentname(String assisting_departmentname) {
                this.assisting_departmentname = assisting_departmentname;
            }

            public String getStandard() {
                return standard;
            }

            public void setStandard(String standard) {
                this.standard = standard;
            }

            public int getSchedule() {
                return schedule;
            }

            public void setSchedule(int schedule) {
                this.schedule = schedule;
            }

            public int getCheck_status() {
                return check_status;
            }

            public void setCheck_status(int check_status) {
                this.check_status = check_status;
            }

            public int getExamine_userid() {
                return examine_userid;
            }

            public void setExamine_userid(int examine_userid) {
                this.examine_userid = examine_userid;
            }

            public String getExamines_userName() {
                return examines_userName;
            }

            public void setExamines_userName(String examines_userName) {
                this.examines_userName = examines_userName;
            }

            public String getMeasures() {
                return measures;
            }

            public void setMeasures(String measures) {
                this.measures = measures;
            }

            public int getIs_change() {
                return is_change;
            }

            public void setIs_change(int is_change) {
                this.is_change = is_change;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public int getIs_publisher() {
                return is_publisher;
            }

            public void setIs_publisher(int is_publisher) {
                this.is_publisher = is_publisher;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }
        }
        public class TaskCheckLogs{
            private int id;
            private int project_id;
            private String completion_desc;
            private String problem;
            private String programme;
            private String proposal;
            private String submit_time;
            private String complete_at;
            private int is_adopt;
            private String audit_opinion;
            private int audit_id;
            private String create_at;
            private int is_delete;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProject_id() {
                return project_id;
            }

            public void setProject_id(int project_id) {
                this.project_id = project_id;
            }

            public String getCompletion_desc() {
                return completion_desc;
            }

            public void setCompletion_desc(String completion_desc) {
                this.completion_desc = completion_desc;
            }

            public String getProblem() {
                return problem;
            }

            public void setProblem(String problem) {
                this.problem = problem;
            }

            public String getSolution() {
                return programme;
            }

            public void setSolution(String solution) {
                this.programme = solution;
            }

            public String getProposal() {
                return proposal;
            }

            public void setProposal(String proposal) {
                this.proposal = proposal;
            }

            public String getSubmit_time() {
                return submit_time;
            }

            public void setSubmit_time(String submit_time) {
                this.submit_time = submit_time;
            }

            public String getComplete_at() {
                return complete_at;
            }

            public void setComplete_at(String complete_at) {
                this.complete_at = complete_at;
            }

            public int getIs_adopt() {
                return is_adopt;
            }

            public void setIs_adopt(int is_adopt) {
                this.is_adopt = is_adopt;
            }

            public String getAudit_opinion() {
                return audit_opinion;
            }

            public void setAudit_opinion(String audit_opinion) {
                this.audit_opinion = audit_opinion;
            }

            public int getAudit_id() {
                return audit_id;
            }

            public void setAudit_id(int audit_id) {
                this.audit_id = audit_id;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }
        }
        public class ChangePos{
            private int id;
            private  int project_id;
            private  String subproject_id;
            private int type;
            private String application_reasons;
            private  String originalstart_at;
            private String originalend_at;
            private String changestart_at;
            private String  changeend_at;
            private int is_adopt;
            private String create_at;
            private String audit_time;
            private String audit_opinion;
            private int changeType;
            private int is_delete;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getProject_id() {
                return project_id;
            }

            public void setProject_id(int project_id) {
                this.project_id = project_id;
            }

            public String getSubproject_id() {
                return subproject_id;
            }

            public void setSubproject_id(String subproject_id) {
                this.subproject_id = subproject_id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getApplication_reasons() {
                return application_reasons;
            }

            public void setApplication_reasons(String application_reasons) {
                this.application_reasons = application_reasons;
            }

            public String getOriginalstart_at() {
                return originalstart_at;
            }

            public void setOriginalstart_at(String originalstart_at) {
                this.originalstart_at = originalstart_at;
            }

            public String getOriginalend_at() {
                return originalend_at;
            }

            public void setOriginalend_at(String originalend_at) {
                this.originalend_at = originalend_at;
            }

            public String getChangestart_at() {
                return changestart_at;
            }

            public void setChangestart_at(String changestart_at) {
                this.changestart_at = changestart_at;
            }

            public String getChangeend_at() {
                return changeend_at;
            }

            public void setChangeend_at(String changeend_at) {
                this.changeend_at = changeend_at;
            }

            public int getIs_adopt() {
                return is_adopt;
            }

            public void setIs_adopt(int is_adopt) {
                this.is_adopt = is_adopt;
            }

            public String getCreate_at() {
                return create_at;
            }

            public void setCreate_at(String create_at) {
                this.create_at = create_at;
            }

            public String getAudit_time() {
                return audit_time;
            }

            public void setAudit_time(String audit_time) {
                this.audit_time = audit_time;
            }

            public String getAudit_opinion() {
                return audit_opinion;
            }

            public void setAudit_opinion(String audit_opinion) {
                this.audit_opinion = audit_opinion;
            }

            public int getChangeType() {
                return changeType;
            }

            public void setChangeType(int changeType) {
                this.changeType = changeType;
            }

            public int getIs_delete() {
                return is_delete;
            }

            public void setIs_delete(int is_delete) {
                this.is_delete = is_delete;
            }
        }
    }



}
