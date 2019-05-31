package com.meiliangzi.app.ui.view.Academy.bean;


/**
 * Created by kk on 2019/5/10.
 */

public class ComintQuestionsBackbean {
    private String code;
    private String message;
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        private int score;
       private String pass;

        public String getPass() {
            return pass;
        }

        public void setPass(String pass) {
            this.pass = pass;
        }

        private ExaminationUserPaperMap examinationUserPaperMap;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public ExaminationUserPaperMap getExaminationUserPaperMap() {
            return examinationUserPaperMap;
        }

        public void setExaminationUserPaperMap(ExaminationUserPaperMap examinationUserPaperMap) {
            this.examinationUserPaperMap = examinationUserPaperMap;
        }

        public class  ExaminationUserPaperMap{
            private int totalNumber;
            private String repeatAnswer;
            private String validityTimeEnd;
            private String finishStatus;
            private  String modifier;
            private  String title;
            private String modifierId;
            private String displayedBefore;
            private  String userPaperId;
            private int score;
            private  String publicOrprivate;
            private  String modifierOrgId;
            private  String countDown;
            private String validityTimeStart;
            private  String allWorker;
            private  String userId;
            private  String founderOrgName;
            private  String mode;
            private String dictionaryId;
            private String founder;
            private String isDelete;
            private String fixedTimeStart;
            private String duration;
            private String updateTime;
            private  String id;
            private  String fraction;
            private String publishStatus;
            private String founderId;
            private String modifierOrgName;
            private String createTime;
            private String answerTime;
            private  String createType;
            private  String founderOrgId;
            private String type;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getTotalNumber() {
                return totalNumber;
            }

            public void setTotalNumber(int totalNumber) {
                this.totalNumber = totalNumber;
            }

            public String getRepeatAnswer() {
                return repeatAnswer;
            }

            public void setRepeatAnswer(String repeatAnswer) {
                this.repeatAnswer = repeatAnswer;
            }

            public String getValidityTimeEnd() {
                return validityTimeEnd;
            }

            public void setValidityTimeEnd(String validityTimeEnd) {
                this.validityTimeEnd = validityTimeEnd;
            }

            public String getFinishStatus() {
                return finishStatus;
            }

            public void setFinishStatus(String finishStatus) {
                this.finishStatus = finishStatus;
            }

            public String getModifier() {
                return modifier;
            }

            public void setModifier(String modifier) {
                this.modifier = modifier;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getModifierId() {
                return modifierId;
            }

            public void setModifierId(String modifierId) {
                this.modifierId = modifierId;
            }

            public String getDisplayedBefore() {
                return displayedBefore;
            }

            public void setDisplayedBefore(String displayedBefore) {
                this.displayedBefore = displayedBefore;
            }

            public String getUserPaperId() {
                return userPaperId;
            }

            public void setUserPaperId(String userPaperId) {
                this.userPaperId = userPaperId;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            public String getPublicOrprivate() {
                return publicOrprivate;
            }

            public void setPublicOrprivate(String publicOrprivate) {
                this.publicOrprivate = publicOrprivate;
            }

            public String getModifierOrgId() {
                return modifierOrgId;
            }

            public void setModifierOrgId(String modifierOrgId) {
                this.modifierOrgId = modifierOrgId;
            }

            public String getCountDown() {
                return countDown;
            }

            public void setCountDown(String countDown) {
                this.countDown = countDown;
            }

            public String getValidityTimeStart() {
                return validityTimeStart;
            }

            public void setValidityTimeStart(String validityTimeStart) {
                this.validityTimeStart = validityTimeStart;
            }

            public String getAllWorker() {
                return allWorker;
            }

            public void setAllWorker(String allWorker) {
                this.allWorker = allWorker;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getFounderOrgName() {
                return founderOrgName;
            }

            public void setFounderOrgName(String founderOrgName) {
                this.founderOrgName = founderOrgName;
            }

            public String getMode() {
                return mode;
            }

            public void setMode(String mode) {
                this.mode = mode;
            }

            public String getDictionaryId() {
                return dictionaryId;
            }

            public void setDictionaryId(String dictionaryId) {
                this.dictionaryId = dictionaryId;
            }

            public String getFounder() {
                return founder;
            }

            public void setFounder(String founder) {
                this.founder = founder;
            }

            public String getIsDelete() {
                return isDelete;
            }

            public void setIsDelete(String isDelete) {
                this.isDelete = isDelete;
            }

            public String getFixedTimeStart() {
                return fixedTimeStart;
            }

            public void setFixedTimeStart(String fixedTimeStart) {
                this.fixedTimeStart = fixedTimeStart;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFraction() {
                return fraction;
            }

            public void setFraction(String fraction) {
                this.fraction = fraction;
            }

            public String getPublishStatus() {
                return publishStatus;
            }

            public void setPublishStatus(String publishStatus) {
                this.publishStatus = publishStatus;
            }

            public String getFounderId() {
                return founderId;
            }

            public void setFounderId(String founderId) {
                this.founderId = founderId;
            }

            public String getModifierOrgName() {
                return modifierOrgName;
            }

            public void setModifierOrgName(String modifierOrgName) {
                this.modifierOrgName = modifierOrgName;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getAnswerTime() {
                return answerTime;
            }

            public void setAnswerTime(String answerTime) {
                this.answerTime = answerTime;
            }

            public String getCreateType() {
                return createType;
            }

            public void setCreateType(String createType) {
                this.createType = createType;
            }

            public String getFounderOrgId() {
                return founderOrgId;
            }

            public void setFounderOrgId(String founderOrgId) {
                this.founderOrgId = founderOrgId;
            }
        }
    }
}
