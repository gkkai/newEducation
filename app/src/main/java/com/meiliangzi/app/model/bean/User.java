package com.meiliangzi.app.model.bean;

/**
 * Created by Administrator on 2017/8/22.
 */

public class User extends BaseBean {


    /**
     * msg : success
     * data : {"userName":"heh","userAddress":"","logDays":1,"userDepartment":"","workTime":"","image":"","yesCourses":0,"userId":7,"user_code":0,"studyTime":0,"tokens":"Qb7FrI412N5hSV2oiMOli59cCYYV8g6qtrNSoQem5lzcGWR9V1YTT5CPLrHBBf6QydRN9rYE+E4P2F+lKqCwHg==","phone":"13709201950","token":"15e576e8c04c0f31947f35cfbf035d98","avgachievement":0,"userCompany":{"company_name":"","id":0},"managementLevel":"","isPartymember":false,"department":{"id":0,"department_name":""},"completionRate":0,"noCourses":0,"userProfile":""}
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
         * userName : heh
         * userAddress :
         * logDays : 1
         * userDepartment :
         * workTime :
         * image :
         * yesCourses : 0
         * userId : 7
         * user_code : 0
         * studyTime : 0
         * tokens : Qb7FrI412N5hSV2oiMOli59cCYYV8g6qtrNSoQem5lzcGWR9V1YTT5CPLrHBBf6QydRN9rYE+E4P2F+lKqCwHg==
         * phone : 13709201950
         * token : 15e576e8c04c0f31947f35cfbf035d98
         * avgachievement : 0
         * userCompany : {"company_name":"","id":0}
         * managementLevel :
         * isPartymember : false
         * department : {"id":0,"department_name":""}
         * completionRate : 0
         * noCourses : 0
         * userProfile :
         */

        private String userName;
        private String userAddress;
        private int logDays;
        private String userDepartment;
        private String workTime;
        private String image;
        private int yesCourses;
        private int userId;
        private String user_code;
        private int studyTime;
        private String tokens;
        private String phone;
        private String token;
        private int avgachievement;
        private UserCompanyBean userCompany;
        private String managementLevel;
        private boolean isPartymember;
        private DepartmentBean department;
        private int completionRate;
        private int noCourses;
        private String userProfile;
        private Partybranch partybranch;
        private int isAuthorization;

        public int getIsAuthorization() {
            return isAuthorization;
        }

        public void setIsAuthorization(int isAuthorization) {
            this.isAuthorization = isAuthorization;
        }

        public Partybranch getPartybranch() {
            return partybranch;
        }

        public void setPartybranch(Partybranch partybranch) {
            this.partybranch = partybranch;
        }

        public static class Partybranch{
            public String getPartybranch_name() {
                return partybranch_name;
            }

            public void setPartybranch_name(String partybranch_name) {
                this.partybranch_name = partybranch_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            private String partybranch_name;
            private String id;
        }
        public boolean isPartymember() {
            return isPartymember;
        }

        public void setPartymember(boolean partymember) {
            isPartymember = partymember;
        }



        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserAddress() {
            return userAddress;
        }

        public void setUserAddress(String userAddress) {
            this.userAddress = userAddress;
        }

        public int getLogDays() {
            return logDays;
        }

        public void setLogDays(int logDays) {
            this.logDays = logDays;
        }

        public String getUserDepartment() {
            return userDepartment;
        }

        public void setUserDepartment(String userDepartment) {
            this.userDepartment = userDepartment;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getYesCourses() {
            return yesCourses;
        }

        public void setYesCourses(int yesCourses) {
            this.yesCourses = yesCourses;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public int getStudyTime() {
            return studyTime;
        }

        public void setStudyTime(int studyTime) {
            this.studyTime = studyTime;
        }

        public String getTokens() {
            return tokens;
        }

        public void setTokens(String tokens) {
            this.tokens = tokens;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getAvgachievement() {
            return avgachievement;
        }

        public void setAvgachievement(int avgachievement) {
            this.avgachievement = avgachievement;
        }

        public UserCompanyBean getUserCompany() {
            return userCompany;
        }

        public void setUserCompany(UserCompanyBean userCompany) {
            this.userCompany = userCompany;
        }

        public String getManagementLevel() {
            return managementLevel;
        }

        public void setManagementLevel(String managementLevel) {
            this.managementLevel = managementLevel;
        }

        public boolean isIsPartymember() {
            return isPartymember;
        }

        public void setIsPartymember(boolean isPartymember) {
            this.isPartymember = isPartymember;
        }

        public DepartmentBean getDepartment() {
            return department;
        }

        public void setDepartment(DepartmentBean department) {
            this.department = department;
        }

        public int getCompletionRate() {
            return completionRate;
        }

        public void setCompletionRate(int completionRate) {
            this.completionRate = completionRate;
        }

        public int getNoCourses() {
            return noCourses;
        }

        public void setNoCourses(int noCourses) {
            this.noCourses = noCourses;
        }

        public String getUserProfile() {
            return userProfile;
        }

        public void setUserProfile(String userProfile) {
            this.userProfile = userProfile;
        }

        public static class UserCompanyBean {
            /**
             * company_name :
             * id : 0
             */

            private String company_name;
            private String id;

            public String getCompany_name() {
                return company_name;
            }

            public void setCompany_name(String company_name) {
                this.company_name = company_name;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }
        }

        public static class DepartmentBean {
            /**
             * id : 0
             * department_name :
             */

            private String id;
            private String department_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDepartment_name() {
                return department_name;
            }

            public void setDepartment_name(String department_name) {
                this.department_name = department_name;
            }
        }
    }
}
