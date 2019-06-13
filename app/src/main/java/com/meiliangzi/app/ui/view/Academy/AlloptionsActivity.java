package com.meiliangzi.app.ui.view.Academy;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.tools.PreferUtils;
import com.meiliangzi.app.tools.ToastUtils;
import com.meiliangzi.app.ui.base.BaseActivity;
import com.meiliangzi.app.ui.base.BaseViewHolder;
import com.meiliangzi.app.ui.base.BaseVoteAdapter;
import com.meiliangzi.app.ui.base.ViewHolder;
import com.meiliangzi.app.ui.view.Academy.adapter.DividerGridItemDecoration;
import com.meiliangzi.app.ui.view.Academy.adapter.MyAdapter;
import com.meiliangzi.app.ui.view.Academy.adapter.OnRecyclerItemClickListener;
import com.meiliangzi.app.ui.view.Academy.adapter.RecyclerViewSpacesItemDecoration;
import com.meiliangzi.app.ui.view.Academy.bean.IndexColumnBean;
import com.meiliangzi.app.ui.view.checkSupervise.CheckSuperviseProjectListActivity;
import com.meiliangzi.app.widget.MyGridView;
import com.meiliangzi.app.widget.SwipeRecyclerView;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class AlloptionsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.recyclerView)
    SwipeRecyclerView mRecyclerView;
    private String[] titles = {"美食", "电影", "酒店住宿", "休闲娱乐", "婚纱摄影", "学习培训", "家装", "结婚", "全部分配", "学习培训", "家装", "结婚", "全部分配", "学习培训", "家装", "结婚", "全部分配"};
    //private List<IndexColumnBean.Data> top = new ArrayList<>();
    private ItemTouchHelper mItemTouchHelper;
    private MyAdapter myAdapter;
    Gson gson = new Gson();
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    @BindView(R.id.g_recommend)
    MyGridView g_recommend;
    BaseVoteAdapter<IndexColumnBean.Data> recommendAdapter;
    //List<String> list=new ArrayList<>();
    private List<IndexColumnBean.Data> recommend = new ArrayList<>();
    LayoutInflater mLiLayoutInflater;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateView(R.layout.activity_alloptions);
    }

    @Override
    protected void findWidgets() {

        //initData();



        initView();
        if(recommend.size()==0){
            g_recommend.setVisibility(View.GONE);

        }

        recommendAdapter=new BaseVoteAdapter<IndexColumnBean.Data>(this, R.layout.item_grid) {


            @Override
            public void convert(BaseViewHolder helper, IndexColumnBean.Data item) {
                ((TextView)helper.getView(R.id.tv_title)).setText(item.getColumnName());
                helper.showOrGoneView(R.id.img,false);
            }
        };
        recommendAdapter.setDatas(recommend);
        //TODO 频道推荐
        g_recommend.setAdapter(recommendAdapter);
        g_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(tv_edit.getText().toString().equals("完成")){

                }else {
                   // top.add(MyApplication.indexColumnBean.getData().get(position));
                    MyApplication.indexColumnBean.getData().remove((MyApplication.indexColumnBean.getData().get(position)));
                    myAdapter.notifyDataSetChanged();
                    recommendAdapter.notifyDataSetChanged();
                }


            }
        });
        mLiLayoutInflater = LayoutInflater.from(mContext);

        view=mLiLayoutInflater.inflate(R.layout.item_grid, null, false);
        tv_edit.setOnClickListener(this);
        if(tv_edit.getText().toString().equals("完成")){
            myAdapter.isshow(false);
        }else {
            myAdapter.isshow(true);
        }
    }
//    private void saveTab(List<String> title) {
//        Gson gson = new Gson();
//        String data = gson.toJson(title);
//
//        PreferUtils.put("TAB",data);
//    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_edit:
                if (tv_edit.getText().toString().equals("完成")){
                    tv_edit.setText("编辑");
                   myAdapter.isshow(true);

                }else {
                    tv_edit.setText("完成");
                    myAdapter.isshow(false);
                }
                break;

        }

    }

    @Override
    protected void initComponent() {

    }
//    private void initData() {
//        top= gson.fromJson(PreferUtils.getString("TAB",""),List.class);
//        //初始化data
//        for (int i = 0; i < titles.length; i++) {
//            //动态获取资源ID，第一个参数是资源名，第二个参数是资源类型例如drawable，string等，第三个参数包名
//
//            (MyApplication.indexColumnBean.getData().add(titles[i]);
//        }
//
//    }

    @Override
    public void onBackClick(View v) {
        setResult(1006);
        finish();
    }

    private void initView() {

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,15);//top间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,15);//底部间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,15);//左间距

        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,15);//右间距

        mRecyclerView.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
       // myAdapter = new MyAdapter(MyApplication.indexColumnBean.getData(), mContext);
        mRecyclerView.setAdapter(myAdapter);
        //TODO 我的频道
        mRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(mRecyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {
                if(tv_edit.getText().toString().equals("完成")){
                    //TODO
                    Intent intent =new Intent();
                    intent.putExtra("position",vh.getPosition());
                    setResult(1005,intent);
                    finish();
                }else {
                    myAdapter.notifyDataSetChanged();
                    recommendAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder vh) {
                //判断被拖拽的是否是前两个，如果不是则执行拖拽
                if (vh.getLayoutPosition()>=5) {
                    mItemTouchHelper.startDrag(vh);

                    //获取系统震动服务
                    Vibrator vib = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);//震动70毫秒
                    vib.vibrate(70);

                }
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
                if(fromPosition<=5||toPosition<=5){

                }else {
                    if (fromPosition < toPosition) {
                        for (int i = fromPosition; i < toPosition; i++) {
                            Collections.swap(MyApplication.indexColumnBean.getData(), i, i + 1);
                        }
                    } else {
                        for (int i = fromPosition; i > toPosition; i--) {

                                Collections.swap(MyApplication.indexColumnBean.getData(), i, i - 1);
                        }
                    }
                }

                if(fromPosition<=5||toPosition<=5){
                    Log.i("拖拽","fromPosition小与5==========="+toPosition);
                }else {
                    myAdapter.notifyItemMoved(fromPosition, toPosition);
                }


                myAdapter.getdata();
                //saveTab(myAdapter.getdata());

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

        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

        mRecyclerView.setRightClickListener(new SwipeRecyclerView.OnRightClickListener() {
            @Override
            public void onRightClick(int position, String id) {
                MyApplication.indexColumnBean.getData().remove(position);
//                myAdapter.notifyItemRemoved(position);
                myAdapter.notifyDataSetChanged();
                Toast.makeText(mContext, " position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }


}
