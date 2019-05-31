package com.meiliangzi.app.ui.view.Academy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.meiliangzi.app.MyApplication;
import com.meiliangzi.app.R;
import com.meiliangzi.app.ui.view.Academy.bean.IndexColumnBean;

import java.util.List;


/**
 * Created by Administrator on 2017/3/13 0013.
 * E-Mail：543441727@qq.com
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<IndexColumnBean.Data> datas;
    private Context mContext;
    private LayoutInflater mLiLayoutInflater;
    private boolean isShow;
    public MyAdapter(List<IndexColumnBean.Data> datas, Context context) {
        this.datas = datas;
        this.mContext = context;
        this.mLiLayoutInflater = LayoutInflater.from(mContext);
    }

    public void isshow(boolean isshow){
        this.isShow=isshow;
        notifyDataSetChanged();


    }
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mLiLayoutInflater.inflate(R.layout.item_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {
        holder.tv_title.setText(datas.get(position).getColumnName());
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

        public ViewHolder(View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            img = (ImageView) itemView.findViewById(R.id.img);

            ll_item = (RelativeLayout) itemView.findViewById(R.id.ll_item);
            //ll_hidden = (LinearLayout) itemView.findViewById(R.id.ll_hidden);
        }
    }
    public  List<IndexColumnBean.Data>  getdata(){
        return datas;
    }
}
