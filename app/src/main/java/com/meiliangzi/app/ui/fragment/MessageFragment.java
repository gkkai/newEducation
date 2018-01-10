package com.meiliangzi.app.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.db.bean.MessageBean;
import com.meiliangzi.app.db.manage.MessageManage;
import com.meiliangzi.app.tools.IntentUtils;
import com.meiliangzi.app.tools.PreferManager;
import com.meiliangzi.app.tools.TimeUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.ArticalDetailActivity;
import com.meiliangzi.app.ui.VideoDetailActivity;
import com.meiliangzi.app.ui.base.BaseFragment;
import com.meiliangzi.app.ui.base.BaseQuickAdapter;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.widget.XListView;

import java.util.List;

import butterknife.BindView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * @author xiaobo
 * @version 1.0
 * @date 2017/8/16
 * @description 我的
 **/

public class MessageFragment extends BaseFragment implements XListView.IXListViewListener{


    private BaseQuickAdapter adapter;

    @BindView(R.id.listView)
    XListView listView;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.bt)
    Button bt;

    public MessageFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = createView(inflater.inflate(R.layout.fragment_message, null, false));
        return view;
    }

    @Override
    protected void findWidgets() {
        listView.setXListViewListener(this);
        listView.setPullLoadEnable(false);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RongIM.getInstance().startPrivateChat(getActivity(), "20474", "苟凯凯");
            }
        });
        //获取会话列表
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                ToastUtils.show("");

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });

    }


    @Override
    protected void initComponent() {

        adapter = new BaseQuickAdapter<MessageBean>(getActivity(),R.layout.item_message) {
            @Override
            public void convert(final BaseViewHolder helper, final MessageBean item) {
                helper.setText(R.id.tv_msgs_title,item.getTitle());
                helper.setText(R.id.tv_msg_content,item.getContent());
                helper.setText(R.id.tv_msgs_time, TimeUtils.getScheduleTimeforStringSecond(item.getTime()));
                helper.setImageByUrl(R.id.ivIcon,item.getImage(),R.mipmap.logo,R.mipmap.logo);
                helper.getView(R.id.tvDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).deleteChannelById(item.getId());
                        adapter.getmDatas().remove(item);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        };
        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(TextUtils.isEmpty(PreferManager.getUserId())){
            tvEmpty.setVisibility(View.VISIBLE);
        }else {
            tvEmpty.setVisibility(View.GONE);
        }
        List<MessageBean> beanList= MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).getUserChannel();
        adapter.setDatas(beanList);
    }

    @Override
    protected void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageBean messageBean= (MessageBean) parent.getItemAtPosition(position);
                if(messageBean.getKey().equals("essay")){
                    IntentUtils.startAtyWithSingleParam(getActivity(), ArticalDetailActivity.class,"id",messageBean.getId());
                }else {
                    IntentUtils.startAtyWithSingleParam(getActivity(), VideoDetailActivity.class,"id",messageBean.getId());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onRefresh() {
        List<MessageBean> beanList= MessageManage.getManage(MyApplication.getInstance().getSQLHelper()).getUserChannel();
        adapter.pullRefreshCommon(beanList);
        listView.setRefreshTime(TimeUtils.getCurrentDate());
        listView.stopRefresh();
    }

    @Override
    public void onLoadMore() {

    }
}
