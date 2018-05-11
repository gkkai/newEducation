package com.meiliangzi.app.ui.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.MeetIDBean;
import com.meiliangzi.app.model.bean.MeetlistsBean;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.LoginActivity;
import com.meiliangzi.app.ui.PersonCenterActivity;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseMeetListAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.dialog.MyDialogMeet;
import com.meiliangzi.app.widget.XListView;

import java.util.List;

import butterknife.BindView;
import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.MeetingError;
import us.zoom.sdk.MeetingEvent;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingServiceListener;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.StartMeetingOptions;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKInitializeListener;

public class ZoomActivity extends BaseActivity implements  ZoomSDKInitializeListener, XListView.IXListViewListener, MeetingServiceListener {
    private String APP_KEY="JalUzHrB9MVQy21CPYDcVn9FKhEyVl0EtW5i";
    private String APP_SECRET="Z4mVMhT1GMkBGJr3bvqQ59Eiv94B58ooNUtD";
    private String WEB_DOMAIN="www.zoomus.cn";
    private int currentPage=1;
    private int pageSize=10;
    private MyDialogMeet dialog;
    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    private BaseMeetListAdapter<MeetlistsBean.DataBean> adapter;
    //private MyListViewAdapter lladapter;
    List<MeetlistsBean.DataBean> DataBeans;
    private int page=1;
    private MeetingService meetingService;
    private String meetingPassword;
    private String meetingNomber;
    private ZoomSDK sdk;
    private boolean mbPendingStartMeeting = false;
    private final static String DISPLAY_NAME = "ZoomUS SDK";
    private String type="";
    private String ZOOM_TOKEN;
    private final static int STYPE = MeetingService.USER_TYPE_API_USER;
    private String USER_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        onCreateView(R.layout.activity_zoom);
        if(savedInstanceState == null) {
            ZoomSDK sdk = ZoomSDK.getInstance();
            sdk.initialize(this, APP_KEY, APP_SECRET, WEB_DOMAIN, this);
        } else {
            registerMeetingServiceListener();
        }





    }

    private void registerMeetingServiceListener() {
        ZoomSDK zoomSDK = ZoomSDK.getInstance();
        MeetingService meetingService = zoomSDK.getMeetingService();
        if(meetingService != null) {
            meetingService.addListener(this);
        }
    }

    @Override
    protected void findWidgets() {

    }

    @Override
    protected void initComponent() {
        listView.setPullLoadEnable(true);
        listView.setPullRefreshEnable(true);
        listView.setXListViewListener(this);
        adapter = new BaseMeetListAdapter<MeetlistsBean.DataBean>(this, listView, R.layout.item_meet_lists) {
            @Override
            public void convert(BaseViewHolder helper, final MeetlistsBean.DataBean item) {
                helper.setText(R.id.tvmeettype, item.getMeeting_type());
                helper.setText(R.id.tvPersonNumber, String.valueOf(item.getReserve_number()));
                helper.setText(R.id.tvMeetStartTime, item.getReal_start_time());
                helper.setText(R.id.tvMeetLong, String.valueOf(item.getReserve_length()));
                helper.getView(R.id.tvHost).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 主持人入口
                        dialog = new MyDialogMeet(ZoomActivity.this);
                        dialog.setYesOnclickListener("确认", new MyDialogMeet.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                             String input= dialog.password.getText().toString();
                                if(input.equals(item.getMeeting_password())){
                                    meetingPassword=item.getMeeting_password();
                                    //meetingNomber= item.getMeeting_app_id()  ;
                                    type="主持人";
                                    ProxyUtils.getHttpProxy().getmeeting(ZoomActivity.this, Integer.valueOf(item.getMeeting_app_id()), item.getMeeting_app_value());

                                }else {
                                    dialog.password.setText("");

                                    ToastUtils.custom("密码输入错误");
                                }
                                dialog.dismiss();
                            }
                        });
                        dialog.setNoOnclickListener("取消", new MyDialogMeet.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                meetingPassword=null;
                                dialog.dismiss();

                            }
                        });
                        dialog.show();
                    }
                });
                helper.getView(R.id.tvParticipants).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 参与人入口
                        dialog = new MyDialogMeet(ZoomActivity.this);

                        dialog.setYesOnclickListener("确认", new MyDialogMeet.onYesOnclickListener() {
                            @Override
                            public void onYesClick() {
                                if(dialog.password.getText().toString().equals(item.getJoin_password())){
                                    meetingPassword=item.getJoin_password();

                                    type="参与人";
                                    ProxyUtils.getHttpProxy().getmeeting(ZoomActivity.this, Integer.valueOf(item.getMeeting_app_id()), item.getMeeting_app_value());

                                }else {
                                    dialog.password.setText("");
                                    ToastUtils.custom("密码输入错误");
                                }
                                dialog.dismiss();
                            }
                        });
                        dialog.setNoOnclickListener("取消", new MyDialogMeet.onNoOnclickListener() {
                            @Override
                            public void onNoClick() {
                                meetingPassword=null;
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }

                });
            }
        };


        listView.setAdapter(adapter);
    }
    @Override
    protected void asyncRetrive() {
        getData(currentPage, pageSize);


    }
    @Override
    protected void onDestroy() {
        ZoomSDK zoomSDK = ZoomSDK.getInstance();

        if(zoomSDK.isInitialized()) {
            MeetingService meetingService = zoomSDK.getMeetingService();
            meetingService.removeListener(this);
        }

        super.onDestroy();
    }
    public void onClickBtnJoinMeeting() {



        ZoomSDK zoomSDK = ZoomSDK.getInstance();

        if(!zoomSDK.isInitialized()) {
            Toast.makeText(this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG).show();
            return;
        }

        MeetingService meetingService = zoomSDK.getMeetingService();

        JoinMeetingOptions opts = new JoinMeetingOptions();
		/*opts.no_driving_mode = true;
		opts.no_invite = true;
		opts.no_meeting_end_message = true;
		opts.no_titlebar = true;
		opts.no_bottom_toolbar = true;
		opts.no_dial_in_via_phone = true;
		opts.no_dial_out_to_phone = true;
        opts.no_disconnect_audio = true;
		opts.no_share = true;
		opts.invite_options = InviteOptions.INVITE_VIA_EMAIL + InviteOptions.INVITE_VIA_SMS;
		opts.meeting_views_options = MeetingViewsOptions.NO_BUTTON_SHARE;
		opts.no_audio = true;
		opts.no_video = true;
		opts.no_meeting_error_message = true;
		opts.participant_id = "participant id";*/

        int ret = meetingService.joinMeeting(this, meetingNomber, DISPLAY_NAME, meetingPassword, opts);

    }
    public void onClickBtnStartMeeting() {
        if(meetingNomber.length() == 0) {
            Toast.makeText(this, "You need to enter a scheduled meeting number.", Toast.LENGTH_LONG).show();
            return;
        }

        ZoomSDK zoomSDK = ZoomSDK.getInstance();

        if(!zoomSDK.isInitialized()) {
            Toast.makeText(this, "ZoomSDK has not been initialized successfully", Toast.LENGTH_LONG).show();
            return;
        }

        final MeetingService meetingService = zoomSDK.getMeetingService();

        if(meetingService.getMeetingStatus() != MeetingStatus.MEETING_STATUS_IDLE) {
            long lMeetingNo = 0;
            try {
                lMeetingNo = Long.parseLong(meetingNomber);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Invalid meeting number: " + meetingNomber, Toast.LENGTH_LONG).show();
                return;
            }

            if(meetingService.getCurrentMeetingID() == lMeetingNo) {
                meetingService.returnToMeeting(this);
                return;
            }

            new AlertDialog.Builder(this)
                    .setMessage("Do you want to leave current meeting and start another?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mbPendingStartMeeting = true;
                            meetingService.leaveCurrentMeeting(false);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
            return;
        }

        StartMeetingOptions opts = new StartMeetingOptions();
		/*opts.no_driving_mode = true;
		opts.no_invite = true;
		opts.no_meeting_end_message = true;
		opts.no_titlebar = true;
		opts.no_bottom_toolbar = true;
		opts.no_dial_in_via_phone = true;
		opts.no_dial_out_to_phone = true;
		opts.no_disconnect_audio = true;
		opts.no_share = true;
		opts.invite_options = InviteOptions.INVITE_ENABLE_ALL;
		opts.meeting_views_options = MeetingViewsOptions.NO_BUTTON_SHARE + MeetingViewsOptions.NO_BUTTON_VIDEO;
		opts.no_audio = true;
		opts.no_video = true;
		opts.no_meeting_error_message = true;*/

        int ret = meetingService.startMeeting(this, USER_ID, ZOOM_TOKEN, STYPE, meetingNomber, DISPLAY_NAME, opts);

    }
    @Override
    public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {

        if(errorCode != ZoomError.ZOOM_ERROR_SUCCESS) {
            Toast.makeText(this, "Failed to initialize Zoom SDK. Error: " + errorCode + ", internalErrorCode=" + internalErrorCode, Toast.LENGTH_LONG).show();
        } else {
            //Toast.makeText(this, "Initialize Zoom SDK successfully.", Toast.LENGTH_LONG).show();

            registerMeetingServiceListener();
        }
    }

    protected void meetinglist(MeetlistsBean meetlistsBeans) {
        DataBeans = meetlistsBeans.getData();
        adapter.pullRefresh(meetlistsBeans.getData());

    }
    protected void getmeeting(MeetIDBean meetidBeans) {
        USER_ID=meetidBeans.getData().getUid();
        ZOOM_TOKEN=meetidBeans.getData().getToken();
        meetingNomber=  meetidBeans.getData().getMeeting_id()  ;
        if(meetingNomber==null){
            ToastUtils.show("会议还未开始，请您稍后再试");
        }else {
            if("参与人".equals(type)){
                onClickBtnJoinMeeting();
            }else if("主持人".equals(type)){
                onClickBtnStartMeeting();
            }
        }

     /*  *//* DataBeans = meetlistsBeans.getData();
        adapter.pullRefresh(meetlistsBeans.getData());*//*
        MeetingOptions opts = new MeetingOptions();
        meetingService = sdk.getMeetingService();
	opts.no_driving_mode = true;

	opts.no_meeting_end_message = true;

	opts.no_titlebar = true;

	opts.no_bottom_toolbar = true;

	opts.no_invite = true;
//	……………
        if(meetingPassword==null){
            int ret = meetingService.startMeeting(this, meetidBeans.getData().getMeeting_id(),  opts);
        }else {
            int rets = meetingService.joinMeeting(this, meetidBeans.getData().getMeeting_id(), meetingPassword, opts);
        }

*/


    }

    public void getData(int currentPage, int pageSize) {
       /* if(TextUtils.isEmpty(PreferManager.getUserId()) && !PreferManager.getIsComplete()){
            tvEmpty.setVisibility(View.VISIBLE);
            return;
        }*/

        ProxyUtils.getHttpProxy().meetinglist(this, currentPage, pageSize);


    }

    @Override
    public void onRefresh() {
        page = 1;


        getData(currentPage, pageSize);
    }

    @Override
    public void onLoadMore() {
        page++;
        getData(currentPage, pageSize);
    }

    @Override
    protected void showErrorMessage(String errorMessage) {
        super.showErrorMessage(errorMessage);
        listView.stopRefresh();
        listView.stopLoadMore();

    }

    @Override
    protected void showErrorMessage(Integer errorCode, String errorMessage) {
        super.showErrorMessage(errorCode, errorMessage);
        listView.stopRefresh();
        listView.stopLoadMore();
    }

    @Override
    public void onMeetingEvent(int meetingEvent, int errorCode, int internalErrorCode) {

        if(meetingEvent == MeetingEvent.MEETING_CONNECT_FAILED && errorCode == MeetingError.MEETING_ERROR_CLIENT_INCOMPATIBLE) {
            Toast.makeText(this, "Version of ZoomSDK is too low!", Toast.LENGTH_LONG).show();
        }

        if(mbPendingStartMeeting && meetingEvent == MeetingEvent.MEETING_DISCONNECTED) {
            mbPendingStartMeeting = false;
            onClickBtnStartMeeting();
        }
    }

    @Override
    protected void initListener() {
        tvEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(PreferManager.getUserId())) {
                    IntentUtils.startAtyWithSingleParam(ZoomActivity.this, LoginActivity.class, "activity", "WholeFragment");
                } else if (!PreferManager.getIsComplete()) {
                    IntentUtils.startAtyWithSingleParam(ZoomActivity.this, PersonCenterActivity.class, "activity", "WholeFragment");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PreferManager.getUserId()) || !PreferManager.getIsComplete()){
            if(TextUtils.isEmpty(PreferManager.getUserId())){
                tvEmpty.setText("请先登录");
            }else if(!PreferManager.getIsComplete()){
                tvEmpty.setText("请完善个人信息");
            }
            tvEmpty.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else {
            listView.setVisibility(View.VISIBLE);
            tvEmpty.setVisibility(View.GONE);
            getData(currentPage, pageSize);
        }
    }


}