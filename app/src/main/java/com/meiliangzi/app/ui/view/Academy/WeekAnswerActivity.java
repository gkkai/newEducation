package com.meiliangzi.app.ui.view.Academy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.NewPreferManager;
import com.meiliangzi.app.tools.OkhttpUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.view.Academy.adapter.MyAdapter;
import com.meiliangzi.app.ui.view.Academy.adapter.OnRecyclerItemClickListener;
import com.meiliangzi.app.ui.view.Academy.adapter.RecyclerViewSpacesItemDecoration;
import com.meiliangzi.app.ui.view.Academy.bean.IndexColumnBean;
import com.meiliangzi.app.ui.view.Academy.bean.WeekColumnBean;
import com.meiliangzi.app.ui.view.Academy.fragment.WeekListFragment;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.SwipeRecyclerView;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class WeekAnswerActivity  extends BaseActivity  {
    private MyAdapter myAdapter;
    private String type;
    private ItemTouchHelper mItemTouchHelper;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.im_menu)
    ImageView im_menu;
    private View inflate;
    private Dialog dialog;
    Gson gson=new Gson();
    private MsgContentFragmentAdapter adapter;
    private SwipeRecyclerView recyclerView;
    private int pos=-1;
    private ImageView cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type=getIntent().getStringExtra("type");
        onCreateView(R.layout.activity_week_answer);
    }




    @Override
    protected void findWidgets() {
        inflate = LayoutInflater.from(getBaseContext()).inflate(R.layout.screen_weekanswer, null);
        recyclerView  = (SwipeRecyclerView) inflate.findViewById(R.id.recyclerView);

        cancel  = (ImageView) inflate.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        shownavigation( );
        if("0".equals(type)){
            tv_title.setText("智能答题");
        }else if("1".equals(type)){
            tv_title.setText("每周一答");
        }
        im_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myAdapter==null||myAdapter.getdata().size()==0){

                }else {
                    for(int i=0;i<myAdapter.getdata().size();i++){
                        if(i==tabLayout.getSelectedTabPosition()){
                            myAdapter.getdata().get(i).setIschos(true);
                        }else {
                            myAdapter.getdata().get(i).setIschos(false);
                        }
                    }
                    myAdapter.notifyDataSetChanged();
                    dialog.show();
                }



            }
        });
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pos=tab.getPosition();
                adapter.weekListFragments.get(pos).setType(adapter.names.get(pos).getType());
                adapter.weekListFragments.get(pos).setPaperTypeId(adapter.names.get(pos).getId());
                adapter.weekListFragments.get(pos).onRefresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                pos=tab.getPosition();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                pos=tab.getPosition();

            }
        });
    }


    private void initView(final WeekColumnBean bean) {

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,15);//top间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,15);//底部间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,15);//左间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,15);//右间距

        recyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));


        myAdapter = new MyAdapter(bean.getData(), mContext);
        bean.getData().get((tabLayout.getSelectedTabPosition())).setIschos(true);
        recyclerView.setAdapter(myAdapter);
        //TODO 我的频道
        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                //bean.getData().get(vh.getPosition()).setIschos(true);
                dialog.dismiss();
                viewPager.setCurrentItem(vh.getPosition());
                tabLayout.getTabAt(vh.getPosition()).select();

//
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //判断被拖拽的是否是前两个，如果不是则执行拖拽
//                if (vh.getLayoutPosition()>=5) {
//                    mItemTouchHelper.startDrag(vh);
//
//                    //获取系统震动服务
//                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
//                    vib.vibrate(70);
//
//                }
                mItemTouchHelper.startDrag(vh);
//
                    //获取系统震动服务
                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
                    vib.vibrate(70);
            }
        });

        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {

            /**
             * 是否处理滑动事件 以及拖拽和滑动的方向 如果是列表类型的RecyclerView的只存在UP和DOWN，如果是网格类RecyclerView则还应该多有LEFT和RIGHT
             * @param recyclerView
             * @param viewHolder
             * @return
             */
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN |
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    final int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                    final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    final int swipeFlags = 0;
//                    final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getAdapterPosition();
                Log.i("拖拽","fromPosition==========="+fromPosition);
                Log.i("拖拽","toPosition==========="+toPosition);
//                if(fromPosition<=5||toPosition<=5){
//
//                }else {
//                    if (fromPosition < toPosition) {
//                        for (int i = fromPosition; i < toPosition; i++) {
//                            Collections.swap(bean.getData(), i, i + 1);
//                        }
//                    } else {
//                        for (int i = fromPosition; i > toPosition; i--) {
//
//                            Collections.swap(bean.getData(), i, i - 1);
//                        }
//                    }
//                }
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(bean.getData(), i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {

                        Collections.swap(bean.getData(), i, i - 1);
                    }
                }

//                if(fromPosition<=5||toPosition<=5){
//                    Log.i("拖拽","fromPosition小与5==========="+toPosition);
//                }else {
//                    myAdapter.notifyItemMoved(fromPosition, toPosition);
//                }
                //bean.getData().add(new WeekColumnBean.Data());
                if("0".equals(type)){
                   // tv_title.setText("智能答题");
                    //TODO  保存本地
                    NewPreferManager.saveIntelligence(gson.toJson(bean));
                }else if("1".equals(type)){
                    //tv_title.setText("每周一答");
                    //TODO  保存本地
                    NewPreferManager.saveWeekColumbean(gson.toJson(bean));
                }

                myAdapter.setPos(tabLayout.getSelectedTabPosition());
                myAdapter.notifyItemMoved(fromPosition, toPosition);
                myAdapter.getdata();
                adapter.setList(bean.getData());
                //viewPager.setAdapter(adapter);
                for(int i=0;i<bean.getData().size();i++){
//                    adapter.weekListFragments.get(i).setType(adapter.names.get(i).getType());
//                    adapter.weekListFragments.get(i).setPaperTypeId(adapter.names.get(i).getId());
//                    adapter.weekListFragments.get(i).onRefresh();
                    if(bean.getData().get(i).ischos()){
                        viewPager.setCurrentItem(i);
                        tabLayout.getTabAt(i).select();
                        break;
                    }

                }
                //if()


                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//                int position = viewHolder.getAdapterPosition();
//                myAdapter.notifyItemRemoved(position);
//                datas.remove(position);
                //myAdapter.notifyDataSetChanged();
            }

            /**
             * 重写拖拽可用
             * @return
             */
            @Override
            public boolean isLongPressDragEnabled() {
                return false;
            }

            /**
             * 长按选中Item的时候开始调用
             *
             * @param viewHolder
             * @param actionState
             */
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                    //viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            /**
             * 手指松开的时候还原
             * @param recyclerView
             * @param viewHolder
             */
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                //viewHolder.itemView.setBackgroundColor(0);
            }
        });

        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    protected void initComponent() {
        //TODO 获取栏目

        Map<String,String> map=new HashMap<>();
        map.put("userId", NewPreferManager.getId());
        map.put("type",type);
        OkhttpUtils.getInstance(this).getList("academyService/examinationPaperType/findExaminationPaperTypeList", map, new OkhttpUtils.onCallBack() {
            @Override
            public void onFaild(Exception e) {

            }

            @Override
            public void onResponse(final String json) {
               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            WeekColumnBean bean=gson.fromJson(json,WeekColumnBean.class);

                            if(bean.getData().size()==0){
                                ToastUtils.show("暂无数据");
                            }else {
                                if("0".equals(type)){
                                    // tv_title.setText("智能答题");
                                    //TODO  保存本地
                                   // NewPreferManager.saveIntelligence(gson.toJson(bean));
                                    if(!NewPreferManager.getIntelligence().equals("")){
                                        WeekColumnBean bean1=gson.fromJson(NewPreferManager.getIntelligence(),WeekColumnBean.class);
                                        //TODO  先清除
                                        for(int i=0;i<bean1.getData().size();i++){

                                            if(iscontains(bean,bean1.getData().get(i))){

                                            }else {
                                                bean1.getData().remove(i);
                                            }
                                        }


                                        for(int i=0;i<bean.getData().size();i++){
                                            if(iscontains(bean1,bean.getData().get(i))){

                                            }else {
                                                bean1.getData().add(bean.getData().get(i));
                                            }
//
                                        }
                                        bean=bean1;
                                    }else {

                                    }
                                }else if("1".equals(type)){
                                    if(!NewPreferManager.getWeekColumbean().equals("")){
                                        WeekColumnBean bean1=gson.fromJson(NewPreferManager.getWeekColumbean(),WeekColumnBean.class);
                                        //TODO  先清除
                                        for(int i=0;i<bean1.getData().size();i++){

                                            if(iscontains(bean,bean1.getData().get(i))){

                                            }else {
                                                bean1.getData().remove(i);
                                            }
                                        }


                                        for(int i=0;i<bean.getData().size();i++){
                                            if(iscontains(bean1,bean.getData().get(i))){

                                            }else {
                                                bean1.getData().add(bean.getData().get(i));
                                            }
//
                                        }
                                        bean=bean1;
                                    }else {

                                    }
                                }

                                if(adapter!=null){
                                    adapter=null;
                                }
                                adapter = new MsgContentFragmentAdapter(getSupportFragmentManager());
                                //Adapter.setDatas(bean.getData());
                                // 更新适配器数据
                                adapter.setList(bean.getData());
                                viewPager.setAdapter(adapter);
                                viewPager.setOffscreenPageLimit(bean.getData().size()-1);
                                tabLayout.setupWithViewPager(viewPager);
                                tabLayout.getTabAt(0).select();

                                initView(bean);
                            }

                        }catch (Exception e){
                            ToastUtils.show(e.getMessage());

                        }
                    }
                });

            }
        });


    }
    private boolean iscontains(WeekColumnBean bean,WeekColumnBean.Data bean1){
        for(int i=0;i<bean.getData().size();i++){
                if(bean1.getId().equals(bean.getData().get(i).getId())){
                    return true;
            }
        }
        return false;
    }
    private void shownavigation( ) {
        dialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        if (inflate != null) {
            ViewGroup parentViewGroup = (ViewGroup) inflate.getParent();
            if (parentViewGroup != null ) {
                parentViewGroup.removeView(inflate);
            }
        }
        //将布局设置给Dialog
        dialog.setContentView(inflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = dialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.TOP);
        dialogWindow.setWindowAnimations(R.style.enter_exit_animate);  //添加动画
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 0;//设置Dialog距离底部的距离
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//       将属性设置给窗体
        dialogWindow.setAttributes(lp);
        //dialog.show();//显示对话框

    }


    public class MsgContentFragmentAdapter extends FragmentPagerAdapter {
        private List<WeekColumnBean.Data> names;
        private List<WeekListFragment> weekListFragments;

        public MsgContentFragmentAdapter(FragmentManager fm) {
            super(fm);
            this.names = new ArrayList<>();
            this. weekListFragments=new ArrayList<>();
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
               // return super.getItemPosition(object);
            }


        /**
         * 数据列表
         *
         * @param datas
         */
        public void setList(List<WeekColumnBean.Data> datas) {
            this.names.clear();
            this.names.addAll(datas);
            notifyDataSetChanged();
        }
        @Override
        public WeekListFragment getItem(int position) {
            WeekListFragment fragment = new WeekListFragment();
            Bundle bundle = new Bundle();
            bundle.putString("position", position+"");
            bundle.putSerializable("type",  type);
            bundle.putSerializable("paperTypeId",  names.get(position).getId());
            bundle.putSerializable("title",  names.get(position).getTypeName());
            fragment.setArguments(bundle);
            weekListFragments.add(position,fragment);
            return fragment;
        }
        @Override
        public int getCount() {
            return names.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String plateName = names.get(position).getTypeName();
            if (plateName == null) {
                plateName = "";
            } else if (plateName.length() > 15) {
                plateName = plateName.substring(0, 15) + "...";
            }
            return plateName;
        }



    }


}
