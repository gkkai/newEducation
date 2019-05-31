package com.meiliangzi.app.ui.view.Academy.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.githang.statusbar.StatusBarCompat;
import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.db.bean.MainBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.FreeBackActivity;
import com.meiliangzi.app.ui.SetttingActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.ErrorBankActivity;
import com.meiliangzi.app.ui.view.Academy.NewPersonCenterActivity;
import com.meiliangzi.app.ui.view.Academy.StudyResultActivity;
import com.meiliangzi.app.ui.view.Academy.TotalscoreActivity;
import com.meiliangzi.app.ui.view.Academy.WeekAnswerActivity;
import com.meiliangzi.app.ui.view.Academy.bean.UserInfoBean;
import com.meiliangzi.app.ui.view.MapNewActivity;
import com.meiliangzi.app.ui.view.ZoomActivity;
import com.meiliangzi.app.ui.view.checkSupervise.CheckSuperviseProjectListActivity;
import com.meiliangzi.app.ui.view.creativecommons.CommonsListActivity;
import com.meiliangzi.app.ui.view.sendcar.SendCarActivity;
import com.meiliangzi.app.ui.view.vote.VoteActivity;
import com.meiliangzi.app.widget.CircleImageView;
import com.meiliangzi.app.widget.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN;
import static com.umeng.socialize.bean.SHARE_MEDIA.WEIXIN_CIRCLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalCenterFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.gradview)
    MyGridView gradview;
    BaseVoteAdapter<MainBean> voteAdapter;
    List<MainBean> mainBeanList=new ArrayList<>();
    @BindView(R.id.ll_Feedback)
    LinearLayout ll_Feedback;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.ll_studyscore)
    LinearLayout ll_studyscore;
    @BindView(R.id.ll_answer)
    LinearLayout ll_answer;
    @BindView(R.id.ll_examination)
    LinearLayout ll_examination;
    @BindView(R.id.ll_learningoutcomes)
    LinearLayout ll_learningoutcomes;
    @BindView(R.id.rl_edit)
    RelativeLayout rl_edit;

    @BindView(R.id.ivImg)
    CircleImageView ivImg;

    @BindView(R.id.tvUserName)
    TextView tvUserName;
    @BindView(R.id.ll_integral_shopping)
    LinearLayout ll_integral_shopping;
    @BindView(R.id.ll_studyresult)
    LinearLayout ll_studyresult;

    @BindView(R.id.ll_share)
    LinearLayout ll_share;
    private Dialog dialog;
    private View inflate;
    private LinearLayout ll_weinxin;
    private LinearLayout ll_penyouquan;
    private LinearLayout ll_copy;
    private TextView concle;

    @BindView(R.id.tv_totle_code)
    TextView tv_totle_code;
    public PersonalCenterFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.de_draft_color));
        return createView(inflater.inflate(R.layout.fragment_personal_center, container, false));
    }
    @Override
    public void onResume() {
        super.onResume();
        StatusBarCompat.setStatusBarColor(getActivity(), getResources().getColor(R.color.de_draft_color));
        userInfo();

    }
    private void userInfo(){
        Map<String,String> map=new HashMap();
        map.put("userId",NewPreferManager.getId());
        map.put("orgId",NewPreferManager.getOrgId());
        OkhttpUtils.getInstance(getContext()).getList("academyService//userInfo/findByUserInfo", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(String json) {
                Gson gson=new Gson();
                UserInfoBean user=gson.fromJson(json,UserInfoBean.class);
                NewPreferManager.saveUserSex(user.getData().getUserSex());
                NewPreferManager.saveOrganizationName(user.getData().getOrganizationName());
                NewPreferManager.saveOrganizationId(user.getData().getOrganizationId());
                NewPreferManager.savePhoto(user.getData().getPhoto());
                NewPreferManager.saveUserName(user.getData().getUserName());
                NewPreferManager.saveBirthDate(user.getData().getBirthDate());
                NewPreferManager.saveNativePlace(user.getData().getNativePlace());
                NewPreferManager.savePartyMasses(user.getData().getPartyMasses());
                NewPreferManager.savePartyName(user.getData().getPartyName());
                NewPreferManager.savePartyPositionName(user.getData().getPartyPositionName());
                NewPreferManager.savePhone(user.getData().getPhone());
                NewPreferManager.saveUserCode(user.getData().getUserCode());
                NewPreferManager.saveWorkNumber(user.getData().getWorkNumber());
                NewPreferManager.savePositionName(user.getData().getPositionName());
                NewPreferManager.savePositionId(user.getData().getPositionId());
                NewPreferManager.saveId(user.getData().getId());
                NewPreferManager.saveUserTotalScore(user.getData().getUserTotalScore());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       String s= NewPreferManager.getUserTotalScore();
                        tv_totle_code.setText(s);
                        if (NewPreferManager.getPhoto().startsWith("http")) {

                            ImageLoader.getInstance().displayImage(NewPreferManager.getPhoto(), ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
                        } else {
                            ImageLoader.getInstance().displayImage("file:///" + NewPreferManager.getPhoto(), ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
                        }
                    }
                });


            }
        });


    }

    @Override
    protected void findWidgets() {
        voteAdapter = new BaseVoteAdapter<MainBean>(getActivity(), gradview, R.layout.main_list) {
            @Override
            public void convert(final BaseViewHolder helper, final MainBean item) {
                helper.setImageByUrl(R.id.imageid,null,item.getImgid(),item.getImgid());

                helper.setText(R.id.name,item.getName());
        }
        };
        int[] id={R.mipmap.dispatchingsystem,R.mipmap.handlingassessment,R.mipmap.videoconferencing,R.mipmap.mapservice
                ,R.mipmap.knowledgebase,R.mipmap.votingmanagement};
        String[] name={"派车系统","考核督办","视频会议","地图服务","知识库","投票管理"};
        for (int i=0;i<6;i++){
            MainBean mainBean =new MainBean();
            mainBean.setImgid(id[i]);
            mainBean.setName(name[i]);
             mainBeanList.add(mainBean);

        }
        tv_totle_code.setOnClickListener(this);
        voteAdapter.setDatas(mainBeanList);
        gradview.setAdapter(voteAdapter);
        gradview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               switch (position){
                   case 0:
                       // TODO  知识共享
                       Intent sendcar=new Intent(getActivity(), SendCarActivity.class);
                       startActivity(sendcar);
                       break;
                   case 1:
                       // TODO  考核督办
                       Intent intentCheck=new Intent(getActivity(), CheckSuperviseProjectListActivity.class);
                       startActivity(intentCheck);
                       break;
                   case 2:
                       // TODO  视频会议
                       Intent intentZoom=new Intent(getActivity(), ZoomActivity.class);
                       startActivity(intentZoom);
                       break;
                   case 3:
                       // TODO  查看更多
                       Intent intentmap=new Intent(getActivity(), MapNewActivity.class);
                       startActivity(intentmap);
                       break;
                   case 4:
                       // TODO  知识共享
                       Intent commmons=new Intent(getActivity(), CommonsListActivity.class);
                       startActivity(commmons);
                       break;
                   case 5:
                       // TODO 投票管理
                       Intent intentvote=new Intent(getActivity(), VoteActivity.class);
                       startActivity(intentvote);
                       break;

               }

            }
        });
        ll_Feedback.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        ll_studyscore.setOnClickListener(this);
        ll_answer.setOnClickListener(this);
        ll_examination.setOnClickListener(this);
        ll_learningoutcomes.setOnClickListener(this);
        rl_edit.setOnClickListener(this);
        ll_integral_shopping.setOnClickListener(this);
        ll_studyresult.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        Log.i("Name====++++",(NewPreferManager.getUserName()));
        String name1=NewPreferManager.getUserName();
        tvUserName.setText(name1);
        tv_totle_code.setText(NewPreferManager.getUserTotalScore());

    }

    @Override
    protected void initComponent() {
        if (NewPreferManager.getPhoto().startsWith("http")) {
            ImageLoader.getInstance().displayImage(NewPreferManager.getPhoto(), ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
        } else {
            ImageLoader.getInstance().displayImage("file:///" + NewPreferManager.getPhoto(), ivImg, MyApplication.getSimpleOptions(R.mipmap.ic_default_star, R.mipmap.ic_default_star));
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
                    case R.id.tv_totle_code:
                        //TODO 学习积分

                        IntentUtils.startAty(getActivity(), TotalscoreActivity.class);
                        break;
            case R.id.ll_studyscore:
                //TODO 学习积分

                IntentUtils.startAty(getActivity(), TotalscoreActivity.class);
                break;
                     case R.id.ll_answer:
                     //TODO 智能答题

                         IntentUtils.startAtyWithSingleParam(getActivity(), WeekAnswerActivity.class,"type","0");
                        break;
                   case R.id.ll_examination:
                            //TODO 每周一答
                            IntentUtils.startAtyWithSingleParam(getActivity(), WeekAnswerActivity.class,"type","1");

                break;
            case R.id.ll_integral_shopping:
                //TODO 积分商城
                //IntentUtils.startAty(getActivity(), WeekAnswerActivity.class);

                break;
            case R.id.ll_studyresult:
                //TODO 学习成果
                IntentUtils.startAty(getActivity(), StudyResultActivity.class);

                break;
            case R.id.ll_learningoutcomes:
                //TODO 错题库

                IntentUtils.startAty(getActivity(), ErrorBankActivity.class);

                break;

                    case R.id.ll_Feedback:
                        //TODO 意见反馈
                        IntentUtils.startAty(getActivity(), FreeBackActivity.class);
                        break;
                    case R.id.ll_setting:
                        IntentUtils.startAty(getActivity(), SetttingActivity.class);
                        break;
                    case R.id.ll_share:
                        //TODO 分享
                        shareurl();


                        break;
            case R.id.rl_edit:

               // IntentUtils.startAty(getActivity(), NewPersonCenterActivity.class);
                IntentUtils.startAtyForResult(this,NewPersonCenterActivity.class,1);

                break;

            case R.id.ll_weinxin:
                sharewein();
                dialog.dismiss();
                break;
            case R.id.ll_penyouquan:
                sharewcircle();
                dialog.dismiss();
                break;
            case R.id.ll_copy:

                IntentUtils.startAty(getActivity(), NewPersonCenterActivity.class);
                dialog.dismiss();
                break;
            case R.id.concle:

                dialog.dismiss();

                break;


                    default:
                        break;
                }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        userInfo();


    }

    public void shareurl(){
        dialog = new Dialog(getActivity(),R.style.ActionSheetDialogStyle);
//填充对话框的布局
        inflate = LayoutInflater.from(getActivity()).inflate(R.layout.share_dialog, null);
        ll_weinxin=(LinearLayout) inflate.findViewById(R.id.ll_weinxin);
        ll_penyouquan=(LinearLayout) inflate.findViewById(R.id.ll_penyouquan);
        ll_copy=(LinearLayout) inflate.findViewById(R.id.ll_copy);
        concle=(TextView) inflate.findViewById(R.id.concle);

        ll_weinxin.setOnClickListener(this);
        ll_penyouquan.setOnClickListener(this);
        ll_copy.setOnClickListener(this);
        concle.setOnClickListener(this);
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity( Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(false);
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        dialog.show();//显示对话框


    }
    /**\
     * 分享链接、title、内容
     */
    String linkHref = "分享链接";
    String title = "分享标题！";
    String content = "分享内容";
    //分享图片
    private UMImage image = null;
    private void  sharewein(){
        UMImage image = new UMImage(getActivity(), R.mipmap.log2);//资源文件

        UMWeb web = new UMWeb("https://www.baidu.com/");
        web.setTitle("产业通");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("产业通");//描述
        new ShareAction(getActivity())
                .setPlatform(WEIXIN)
                .withMedia(web)
                .share();

    }
    private void  sharewcircle(){
        UMImage image = new UMImage(getActivity(), R.mipmap.log2);//资源文件

        UMWeb web = new UMWeb("https://www.baidu.com/");
        web.setTitle("产业通");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("产业通");//描述
        new ShareAction(getActivity())
                .setPlatform(WEIXIN_CIRCLE)
                .withMedia(web)
                .share();

    }
    //分享回调
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {


        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            ToastUtils.show("分享成功");
//            if (platform.toString().equals("QQ")){
//                ToastUtil.showToast("手机QQ分享成功");
//            }else if (platform.toString().equals("QZONE")){
//                ToastUtil.showToast("QQ空间分享成功");
//            }else if (platform.toString().equals("WEIXIN")){
//                ToastUtil.showToast("微信好友分享成功");
//            }else if (platform.toString().equals("WEIXIN_CIRCLE")){
//                ToastUtil.showToast("微信朋友圈分享成功");
//            }

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            ToastUtils.show("分享失败");
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            ToastUtils.show("分享取消");
        }
    };
}
