package com.meiliangzi.app.ui.view.creativecommons;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.meiliangzi.app.R;
import com.meiliangzi.app.model.bean.RepositoryinfoBean;
import com.meiliangzi.app.tools.ProxyUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.ScaleImageView;

import butterknife.BindView;


public class CommonsDataActivity extends BaseActivity {
    @BindView(R.id.text_title)
    TextView text_title;
    @BindView(R.id.text_time)
    TextView text_time;
    @BindView(R.id.text_neirong)
    TextView text_neirong;
    @BindView(R.id.gradview)
    MyGridView gradview;
    BaseVoteAdapter<String> Adapter;
    private int mScreenWidth;

    private ScaleImageView scaleImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_commons_data);
    }

    @Override
    protected void findWidgets() {
        scaleImageView=new ScaleImageView(this);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        Adapter = new BaseVoteAdapter<String>(this, R.layout.showselectphone) {
            @Override
            public void convert(BaseViewHolder helper, String item) {
                //适配imageView，正方形，宽和高都是屏幕宽度的1/3
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) helper.getView(R.id.image_group_userheader).getLayoutParams();
                params.width =  mScreenWidth / 3 - params.rightMargin - params.leftMargin;
                params.height =  mScreenWidth / 3 - params.topMargin - params.bottomMargin;
                helper.getView(R.id.image_group_userheader).setLayoutParams(params);
                helper.showOrGoneView(R.id.image_delete,false);
                Glide.with(CommonsDataActivity.this).load(item).into((ImageView) helper.getView(R.id.image_group_userheader));

            }
        };
        gradview.setAdapter(Adapter);
        gradview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                scaleImageView.setUrls(Adapter.getmDatas(),position);
                scaleImageView.create();
            }
        });
    }

    @Override
    protected void initComponent() {
        ProxyUtils.getHttpProxy().repositoryinfo(this,getIntent().getIntExtra("repositoryId",0));

    }



    protected void getrepositoryinfo(RepositoryinfoBean bean){

        Adapter.setDatas(bean.getData().getImage());
        text_title.setText(bean.getData().getTitle());
        text_neirong.setText(bean.getData().getContent());
        text_time.setText(bean.getData().getUserName()+"  "+bean.getData().getCreateAt());
    }

}
