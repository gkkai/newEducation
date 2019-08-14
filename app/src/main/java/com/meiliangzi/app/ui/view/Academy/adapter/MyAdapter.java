package com.meiliangzi.app.ui.view.Academy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.view.Academy.bean.IndexColumnBean;
import com.meiliangzi.app.ui.view.Academy.bean.PaperOneLevelTypeListBean;
import com.meiliangzi.app.ui.view.Academy.bean.WeekColumnBean;

import java.util.List;


/**
 * Created by Administrator on 2017/3/13 0013.
 * E-Mail：543441727@qq.com
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<PaperOneLevelTypeListBean.Data> datas;
    private Context mContext;
    private LayoutInflater mLiLayoutInflater;
    private boolean isShow;
    private int pos;

    public MyAdapter(List<PaperOneLevelTypeListBean.Data> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
        this.mLiLayoutInflater = LayoutInflater.from(mContext);
    }

    public void isshow(boolean isshow){
        this.isShow=isshow;
        notifyDataSetChanged();


    }
    public void setPos(int isshow){
        this.pos=isshow;



    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLiLayoutInflater.inflate(R.layout.item_grid, parent, false));
       // return new ViewHolder(mLiLayoutInflater.inflate(R.layout.gradview_chex, parent, false));

    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.ck.setText(datas.get(position).getTypeName());
        if(datas.get(position).ischos()){
            holder.ck.setChecked(true);
        }else {
            holder.ck.setChecked(false);
        }

       // holder.img.setImageResource(datas.get(position).getImg());
        if(isShow) {
            holder.img.setVisibility(View.VISIBLE);
        }else {
            holder.img.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        ImageView img;
        public RelativeLayout ll_item;
        LinearLayout ll_hidden;

        CheckBox ck;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            img = (ImageView) itemView.findViewById(R.id.img);
            ck= (CheckBox) itemView.findViewById(R.id.ck);
            ll_item = (RelativeLayout) itemView.findViewById(R.id.ll_item);
            //ll_hidden = (LinearLayout) itemView.findViewById(R.id.ll_hidden);
        }
    }
    public  List<PaperOneLevelTypeListBean.Data>  getdata(){
        return datas;
    }
}
