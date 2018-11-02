package com.meiliangzi.app.model.bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/27.
 */

public class QuestionList extends BaseBean {

    /**
     * msg : success
     * data : [{"score":"20","correctnessAnswer":"A","title":"共产党员的党龄，从____之日算起。","answerD":"以上全错","studyTypeId":1,"answerA":"预备期满转为正式党员","id":1,"answerC":"预备期满","answerB":"支部大会通过"},{"score":"20","correctnessAnswer":"B","title":"属重大问题都要按照集体领导、____的原则，由党的委员会集体讨论，作出决定。(  ) ","answerD":"以上全错","studyTypeId":1,"answerA":"民主讨论、个别协商、会议决定","id":2,"answerC":"民主讨论、充分酝酿、会议决定","answerB":"民主集中、个别酝酿、会议决定"},{"score":"20","correctnessAnswer":"A","title":"《党章》规定,党选拔干部坚持___、任人唯贤，反对任人唯亲。","answerD":"以德为先","studyTypeId":1,"answerA":"五湖四海","id":3,"answerC":"政治性强","answerB":"政绩突出"}]
     */

    private String msg;
    private List<DataBean> data;

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
        /**
         * score : 20
         * correctnessAnswer : A
         * title : 共产党员的党龄，从____之日算起。
         * answerD : 以上全错
         * studyTypeId : 1
         * answerA : 预备期满转为正式党员
         * id : 1
         * answerC : 预备期满
         * answerB : 支部大会通过
         */
        private String select;

        private String score;
        private String correctnessAnswer;
        private String title;
        private String answerD;
        private int studyTypeId;
        private String answerA;
        private int id;
        private String answerC;
        private String answerB;
        private ArrayList<Map<String,String>> randomansers;//list 0 代表view 选择的a 1 b 2 c调用这个方法取得
        private boolean  study_status; //单选还是多选:单选false 多选ture
        public boolean getStudy_status() {
            return study_status;
        }

        public void setStudy_status(boolean study_status) {
            this.study_status = study_status;
        }


        public void setSelect(String select) {
            this.select = select;
        }
        //把原有的答案A,B,C,D 打乱随机
        public List<Map<String,String>> getRandomansers(){
            List<Map> list =new ArrayList();
            Map ma=new java.util.HashMap();
            ma.put("A",this.getAnswerA());

            Map mb=new java.util.HashMap();
            mb.put("B",this.getAnswerB());
            Map mc=new java.util.HashMap();
            mc.put("C",this.getAnswerC());
            Map md=new java.util.HashMap();
            md.put("D",this.getAnswerD());

            list.add(ma);
            list.add(mb);
            list.add(mc);
            list.add(md);

            java.util.Set set=new java.util.HashSet();
            java.util.Random random=new java.util.Random();

            while(set.size()<4){
                set.add(random.nextInt(4));
            }
            List randomAnswers=new ArrayList();

            Iterator it=set.iterator();
            this.randomansers=new ArrayList();
            //TODO 随机打乱
            Collections.shuffle(list);
            while(it.hasNext()){
                Integer index=(Integer)it.next();

                this.randomansers.add(list.get(index));
            }

            return this.randomansers;

        }
        //依据View 界面选择的答案找到这个答案的真实答案,比如界面选择的是随机后的B 而真实的是C,
        public String getRealAnswer(String viewAnswer){
            String realAnswer="X";//默认给一个不存在的编号
            if("A".equals(viewAnswer.toUpperCase())){
                realAnswer=(String)this.randomansers.get(0).keySet().toArray()[0];
            }else if("B".equals(viewAnswer.toUpperCase())){
                realAnswer=(String)this.randomansers.get(1).keySet().toArray()[0];
            }else if("C".equals(viewAnswer.toUpperCase())){
                realAnswer=(String)this.randomansers.get(2).keySet().toArray()[0];
            }else if("D".equals(viewAnswer.toUpperCase())){
                realAnswer=(String)this.randomansers.get(3).keySet().toArray()[0];
            }
            return realAnswer;
        }
        //取出随机后的答案A
        public String getRandomAnswerA(){
            if(randomansers==null){
                this.getRandomansers();
            }
            String anwserTitle=(String) this.randomansers.get(0).values().toArray()[0];
            return anwserTitle;
        }
        //取出随机后的答案B
        public String getRandomAnswerB(){
            if(randomansers==null){
                this.getRandomansers();
            }
            String anwserTitle=(String) this.randomansers.get(1).values().toArray()[0];;
            return anwserTitle;
        }
        //取出随机后的答案C
        public String getRandomAnswerC(){
            if(randomansers==null){
                this.getRandomansers();
            }
            String anwserTitle=(String) this.randomansers.get(2).values().toArray()[0];
            return anwserTitle;
        }
        //取出随机后的答案D
        public String getRandomAnswerD(){
            if(randomansers==null){
                this.getRandomansers();
            }
            String anwserTitle=(String) this.randomansers.get(3).values().toArray()[0];
            return anwserTitle;
        }
        /**
         * 计算当前题的分值
         *
         * selectAnswers: 这个的为用户选择的答案拼接字符串格式"b,c,d"
         */

        public int getCurrentItemScore(String selectAnswers) {

            //如果selectAnswers为空 则当前题为0分
            if (selectAnswers == "") {
                return 0;
            }
            //去掉selectAnswers前后的","
            if (selectAnswers.charAt(0) == ',') {
                selectAnswers = selectAnswers.substring(1);
            }
            //去掉最后的逗号
            if (selectAnswers.charAt(selectAnswers.length() - 1) == ',') {
                selectAnswers = selectAnswers.substring(0, selectAnswers.length() - 1);
            }


            // 单选 选择正确得分,选择不正确不得分
            if (this.study_status == false) {
                if (correctnessAnswer.equals(selectAnswers)) {
                    return Integer.parseInt(this.getScore());
                } else {
                    return 0;
                }

            }
            //以上如果单选不进入则为多选
            String[] correct = this.correctnessAnswer.split(",");
            String[] userSelectAnwse = selectAnswers.split(",");

            //用户选择的答案大于正确答案的数量为０分
            if (userSelectAnwse.length > correct.length) {
                return 0;
            }

            //用户选择的数量和正确答案的数量相同，如果有一个错为０分，如果都正确为当前题的分数
            if (correct.length == userSelectAnwse.length) {
                boolean flag = true;
                for (int i = 0; i < userSelectAnwse.length; i++) {
                    if (this.correctnessAnswer.contains(userSelectAnwse[i]) == false) {//用户选择的答案不包含在正确答案中
                        flag = false; //有一个错误设置标记为假，结束循环，此题为０
                        break;
                    }
                }
                if (flag == true) {
                    return Integer.parseInt(this.score);
                }
                return 0;



            }
            //少选 如果有选错的为0分，如果没有选错的答案则分数=当前题分值/正确答案数量*选择的数量,
            boolean flag = true;
            for (int i = 0; i < userSelectAnwse.length; i++) {
                if (this.correctnessAnswer.contains(userSelectAnwse[i]) == false) {//用户选择的答案不包含在正确答案中
                    flag = false; //有一个错误设置标记为假，结束循环，此题为０
                    break;
                }
            }
            if (flag == true) {
                return Integer.parseInt(this.score)/correct.length*userSelectAnwse.length;
            }
            return 0;

        }

        public String getSelect() {
            return select;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getCorrectnessAnswer() {
            return correctnessAnswer;
        }

        public void setCorrectnessAnswer(String correctnessAnswer) {
            this.correctnessAnswer = correctnessAnswer;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAnswerD() {
            return answerD;
        }

        public void setAnswerD(String answerD) {
            this.answerD = answerD;
        }

        public int getStudyTypeId() {
            return studyTypeId;
        }

        public void setStudyTypeId(int studyTypeId) {
            this.studyTypeId = studyTypeId;
        }

        public String getAnswerA() {
            return answerA;
        }

        public void setAnswerA(String answerA) {
            this.answerA = answerA;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAnswerC() {
            return answerC;
        }

        public void setAnswerC(String answerC) {
            this.answerC = answerC;
        }

        public String getAnswerB() {
            return answerB;
        }

        public void setAnswerB(String answerB) {
            this.answerB = answerB;
        }
    }
}
