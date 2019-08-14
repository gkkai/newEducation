package com.meiliangzi.app.ui.view.Academy.bean;

import java.util.List;

/**
 * Created by kk on 2019/7/11.
 */

public class FindByPaperIdBean {
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
        private String id;
        private String dictionaryId;
        private String paperTypeId;
        private String title;
        private String mode;
        private String duration;
        private String publicOrprivate;
        private String countDown;
        private String totalNumber;
        private String fraction;
        private String validityTimeStart;
        private String validityTimeEnd;
        private String type;
        private String fixedTimeStart;
        private String createType;
        private String displayedBefore;
        private String publishStatus;
        private String repeatAnswer;
        private String allWorker;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDictionaryId() {
            return dictionaryId;
        }

        public void setDictionaryId(String dictionaryId) {
            this.dictionaryId = dictionaryId;
        }

        public String getPaperTypeId() {
            return paperTypeId;
        }

        public void setPaperTypeId(String paperTypeId) {
            this.paperTypeId = paperTypeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getPublicOrprivate() {
            return publicOrprivate;
        }

        public void setPublicOrprivate(String publicOrprivate) {
            this.publicOrprivate = publicOrprivate;
        }

        public String getCountDown() {
            return countDown;
        }

        public void setCountDown(String countDown) {
            this.countDown = countDown;
        }

        public String getTotalNumber() {
            return totalNumber;
        }

        public void setTotalNumber(String totalNumber) {
            this.totalNumber = totalNumber;
        }

        public String getFraction() {
            return fraction;
        }

        public void setFraction(String fraction) {
            this.fraction = fraction;
        }

        public String getValidityTimeStart() {
            return validityTimeStart;
        }

        public void setValidityTimeStart(String validityTimeStart) {
            this.validityTimeStart = validityTimeStart;
        }

        public String getValidityTimeEnd() {
            return validityTimeEnd;
        }

        public void setValidityTimeEnd(String validityTimeEnd) {
            this.validityTimeEnd = validityTimeEnd;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFixedTimeStart() {
            return fixedTimeStart;
        }

        public void setFixedTimeStart(String fixedTimeStart) {
            this.fixedTimeStart = fixedTimeStart;
        }

        public String getCreateType() {
            return createType;
        }

        public void setCreateType(String createType) {
            this.createType = createType;
        }

        public String getDisplayedBefore() {
            return displayedBefore;
        }

        public void setDisplayedBefore(String displayedBefore) {
            this.displayedBefore = displayedBefore;
        }

        public String getPublishStatus() {
            return publishStatus;
        }

        public void setPublishStatus(String publishStatus) {
            this.publishStatus = publishStatus;
        }

        public String getRepeatAnswer() {
            return repeatAnswer;
        }

        public void setRepeatAnswer(String repeatAnswer) {
            this.repeatAnswer = repeatAnswer;
        }

        public String getAllWorker() {
            return allWorker;
        }

        public void setAllWorker(String allWorker) {
            this.allWorker = allWorker;
        }
    }

}
