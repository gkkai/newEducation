package com.meiliangzi.app.model.api;


import com.meiliangzi.app.db.bean.FreebackBean;
import com.meiliangzi.app.db.bean.MapInfoBean;
import com.meiliangzi.app.model.annotation.HttpRequest;
import com.meiliangzi.app.model.bean.AddFrouppicBean;
import com.meiliangzi.app.model.bean.AddMapBean;
import com.meiliangzi.app.model.bean.AgreeFriendapplyBean;
import com.meiliangzi.app.model.bean.ArticalDetail;
import com.meiliangzi.app.model.bean.ArticalList;
import com.meiliangzi.app.model.bean.BannerBean;
import com.meiliangzi.app.model.bean.BaseBean;
import com.meiliangzi.app.model.bean.BindPhoneBean;
import com.meiliangzi.app.model.bean.CheckChildenProjectBean;
import com.meiliangzi.app.model.bean.CheckDepartmentsBean;
import com.meiliangzi.app.model.bean.CheckProjectBean;
import com.meiliangzi.app.model.bean.CheckProjectDetBean;
import com.meiliangzi.app.model.bean.CheckProjectTaskDetBean;
import com.meiliangzi.app.model.bean.CityListBean;
import com.meiliangzi.app.model.bean.CommonList;
import com.meiliangzi.app.model.bean.CommonsListBean;
import com.meiliangzi.app.model.bean.Company;
import com.meiliangzi.app.model.bean.ConfirmationBean;
import com.meiliangzi.app.model.bean.CountyListbean;
import com.meiliangzi.app.model.bean.CreatGroupChatBean;
import com.meiliangzi.app.model.bean.DepartmentuserNumberBean;
import com.meiliangzi.app.model.bean.DetailDetail;
import com.meiliangzi.app.model.bean.ExitGroupBean;
import com.meiliangzi.app.model.bean.FileDownloadBean;
import com.meiliangzi.app.model.bean.FridentListBean;
import com.meiliangzi.app.model.bean.GroupListBean;
import com.meiliangzi.app.model.bean.GroupUserlistBean;
import com.meiliangzi.app.model.bean.GroupinfoBean;
import com.meiliangzi.app.model.bean.HomePageBean;
import com.meiliangzi.app.model.bean.ImageCodeBean;
import com.meiliangzi.app.model.bean.IndexNewsBean;
import com.meiliangzi.app.model.bean.IndexNewsListsBean;
import com.meiliangzi.app.model.bean.IndexNewsTypeBean;
import com.meiliangzi.app.model.bean.IndexSendacarBean;
import com.meiliangzi.app.model.bean.IndexpicBean;
import com.meiliangzi.app.model.bean.MapTypeListsBean;
import com.meiliangzi.app.model.bean.MeetIDBean;
import com.meiliangzi.app.model.bean.MeetlistsBean;
import com.meiliangzi.app.model.bean.MessageListBean;
import com.meiliangzi.app.model.bean.MyClassInfoBean;
import com.meiliangzi.app.model.bean.News;
import com.meiliangzi.app.model.bean.NoticeMessagelistBean;
import com.meiliangzi.app.model.bean.Partment;
import com.meiliangzi.app.model.bean.PartyBranchBean;
import com.meiliangzi.app.model.bean.ProposerUserlistBean;
import com.meiliangzi.app.model.bean.QualityVideoCommentBean;
import com.meiliangzi.app.model.bean.QueryDataBean;
import com.meiliangzi.app.model.bean.QueryMapsBeans;
import com.meiliangzi.app.model.bean.QuerySendacarinfoBean;
import com.meiliangzi.app.model.bean.QueryUserInfoBean;
import com.meiliangzi.app.model.bean.QueryVideoCommentBean;
import com.meiliangzi.app.model.bean.QueryuserBean;
import com.meiliangzi.app.model.bean.QueryvehicleListBean;
import com.meiliangzi.app.model.bean.QuestionList;
import com.meiliangzi.app.model.bean.RankList;
import com.meiliangzi.app.model.bean.RecentClassInfobean;
import com.meiliangzi.app.model.bean.RecentOpenClassBean;
import com.meiliangzi.app.model.bean.Register;
import com.meiliangzi.app.model.bean.RepositoryinfoBean;
import com.meiliangzi.app.model.bean.SearchGroupBean;
import com.meiliangzi.app.model.bean.SearchUserBean;
import com.meiliangzi.app.model.bean.SendCarUserBean;
import com.meiliangzi.app.model.bean.SendacardeleteBean;
import com.meiliangzi.app.model.bean.SendacarinfoBean;
import com.meiliangzi.app.model.bean.SetAdminBean;
import com.meiliangzi.app.model.bean.StudyCenternBean;
import com.meiliangzi.app.model.bean.StudyInfo;
import com.meiliangzi.app.model.bean.StudyList;
import com.meiliangzi.app.model.bean.StudyMessageBean;
import com.meiliangzi.app.model.bean.TrainBannerBean;
import com.meiliangzi.app.model.bean.TrainQueryDataBean;
import com.meiliangzi.app.model.bean.TrainSignerUpBean;
import com.meiliangzi.app.model.bean.Updateuserinfo;
import com.meiliangzi.app.model.bean.User;
import com.meiliangzi.app.model.bean.UserAddGroupBean;
import com.meiliangzi.app.model.bean.UserCarSearchuserinfoBean;
import com.meiliangzi.app.model.bean.UserStar;
import com.meiliangzi.app.model.bean.Validate;
import com.meiliangzi.app.model.bean.VersionUpdate;
import com.meiliangzi.app.model.bean.VideoInfoBean;
import com.meiliangzi.app.model.bean.VoteBaseBean;
import com.meiliangzi.app.model.bean.VoteSubvotelistBean;
import com.meiliangzi.app.model.bean.VoteUsersubvoteBean;
import com.meiliangzi.app.model.bean.VpteSubvoteinfoBean;
import com.meiliangzi.app.model.bean.departmentuserlistBean;
import com.meiliangzi.app.ui.view.Academy.bean.ArticleListBean;
import com.meiliangzi.app.ui.view.Academy.bean.IndexColumnBean;
import com.meiliangzi.app.ui.view.Academy.bean.PageListBean;
import com.meiliangzi.app.ui.view.Academy.bean.PaperBean;
import com.meiliangzi.app.ui.view.Academy.bean.PersonalScorebean;
import com.meiliangzi.app.ui.view.Academy.bean.RuleListBean;
import com.meiliangzi.app.ui.view.Academy.bean.VideoListBean;
import com.meiliangzi.app.ui.view.AddMapLoctionActivity;
import com.meiliangzi.app.ui.view.sendcar.DriverListActivity;

import org.json.JSONObject;
import org.jsoup.Connection;

import java.io.File;

public interface IHttpService {

    /**
     * 注册
     *
     * @param context   上下文对象
     * @param loginName 登录名
     * @param password  登录名
     * @param userName  用户名
     * @param code
     */
    @HttpRequest(arguments = {"loginName", "password", "userName", "code"}, url = "common/", resultClass = Register.class, refreshMethod = "getResult")
    public void reg(Object context, String loginName, String password, String userName, String code);


    /**
     * 短信验证码
     *
     * @param context 上下文对象
     * @param phone   手机号
     */
    @HttpRequest(arguments = {"phone","code","text"}, url = "common/", resultClass = Validate.class, refreshMethod = "getValidate")
    public void sms(Object context, String phone,int code,String text);


    /**
     * 登录
     *
     * @param context   上下文对象
     * @param loginName 登录名
     * @param password  密码
     */
    @HttpRequest(arguments = {"loginName", "password"}, url = "common/", resultClass = User.class, refreshMethod = "getResult")
    public void login(Object context, String loginName, String password);

    /**
     * 地图类型列表
     * @param context
     */
    @HttpRequest(arguments = {}, url = "", resultClass = MapTypeListsBean.class, refreshMethod = "classificationlist")
    public void classificationlist(Object context);
    /**
     * 查询地址
     * @param context
     */
    @HttpRequest(arguments = {"key"}, url = "", resultClass = QueryMapsBeans.class, refreshMethod = "querymaps")
    public void querymaps(Object context,String key);
    /**
     * 县列表
     * @param context
     */
    @HttpRequest(arguments = {"cityId","classificationId"}, url = "", resultClass = CountyListbean.class, refreshMethod = "countylist")
    public void countylist(Object context,int cityId,int classificationId);
    /**
     * 地理位置添加
     * @param context
     */
    @HttpRequest(arguments = {"name","city_id","county_id","classification_id","phone","lng","lat","image","describe","user_id"}, url = "", resultClass = AddMapBean.class, refreshMethod = "addmaps")
    public void addmaps(Object context,String name,int city_id,int county_id,int classification_id,String phone,String lng,String lat,String image,String describe,int userId);
    /**
     * 地理位置修改
     * @param context
     */
    @HttpRequest(arguments = {"name","city_id","county_id","classification_id","phone","lng","lat","image","describe","id","user_id"}, url = "", resultClass = AddMapBean.class, refreshMethod = "addmaps")
    public void updateMap(Object context,String name,int city_id,int county_id,int classification_id,String phone,String lng,String lat,String image,String describe,String id,int userId);

    /**
     * 县列表
     * @param context
     */
    @HttpRequest(arguments = {"cityId",}, url = "", resultClass = CountyListbean.class, refreshMethod = "countylist")
    public void countylist(Object context,int cityId);
    /**
     * 市列表
     * @param context
     */
    @HttpRequest(arguments = {}, url = "", resultClass = CityListBean.class, refreshMethod = "citylist")
    public void citylist(Object context);
    /**
     * 获取党支部详情
     * @param context
     */
    @HttpRequest(arguments = {"company_id"}, url = "common/", resultClass = PartyBranchBean.class, refreshMethod = "querypartbranch")
    public void querypartbranch(Object context, int company_id);
    /**
     * 微信登录
     *
     * @param context  上下文对象
     * @param unionId  微信的返回unionId
     * @param nickName 微信名称
     * @param userHead 头像
     */
    @HttpRequest(arguments = {"unionId", "nickName", "userHead"}, url = "common/", resultClass = User.class, refreshMethod = "getResult")
    public void login(Object context, String unionId, String nickName, String userHead);
//

    /**
     * 完善个人信息
     *
     * @param context        上下文对象
     * @param id             用户id
     * @param image          头像,如果不填写不修改
     * @param userName       用户名如果不填写不修改
     * @param companyId      公司ID如果不填写不修改
     * @param departmentId   部门id如果不填写不修改
     * @param isPartyMember  是否党员如果不填写不修改
     * @param userDepartment 用户职位
     * @param user_code      用户工号
     */
    @HttpRequest(arguments = {"id", "image", "userName", "companyId", "departmentId", "isPartyMember", "userDepartment", "user_code","partyBranchId","partyBranchsId"}, url = "common/", resultClass = Updateuserinfo.class, refreshMethod = "getResult")
    public void updateuserinfo(Object context, int id, File image, String userName, int companyId, int departmentId, String isPartyMember, String userDepartment, int user_code, int partyBranchId, int partyBranchsId);

    @HttpRequest(arguments = {"id", "image"}, url = "common/", resultClass = UserStar.class, refreshMethod = "getUserStar")
    public void updateuserinfo(Object context, String id, File image);

    /**
     * 修改密码
     *
     * @param context     上下文对象
     * @param userId      用户id
     * @param oldPassword 老密码
     * @param newPassowrd 新密码
     */
    @HttpRequest(arguments = {"userId", "oldPassword", "newPassowrd"}, url = "common/", resultClass = BaseBean.class, refreshMethod = "getResult")
    public void modifypassword(Object context, String userId, String oldPassword, String newPassowrd);


    /**
     * 忘记密码
     *
     * @param context     上下文对象
     * @param phone       电话
     * @param authcode    短信验证码
     * @param newPassword 新密码
     */
    @HttpRequest(arguments = {"phone", "authcode", "newPassword"}, url = "common/", resultClass = BaseBean.class, refreshMethod = "getResult")
    public void forgetpassword(Object context, String phone, String authcode, String newPassword);


    /**
     * 获取部门列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"user_id"}, url = "common/", resultClass = Partment.class, refreshMethod = "getData")
    public void querydepartment(Object context, int user_id);
    /**
     * 获取部门列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"departmentId"}, url = "common/", resultClass = departmentuserlistBean.class, refreshMethod = "getdepartmentuserlist")
    public void departmentuserlist(Object context, int departmentId);
//
//

    /**
     * 获取公司列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "common/", resultClass = Company.class, refreshMethod = "getData")
    public void querycampany(Object context);


    /**
     * 取得视频文章列表
     *
     * @param context     上下文对象
     * @param type        文章视频类型0表示全部内容，1基础课程，2党员学习课程
     * @param currentPage 分页，当前页
     * @param pageSize    每页大小
     * @param userId      用户编号
     */
    @HttpRequest(arguments = {"type", "currentPage", "pageSize", "userId"}, url = "community/", resultClass = ArticalList.class, refreshMethod = "getData")
    public void querylist(Object context, String type, String currentPage, String pageSize, String userId);

    /**
     * sq_14 记录在线时长
     * @param context     上下文对象
     * @param duration    在线时长  分钟
     * @param userId      用户编号
     */
    @HttpRequest(arguments = {"duration",  "userId"}, url = "community/", resultClass = BaseBean.class, refreshMethod = "useronlinetimebyid")
    public void useronlinetimebyid(Object context, int duration, int userId);

    /**
     * 取得用户学习时长天数学习的课程数
     *
     * @param context 上下文对象
     * @param userId  用户编号
     */
    @HttpRequest(arguments = {"userId"}, url = "community/", resultClass = StudyInfo.class, refreshMethod = "getTime")
    public void studyinfo(Object context, String userId);


    @HttpRequest(arguments = {"userId"}, url = "common/", resultClass = JSONObject.class, refreshMethod = "getStatus")
    public void queryuserloginlog(Object context, String userId);


    /**
     * 取得排名信息
     *
     * @param context           上下文对象
     * @param filter_type       是否按基础知识或党员交易进行筛选 0：不筛选，1：按基础知识筛选，2：按党员排名筛选
     * @param filter_department 是否按不们筛选 0:不筛选，其他值：按部门编号筛选
     * @param order_avgscore    0:按完成率排名 1：按平均成绩排序
     * @param pageSize          每页大小
     * @param currentPage       当前页
     */
    @HttpRequest(arguments = {"filter_type", "filter_department", "order_avgscore", "pageSize", "currentPage", "timescreening","user_id","filter_dpartybranch","filter_dpartybranchs"}, url = "community/", resultClass = RankList.class, refreshMethod = "getRank")
    public void getranking(Object context, int filter_type, int filter_department, int order_avgscore, int pageSize, int currentPage, int timescreening, int user_id, int filter_dpartybranch, int filter_dpartybranchs);

    /**
     * 我的收藏
     *
     * @param context     上下文对象
     * @param userId      用户编号
     * @param pageSize
     * @param currentPage
     */
    @HttpRequest(arguments = {"userId", "pageSize", "currentPage"}, url = "community/", resultClass = ArticalList.class, refreshMethod = "getData")
    public void collectionlist(Object context, String userId, String pageSize, String currentPage);

    /**
     * 绑定手机
     * @param context
     * @param userId
     * @param phone
     * @param authcode
     */
    @HttpRequest(arguments = {"userId", "phone", "authcode","password"}, url = "common/", resultClass = BindPhoneBean.class, refreshMethod = "addphone")
    public void addphone(Object context, String userId, String phone, String authcode, String password);

    /**
     * 查看文章详情
     *
     * @param context 上下文对象
     * @param userId  用户编号
     * @param id      文章编号
     */
    @HttpRequest(arguments = {"userId", "id"}, url = "community/", resultClass = ArticalDetail.class, refreshMethod = "getData")
    public void articleinfo(Object context, String userId, String id);

    /**
     * 用户收藏或取消收藏文章或视频
     *
     * @param context   上下文对象
     * @param userId    用户编号
     * @param subjectId 视频or文章编号
     * @param value     value:0收藏，1：取消收藏
     */
    @HttpRequest(arguments = {"userId", "subjectId", "value"}, url = "community/", resultClass = BaseBean.class, refreshMethod = "getResult")
    public void usercollect(Object context, String userId, String subjectId, String value);

    /**
     * 点赞
     *
     * @param context    上下文对象
     * @param categoryId 文章编号
     * @param userId     用户编号
     */
    @HttpRequest(arguments = {"categoryId", "userId"}, url = "community/", resultClass = BaseBean.class, refreshMethod = "getParise")
    public void ispraise(Object context, String categoryId, String userId);


    /**
     * 获取帖子的所有评论
     *
     * @param context     上下文对象
     * @param subjectId   学习总表的id
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @param userId      用户编号
     */
    @HttpRequest(arguments = {"subjectId", "pageSize", "currentPage", "userId"}, url = "community/", resultClass = CommonList.class, refreshMethod = "getData")
    public void getallcomment(Object context, String subjectId, String pageSize, String currentPage, String userId);

    /**
     * 发布评论
     *
     * @param context   上下文对象
     * @param userId    用户编号
     * @param subjectId 视频or文章编号
     * @param parentId` 父评论id,-1为每页父评论
     * @param content   评论内容
     */
    @HttpRequest(arguments = {"userId", "subjectId", "parentId", "content"}, url = "community/", resultClass = BaseBean.class, refreshMethod = "getCommon")
    public void publishcomment(Object context, String userId, String subjectId, String parentId, String content);


    /**
     * 点赞
     *
     * @param context
     * @param praiseId      评论编号
     * @param userId        点赞用户编号
     * @param praise_userid 被点赞用户编号
     */
    @HttpRequest(arguments = {"praiseId", "userId", "praise_userid"}, url = "common/", resultClass = BaseBean.class, refreshMethod = "getPariseResult")
    public void ispraiselog(Object context, String praiseId, String userId, String praise_userid);


    /**
     * 取得我应学习的课程列表
     *
     * @param context     上下文对象
     * @param userId      用户编号
     * @param type        0：全部，1：未学习课程的列表，2：以学习的课程列表
     * @param pageSize    每页大小
     * @param currentPage 当前页
     * @param isPage      是否分页 0：不分页，1分页（暂时没用到这个参数）
     */
    @HttpRequest(arguments = {"userId", "type", "pageSize", "currentPage", "isPage"}, url = "community/", resultClass = StudyList.class, refreshMethod = "getData")
    public void userstudylist(Object context, String userId, String type, String pageSize, String currentPage, String isPage);


    /**
     * 取得试卷
     *
     * @param context 上下文对象
     * @param id      视频or文章编号
     */
    @HttpRequest(arguments = {"id"}, url = "community/", resultClass = QuestionList.class, refreshMethod = "getData")
    public void gettestpaper(Object context, String id);

    /**
     * 文章取消点赞
     *
     * @param context    上下文对象
     * @param categoryId 文章编号
     * @param userId     用户编号
     */
    @HttpRequest(arguments = {"categoryId", "userId"}, url = "community/", resultClass = BaseBean.class, refreshMethod = "getUnParise")
    public void undopraise(Object context, String categoryId, String userId);


    /**
     * 保存成绩，并且在学生学习的课程中标识当前已经学习
     *
     * @param context   上下文对象
     * @param userId    用户编号
     * @param subjectId 视频or文章编号
     * @param score     考试成绩
     */
    @HttpRequest(arguments = {"userId", "subjectId", "score","answerTime"}, url = "community/", resultClass = BaseBean.class, refreshMethod = "getResult")
    public void savescore(Object context, int userId, int subjectId, int score,long answerTime);


    /**
     * 查看视频详情
     *
     * @param context 上下文对象
     * @param id      视频表编号
     * @param userId  用户编号
     */
    @HttpRequest(arguments = {"id", "userId"}, url = "community/", resultClass = DetailDetail.class, refreshMethod = "getResult")
    public void videoinfo(Object context, String id, String userId);


    /**
     * 评论取消点赞
     *
     * @param context  上下文对象
     * @param praiseId 评论编号编号
     * @param userId   点赞用户编号
     */
    @HttpRequest(arguments = {"praiseId", "userId"}, url = "community/", resultClass = BaseBean.class, refreshMethod = "unParise")
    public void undopraiselog(Object context, String praiseId, String userId);

    /**
     *  标记已答题
     * @param context   上下文对象
     * @param userId    用户编号
     * @param categoryId    文章或视频编号
     */
    @HttpRequest(arguments = {"userId", "categoryId"}, url = "community/", resultClass = BaseBean.class, refreshMethod = "getAnswerStatus")
    public void updatestudystatus(Object context, String userId, String categoryId);

    /**
     * 版本更新
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = VersionUpdate.class, refreshMethod = "getVersion")
    public void queryversionnumber(Object context);

    /**
     * 首页行业资讯5条
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = IndexNewsBean.class, refreshMethod = "indexnews")
    public void indexnews(Object context);

    /**
     * 资讯类型列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = IndexNewsTypeBean.class, refreshMethod = "newstypelist")
    public void newstypelist(Object context);

    /**
     * 资讯列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"type", "currentPage", "pageSize"}, url = "", resultClass = IndexNewsListsBean.class, refreshMethod = "querynewslist")
    public void querynewslist(Object context, int type, int currentPage, int pageSize);
    /**
     * 资讯列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"type", "currentPage", "pageSize"}, url = "", resultClass = IndexNewsListsBean.class, refreshMethod = "querynewslist")
    public void querynewslist1(Object context, int type, int currentPage, int pageSize);

    /**
     * 资讯详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"id"}, url = "", resultClass = News.class, refreshMethod = "querynewsinfo")
    public void querynewsinfo(Object context, String id);


    /**
     * 记录资讯阅读数
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"id"}, url = "", resultClass = IndexNewsBean.class, refreshMethod = "recordsbrowse")
    public void recordsbrowse(Object context, String id);

    /**
     * 首页轮播图
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = BannerBean.class, refreshMethod = "indexslideshow")
    public void indexslideshow(Object context);
    /**
     * 培训轮播图
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = TrainBannerBean.class, refreshMethod = "querypiclist")
    public void querypiclist(Object context);
    /**
     * 首页轮播图
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = HomePageBean.class, refreshMethod = "querytotalstatistics")
    public void querytotalstatistics(Object context);

    /**
     * 视频会议列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"currentPage","pageSize"}, url = "", resultClass = MeetlistsBean.class, refreshMethod = "meetinglist")
    public void meetinglist(Object context, int currentPage, int pageSize);

    /**
     * 视频会议列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"id","value"}, url = "", resultClass = MeetIDBean.class, refreshMethod = "getmeeting")
    public void getmeeting(Object context, int id, String value);
    /**
     * 近期开班列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"currentPage","pageSize","userId"}, url = "", resultClass = RecentOpenClassBean.class, refreshMethod = "getrecentclasslist")
    public void recentclasslist(Object context,int currentPage,int pageSize, int userId);
    /**
     * 学习中心
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"currentPage","pageSize"}, url = "", resultClass = StudyCenternBean.class, refreshMethod = "getstudylists")
    public void qualityvideolist(Object context,int currentPage,int pageSize);
    /**
     * 信息查询
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","name","cardId"}, url = "", resultClass = TrainQueryDataBean.class, refreshMethod = "getqueryuserstudylist")
    public void queryuserstudylist(Object context,int userId,String name,String cardId);
    /**
     * 查询资料列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = FileDownloadBean.class, refreshMethod = "getfiledownloadt")
    public void filedownload(Object context);
    /**
     * 信息结果
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"studyId",}, url = "", resultClass = QueryDataBean.class, refreshMethod = "getqqueryuserstudyinfo")
    public void queryuserstudyinfo(Object context,int studyId);
    /**
     * 视频学习详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"videoId"}, url = "", resultClass = VideoInfoBean.class, refreshMethod = "getqualityvideoinfo")
    public void qualityvideoinfo(Object context,int videoId);
    /**
     * 开班详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"trainingId","userId"}, url = "", resultClass = RecentClassInfobean.class, refreshMethod = "getrecentclassinfo")
    public void recentclassinfo(Object context,int trainingId,int userId);
    /**
     * 视频学评论查询
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"videoId","currentPage","pageSize"}, url = "", resultClass = QueryVideoCommentBean.class, refreshMethod = "getqueryvideocomment")
    public void queryvideocomment(Object context,int videoId,int currentPage,int pageSize);
    /**
     * 开始报名
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"train_id","userId","name","idCard","sex","phone","education","major","company","companyType","department","position","qq","remarks"}, url = "", resultClass = TrainSignerUpBean.class, refreshMethod = "getcultivatetrainbaoming")
    public void cultivatetrainbaoming(Object context,int train_id,int userId,String name,String idCard,String sex,String phone,String education,String major,String company,String companyType,String department,String position,String qq,String remarks);
    /**
     * 我的成绩
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = MyClassInfoBean.class, refreshMethod = "getusercurriculum")
    public void usercurriculum(Object context,int userId);
    /**
     * 发表评论
     *
             * @param context 上下文对象
     */
    @HttpRequest(arguments = {"videoId","commentPerson","commentContent"}, url = "", resultClass = QualityVideoCommentBean.class, refreshMethod = "getqualityvideocomment")
    public void qualityvideocomment(Object context,int videoId,String commentPerson,String commentContent );
 /* 部门列表
     *
             * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = DepartmentuserNumberBean.class, refreshMethod = "getmentusernumber")
    public void querydepartmentusernumber(Object context,int userId);
    /**
     * 同意添加好友
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","friendId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getagreefriendapply")
    public void agreefriendapply(Object context,int userId,int friendId );
    /**
     * 同意加入群组好友
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId","gorupName"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getagreefriendapply")
    public void agreegroupapply(Object context,int userId,int groupId,String gorupName );
    /**
     * 管理员同意加入群组好友
     *
     * @param context 上下文对象
     *@param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId","friendsId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getagreefriendapply")
    public void agreeaddgroup(Object context,int userId,int groupId,int friendsId );
    /**
     * 拒绝添加好友
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","friendId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getagreefriendapply")
    public void deletefriend(Object context,int userId,int friendId );
    /**
     * 拒绝加入组群
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId","gorupName"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getagreefriendapply")
    public void refusegroupapply(Object context,int userId,int groupId ,String gorupName);
    /**
     * 管理员拒绝加入组群
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId","friendsId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getagreefriendapply")
    public void refuseaddgroup(Object context,int userId,int groupId ,int friendsId);
    /**
     * 管理员删除加入组群
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId","friendsId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getdeleteaddgroupmessage")
    public void deleteaddgroupmessage(Object context,int userId,int groupId,int friendsId );
    /**
     * 添加好友
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"myId","friendId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getaddfriend")
    public void addfriend(Object context,int myId,int friendId );
    /**
     * 查看好友申请消息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = ConfirmationBean.class, refreshMethod = "getfriendapplymessage")
    public void friendapplymessage(Object context,int userId);
    /**
     * 创建群组
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","friendId","groupName"}, url = "", resultClass = CreatGroupChatBean.class, refreshMethod = "getgroupaddinvitation")
    public void groupaddinvitation(Object context,int userId,String friendId,String groupName);
    /**
     * 获取好友列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = FridentListBean.class, refreshMethod = "getfriendlist")
    public void friendlist(Object context,int userId);
    /**
     * 获取我的群组列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = GroupListBean.class, refreshMethod = "getusergrouplist")
    public void usergrouplist(Object context,int userId);
    /**
     * 获取我参与群组列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = GroupListBean.class, refreshMethod = "getusergrouplist")
    public void grouplist(Object context,int userId);
    /**
     * 获取系统消息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","type"}, url = "", resultClass = NoticeMessagelistBean.class, refreshMethod = "getnoticemessagelist")
    public void noticemessagelist(Object context,int userId,int type);
    /**
     * 获取封面消息提示
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = MessageListBean.class, refreshMethod = "getmessagelist")
    public void messagelist(Object context,int userId);
    /**
     * 获取学习消息提示
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","type"}, url = "", resultClass = StudyMessageBean.class, refreshMethod = "getcategorymessagelist")
    public void categorymessagelist(Object context,int userId,int type);
    /**
     * 查询群信息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"groupId"}, url = "", resultClass = GroupinfoBean.class, refreshMethod = "getgroupinfo")
    public void groupinfo(Object context,int groupId);
    /**
     * 查询群用户信息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"groupId"}, url = "", resultClass = GroupUserlistBean.class, refreshMethod = "getgroupuserlist")
    public void groupuserlist(Object context,int groupId);
    /**
     * 删除并且退出
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId"}, url = "", resultClass = ExitGroupBean.class, refreshMethod = "getexitgroup")
    public void exitgroup(Object context,int userId ,int groupId);
    /**
     * 解散并且退出
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId"}, url = "", resultClass = ExitGroupBean.class, refreshMethod = "getexitgroup")
    public void dissolutiongroup(Object context,int userId ,int groupId);

    @HttpRequest(arguments = {"groupId", "image"}, url = "", resultClass = AddFrouppicBean.class, refreshMethod = "getaddgrouppic")
    public void addgrouppic(Object context, int groupId, File image);
    /**
     * 邀请用户加入群组
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId","friendId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getpersonaljoingroup")
    public void personaljoingroup(Object context,int userId,int groupId,String friendId);
    /**
     * 设置群管理员
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId"}, url = "", resultClass = SetAdminBean.class, refreshMethod = "getsetadmin")
    public void addadmin(Object context,int userId,int groupId);
    /**
     * 设置群管理员
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","groupId"}, url = "", resultClass = SetAdminBean.class, refreshMethod = "getsetadmin")
    public void removeadmin(Object context,int userId,int groupId);
    /**
     * 查看用户个人信息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "common/", resultClass = QueryUserInfoBean.class, refreshMethod = "getqueryuserinfo")
    public void queryuserinfo(Object context,int userId);
    /**
     * 搜索好友
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userName","myId"}, url = "", resultClass = SearchUserBean.class, refreshMethod = "getsearchuser")
    public void searchuser(Object context,String userName,int myId);
    /**
     * 搜索用户
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userName"}, url = "common/", resultClass = UserCarSearchuserinfoBean.class, refreshMethod = "getsearchuserinfo")
    public void searchuserinfo(Object context,String userName);

    /**
     * 搜索群组
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"groupInfo","userId"}, url = "", resultClass = SearchGroupBean.class, refreshMethod = "getsearchgroup")
    public void searchgroup(Object context,String groupInfo,int userId);
    /**
     * 群组列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = SearchGroupBean.class, refreshMethod = "getsearchgroup")
    public void grouplists(Object context,int userId);
    /**
     * 申请加群
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"groupId","userId"}, url = "", resultClass = UserAddGroupBean.class, refreshMethod = "getuseraddgroup")
    public void useraddgroup(Object context,int groupId,int userId);
    /**
     * 删除学习消息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","categoryId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getdeletecategorymessage")
    public void deletecategorymessage(Object context,int userId,int categoryId);
    /**
     * 删除通知消息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","noticeId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getdeletenoticemessage")
    public void deletenoticemessage(Object context,int userId,int noticeId);
    /**
     * 清空消息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getemptynoticemessage")
    public void emptynoticemessage(Object context,int userId);
    /**
     * 清空消息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getemptynoticemessage")
    public void emptycategorymessage(Object context,int userId);

    /**
     * 查询是否为好友
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","friendId"}, url = "", resultClass = AgreeFriendapplyBean.class, refreshMethod = "getisfriend")
    public void isfriend(Object context,int userId,int friendId);
    /**
     * 投票项目列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = VoteBaseBean.class, refreshMethod = "getvotelist")
    public void votelist(Object context);
    /**
     * 投票项目列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"voteId","userId"}, url = "", resultClass = VoteSubvotelistBean.class, refreshMethod = "getvotelist")
    public void subvotelist(Object context,int voteId,int userId);
    /**
     * 投票
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"subVoteId","userId"}, url = "", resultClass = VoteUsersubvoteBean.class, refreshMethod = "getusersubvote")
    public void usersubvote(Object context,int subVoteId,int userId);
    /**
     * 投票内容详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"subVoteId","userId"}, url = "", resultClass = VpteSubvoteinfoBean.class, refreshMethod = "getsubvoteinfo")
    public void subvoteinfo(Object context,int subVoteId,int userId);
    /**
     * 投票内容详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = IndexpicBean.class, refreshMethod = "getindexpic")
    public void indexpic(Object context);
    /**
     * 取得所有部门列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = CheckDepartmentsBean.class, refreshMethod = "getdepartments")
    public void departments(Object context);
    /**
     * 取得所有部门列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"endYear","month","departId","nature","projectType","lightStatus"}, url = "", resultClass = CheckProjectBean.class, refreshMethod = "getprojects")
    public void projects(Object context,String endYear,String month,int departId,int nature ,int projectType ,int lightStatus);
    /**
     * 取得所有部门列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = CheckProjectBean.class, refreshMethod = "getprojects")
    public void projects(Object context);
    /**
     * 取得所有子项目列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"proId"}, url = "", resultClass = CheckChildenProjectBean.class, refreshMethod = "getsubpros")
    public void subpros(Object context,int proId);
    /**
     * 取得所有子项目列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"projectId"}, url = "project/", resultClass = CheckProjectDetBean.class, refreshMethod = "getdet")
    public void det(Object context,int projectId);
    /**
     * 任务详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"taskId"}, url = "task/", resultClass = CheckProjectTaskDetBean.class, refreshMethod = "getdet")
    public void det(Object context,String taskId);
    /**
     * 获取图文验证码
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "common/", resultClass = ImageCodeBean.class, refreshMethod = "getimagecode")
    public void imagecode(Object context);
    /**
     * 获取图文验证码
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"search","currentPage","pageSize"}, url = "", resultClass = CommonsListBean.class, refreshMethod = "getindexrepository")
    public void indexrepository(Object context,String search,int currentPage,int pageSize);
    /**
     * 获取图文验证码
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"repositoryId"}, url = "", resultClass = RepositoryinfoBean.class, refreshMethod = "getrepositoryinfo")
    public void repositoryinfo(Object context,int repositoryId);
    /**
     * 派车首页
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"plateStatus","currentPage","pageSize"}, url = "", resultClass = IndexSendacarBean.class, refreshMethod = "getindexsendacar")
    public void indexsendacar(Object context,int plateStatus,int currentPage,int pageSize );
    /**
     * 派车首页
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"currentPage","pageSize"}, url = "", resultClass = IndexSendacarBean.class, refreshMethod = "getindexsendacar")
    public void indexsendacar(Object context,int currentPage,int pageSize );
    /**
     * 获取驾驶人员列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = QueryuserBean.class, refreshMethod = "getqueryuserlist")
    public void queryuserlist(Object context);
    /**
     * 派车删除
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"sendACarId"}, url = "", resultClass = SendacardeleteBean.class, refreshMethod = "getsendacardelete")
    public void sendacardelete(Object context,int sendACarId);
    /**
     * 派车详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"sendACarId"}, url = "", resultClass = SendacarinfoBean.class, refreshMethod = "getsendacarinfo")
    public void sendacarinfo(Object context,int sendACarId);
    /**
     * 派车详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"sendACarId","returnTime","initialMileage","returnMileage","mileage","remarks"}, url = "", resultClass = SendacardeleteBean.class, refreshMethod = "getsendacarinfoadd")
    public void sendacarinfoadd(Object context,int sendACarId,String returnTime,String initialMileage,String returnMileage,String mileage,String remarks);


    /**
     * 派车详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"endAt","startAt"}, url = "", resultClass = QuerySendacarinfoBean.class, refreshMethod = "getquerysendacarlist")
    public void querysendacarlist(Object context,String endAt,String startAt);
    /**
     * 派车详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"sendACarInfoId","returnTime","initialMileage","returnMileage","mileage","remarks"}, url = "", resultClass = SendacardeleteBean.class, refreshMethod = "getsendacarinfoadd")
    public void sendacarinfoupdate(Object context,int sendACarInfoId,String returnTime,String initialMileage,String returnMileage,String mileage,String remarks);
    /**
     * 派车申请人校验
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = ProposerUserlistBean.class, refreshMethod = "getproposeruserlist")
    public void proposeruserlist(Object context);

    /**
     * 车辆信息列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {}, url = "", resultClass = QueryvehicleListBean.class, refreshMethod = "getQueryvehicleListBean")
    public void queryvehiclelist(Object context);
    /**
     * 意见反馈
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","deliveryContent"}, url = "", resultClass = FreebackBean.class, refreshMethod = "getfeedbackadd")
    public void feedbackadd(Object context,int userId,String deliveryContent);
    /**
     * 根据用户id及试卷类型获取试卷分页信息
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","type","pageNumber","pageSize"}, url = "examinationUserPaper/", resultClass = PageListBean.class, refreshMethod = "getPageList")
    public void getPageList(Object context,String userId,String type ,int pageNumber ,int pageSize );

    /**
     * 根据试卷ID获取试题
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId","paperId"}, url = "examinationUserPaperQuestions/", resultClass = PaperBean.class, refreshMethod = "getList")
    public void getList(Object context,String userId,String paperId);
    /**
     * 栏目列表没分页
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"columnType"}, url = "column/", resultClass = IndexColumnBean.class, refreshMethod = "getList")
    public void getList(Object context,String columnType);
    /**
     * 文章列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"columnId","pageNumber","pageSize"}, url = "essay/", resultClass = ArticleListBean.class, refreshMethod = "getPageList")
    public void getPageList(Object context,String columnId ,String pageNumber ,String pageSize );
    /**
     * 视频列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"columnId","pageNumber","pageSize"}, url = "video/", resultClass = VideoListBean.class, refreshMethod = "getPageList")
    public void getPageList(Object context,String columnId ,int pageNumber ,String pageSize );
    /**
     * 搜索文章
     *
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"pageNumber","pageSize","title"}, url = "essay/", resultClass = ArticleListBean.class, refreshMethod = "getPageList")
    public void getPageList1(Object context,int pageNumber ,String pageSize ,String title);
    /**
     * 搜索视频
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"pageNumber","pageSize","title"}, url = "video/", resultClass = VideoListBean.class, refreshMethod = "getPageList")
    public void getPageList1(Object context,int pageNumber ,int pageSize ,String title);

    /**
     * 积分列表
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"userId"}, url = "rule/", resultClass = RuleListBean.class, refreshMethod = "getList")
    public void get(Object context,String userId );
    /**
     * 地理位置详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"id"}, url = "", resultClass = MapInfoBean.class, refreshMethod = "mapInfo")
    public void mapInfo(Object context,int id);
    /**
     * 地理位置详情
     *
     * @param context 上下文对象
     */
    @HttpRequest(arguments = {"image"}, url = "", resultClass = AddMapBean.class, refreshMethod = "imageupload")
    public void imageupload(Object context,File image);

}




